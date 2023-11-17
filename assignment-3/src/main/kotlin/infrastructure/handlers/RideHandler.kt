package infrastructure.handlers

import application.RideService

interface RideHandler {
    val rideService: RideService
}

class RideHandlerImpl(override val rideService: RideService) : RideHandler

fun RideHandler(rideService: RideService) = RideHandlerImpl(rideService)
