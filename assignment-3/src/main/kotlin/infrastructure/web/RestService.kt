package infrastructure.web

import infrastructure.web.handlers.EScooterHandler
import infrastructure.web.handlers.RideHandler
import infrastructure.web.handlers.UserHandler
import io.vertx.core.Vertx

class RestService(
    val port: Int,
    val userHandler: UserHandler,
    val escooterHandler: EScooterHandler,
    val rideHandler: RideHandler
) {
    fun init() {
        Vertx.vertx().apply { deployVerticle(RestServiceVerticle(port, userHandler, escooterHandler, rideHandler)) }
    }

}