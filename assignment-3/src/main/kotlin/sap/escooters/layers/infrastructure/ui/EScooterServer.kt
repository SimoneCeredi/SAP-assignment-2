package sap.escooters.layers.infrastructure.ui

import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.StaticHandler
import sap.escooters.layers.application.ApplicationApi
import sap.escooters.layers.application.RideDashboardPort
import sap.escooters.layers.business.logic.ride.RideAlreadyEndedException
import java.util.logging.Level
import java.util.logging.Logger

class EScooterServer(private val port: Int, private val applicationApi: ApplicationApi) : AbstractVerticle(), RideDashboardPort {
    val logger: Logger = Logger.getLogger("[EScooter Server]")

    override fun start() {
        logger.log(Level.INFO, "Server initializing")
        val server = vertx.createHttpServer()
        val router = Router.router(vertx)

        router.route("/static/*").handler(StaticHandler.create().setCachingEnabled(false))
        router.route().handler(BodyHandler.create())

        router.route(HttpMethod.POST, "/api/users").handler(::registerNewUser)
        router.route(HttpMethod.GET, "/api/users/:userId").handler(::getUserInfo)
        router.route(HttpMethod.POST, "/api/escooters").handler(::registerNewEScooter)
        router.route(HttpMethod.GET, "/api/escooters/:escooterId").handler(::getEScooterInfo)
        router.route(HttpMethod.POST, "/api/rides").handler(::startNewRide)
        router.route(HttpMethod.GET, "/api/rides/:rideId").handler(::getRideInfo)
        router.route(HttpMethod.POST, "/api/rides/:rideId/end").handler(::endRide)

        server.webSocketHandler {
            logger.log(Level.INFO, "Ride monitoring request: ${it.path()}")
            if (it.path() == "/api/rides/monitoring") {
                it.accept()
                logger.log(Level.INFO, "New ride monitoring observer registered")
                vertx.eventBus().consumer("ride-events") { msg ->
                    when (msg.body()) {
                        is JsonObject -> it.writeTextMessage(msg.body().encodePrettily())
                        else -> it.end()
                    }
                }
            } else {
                logger.log(Level.INFO, "Ride monitoring observer rejected.")
                it.reject()
            }
        }
        server.requestHandler(router).listen(port)
        logger.log(Level.INFO, "EScooter server ready - port: $port")

    }

    private fun registerNewUser(context: RoutingContext?) {
        logger.log(Level.INFO, "New user registration request")
        val userInfo = context?.body()?.asJsonObject()
        val id = userInfo?.getString("id")
        val name = userInfo?.getString("name")
        val surname = userInfo?.getString("surname")
        if (id != null && name != null && surname != null) {
            val user = applicationApi.registerNewUser(id, name, surname)
            sendReply(context, JsonObject().put("result", if (user != null) "ok" else "user-id-already-exists"))
        }

    }

    private fun getUserInfo(context: RoutingContext?) {
        logger.log(Level.INFO, "New user info request")
        context?.pathParam("userId")?.let { user ->
            sendReply(context,
                applicationApi.getUser(user)?.let { JsonObject().put("result", "ok").put("user", it) }
                    ?: JsonObject().put("result", "user-not-found"))
        }
    }

    private fun registerNewEScooter(context: RoutingContext?) {
        logger.log(Level.INFO, "New EScooter registration request")
        val escooterInfo = context?.body()?.asJsonObject()
        context?.let {
            sendReply(context, escooterInfo?.let {
                it.getString("id")
                    ?.let { id -> applicationApi.getEScooter(id)?.let { JsonObject().put("result", "ok") } }
            } ?: JsonObject().put("result", "escooter-id-already-existing"))
        }
    }

    private fun getEScooterInfo(context: RoutingContext?) {
        logger.log(Level.INFO, "New EScooter info request")
        context?.let {
            sendReply(context, context.pathParam("escooterId")?.let { escooterId ->
                applicationApi.getEScooter(escooterId)?.let {
                    JsonObject().put("result", "ok").put("escooter", it)
                } ?: JsonObject().put("result", "escooter-not-found")
            })
        }
    }

    private fun startNewRide(context: RoutingContext?) {
        logger.log(Level.INFO, "New start new ride request")
        context?.let {
            val userId = context.body().asJsonObject().getString("userId")
            val escooterId = context.body().asJsonObject().getString("escooterId")
            sendReply(context, applicationApi.startNewRide(userId, escooterId)?.let {
                JsonObject().put("result", "ok").put("rideId", it)
            } ?: JsonObject().put("result", "start-new-ride-failed"))
        }
    }

    private fun getRideInfo(context: RoutingContext?) {
        logger.log(Level.INFO, "New ride info request")
        context?.let {
            sendReply(
                context,
                context.pathParam("rideId")?.let {
                    JsonObject().put("result", "ok").put("ride", it)
                } ?: JsonObject().put("result", "ride-not-found")
            )
        }
    }

    private fun endRide(context: RoutingContext?) {
        logger.log(Level.INFO, "New end ride request")
        context?.let {
            try {

                applicationApi.endRide(context.pathParam("rideId"))
                sendReply(context, JsonObject().put("result", "ok"))
            } catch (ex: IllegalStateException) {
                sendReply(context, JsonObject().put("result", "ride-not-found"))
            } catch (ex: RideAlreadyEndedException) {
                sendReply(context, JsonObject().put("result", "ride-already-ended"))
            }
        }
    }

    private fun sendReply(request: RoutingContext, reply: JsonObject?) {
        val response = request.response()
        response.putHeader("content-type", "application/json")
        response.end(reply.toString())
    }

    override fun notifyNumOngoingRidesChanged(nOngoingRides: Int) {
        logger.log(Level.INFO, "notify num rides changed")
        vertx.eventBus().publish(
            "ride-events",
            JsonObject().put("even", "num-ongoing-rides-changed").put("nOngoingRides", nOngoingRides)
        )
    }

}