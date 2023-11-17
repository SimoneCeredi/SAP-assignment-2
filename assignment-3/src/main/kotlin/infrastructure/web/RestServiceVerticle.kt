package infrastructure.web

import infrastructure.web.handlers.EScooterHandler
import infrastructure.web.handlers.RideHandler
import infrastructure.web.handlers.UserHandler
import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
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
            route(HttpMethod.POST, "/api/users").handler(userHandler::registerNewUser)
            route(HttpMethod.GET, "/api/users/:userId").handler(userHandler::getUser)
            // TODO: Implement other routes
        }
    }

}

fun RoutingContext.sendReply(message: JsonObject): Unit {
    this.response().putHeader("content/type", "application/json").end(message.toString())
}

fun RestServiceVerticle(
    port: Int,
    userHandler: UserHandler,
    eScooterHandler: EScooterHandler,
    rideHandler: RideHandler,
) = RestServiceVerticleImpl(port, userHandler, eScooterHandler, rideHandler)