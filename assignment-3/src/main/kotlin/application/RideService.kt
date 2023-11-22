package application

import domain.Ride
import domain.model.RideModel
import java.util.logging.Level
import java.util.logging.Logger

interface RideService {
    val rideModel: RideModel
    fun startNewRide(userId: String, escooterId: String) : Result<Ride>
    fun getRide(id: String): Result<Ride>
}

class RideServiceImpl(override val rideModel: RideModel) : RideService {
    val logger: Logger = Logger.getLogger("[RideService]")

    override fun startNewRide(userId: String, escooterId: String): Result<Ride> {
        logger.log(Level.INFO, "Registering new ride")
        val ride = Ride(userId, escooterId)
        return rideModel.addNewRide(ride)
    }

    override fun getRide(id: String): Result<Ride> = rideModel.getRide(id)
}

fun RideService(rideModel: RideModel) = RideServiceImpl(rideModel)
