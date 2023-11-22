package infrastructure.web

import infrastructure.web.handlers.EScooterHandler
import infrastructure.web.handlers.RideHandler
import infrastructure.web.handlers.UserHandler
import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.StaticHandler
import java.util.logging.Level
import java.util.logging.Logger


interface RestServiceVerticle {
    val port: Int
    val userHandler: UserHandler
    val eScooterHandler: EScooterHandler
    val rideHandler: RideHandler

    fun start()
}

class RestServiceVerticleImpl(
    override val port: Int,
    override val userHandler: UserHandler,
    override val eScooterHandler: EScooterHandler,
    override val rideHandler: RideHandler,
) : RestServiceVerticle, AbstractVerticle() {
    private val logger = Logger.getLogger("[RestService]")

    override fun start() {
        logger.log(Level.INFO, "Service initializing...")
        val server = vertx.createHttpServer()
        val router = Router.router(vertx)

        router.apply {
            route("/static/*").handler(StaticHandler.create().setCachingEnabled(false))
            route().handler(BodyHandler.create())

            route(HttpMethod.POST, "/api/users").handler(userHandler::registerNewUser)
            route(HttpMethod.GET, "/api/users/:userId").handler(userHandler::getUser)
            route(HttpMethod.POST, "/api/escooters").handler(eScooterHandler::registerNewEscooter)
            route(HttpMethod.GET, "/api/escooters/:escooterId").handler(eScooterHandler::getEscooter)
            route(HttpMethod.POST, "/api/rides").handler(rideHandler::startNewRide)
            route(HttpMethod.GET, "/api/rides/:rideId").handler(rideHandler::getRide)
            route(HttpMethod.POST, "/api/rides/:rideId/end").handler(rideHandler::endRide)
            // TODO: Implement other routes
        }

        server.apply {
            requestHandler(router)
            listen(port)
        }

        logger.log(
            Level.INFO, """Service ready, listening on port $port
            |Web pages are available at:
            |${createWebPageLink("user-registration")}
            |${createWebPageLink("escooter-registration")}
            |${createWebPageLink("ride-dashboard")}
        """.trimMargin()
        )
    }

    private fun createWebPageLink(name: String) = "http://localhost:$port/static/${name}.html"

}

fun RoutingContext.sendReply(message: JsonObject) {
    this.response().putHeader("content-type", "application/json").end(message.toString())
}

fun RestServiceVerticle(
    port: Int,
    userHandler: UserHandler,
    eScooterHandler: EScooterHandler,
    rideHandler: RideHandler,
) = RestServiceVerticleImpl(port, userHandler, eScooterHandler, rideHandler)