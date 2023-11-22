package application

import application.exceptions.RideNotFound
import domain.Ride
import domain.model.RideModel
import java.util.logging.Level
import java.util.logging.Logger

interface RideService {
    val rideModel: RideModel
    fun startNewRide(userId: String, escooterId: String): Result<Ride>
    fun getRide(id: String): Result<Ride>
    fun endRide(id: String): Result<Ride>
}

class RideServiceImpl(override val rideModel: RideModel) : RideService {
    val logger: Logger = Logger.getLogger("[RideService]")

    override fun startNewRide(userId: String, escooterId: String): Result<Ride> {
        logger.log(Level.INFO, "Registering new ride")
        val ride = Ride(userId, escooterId)
        return rideModel.addNewRide(ride)
    }

    override fun getRide(id: String): Result<Ride> {
        logger.log(Level.INFO, "Getting ride with id $id")
        return rideModel.getRide(id)
    }

    override fun endRide(id: String): Result<Ride> {
        logger.log(Level.INFO, "Ending ride with id $id")
        return getRide(id).fold(
            onFailure = { Result.failure(RideNotFound()) },
            onSuccess = {
                logger.log(Level.INFO, "got the ride")
                rideModel.endRide(it.end())
            }

        )
    }
}

fun RideService(rideModel: RideModel) = RideServiceImpl(rideModel)
