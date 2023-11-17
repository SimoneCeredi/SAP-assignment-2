package infrastructure.web.handlers

import application.RideService
import java.util.logging.Logger

interface RideHandler {
    val rideService: RideService
}

class RideHandlerImpl(override val rideService: RideService) : RideHandler {

    val logger = Logger.getLogger("[RideHandler]")
}

fun RideHandler(rideService: RideService) = RideHandlerImpl(rideService)
