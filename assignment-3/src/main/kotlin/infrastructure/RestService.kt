package infrastructure

import infrastructure.handlers.EScooterHandler
import infrastructure.handlers.RideHandler
import infrastructure.handlers.UserHandler
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