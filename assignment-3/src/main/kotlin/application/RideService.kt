package application

import application.exceptions.RideNotFound
import domain.Ride
import domain.model.RideModel
import java.util.logging.Level
import java.util.logging.Logger

interface RideService {
    val rideModel: RideModel
    var rideDashboardPort: RideDashboardPort?
    fun setRideDashboardPort(rideDashboardPort: RideDashboardPort): RideService
    fun startNewRide(userId: String, escooterId: String): Result<Ride>
    fun getRide(id: String): Result<Ride>
    fun endRide(id: String): Result<Ride>
}

class RideServiceImpl private constructor(
    override val rideModel: RideModel, override var rideDashboardPort: RideDashboardPort?
) : RideService {
    companion object {
        fun new(rideModel: RideModel) = RideServiceImpl(rideModel, null)
    }

    val logger: Logger = Logger.getLogger("[RideService]")

    override fun setRideDashboardPort(rideDashboardPort: RideDashboardPort): RideService {
        this.rideDashboardPort = rideDashboardPort
        return this
    }

    override fun startNewRide(userId: String, escooterId: String): Result<Ride> {
        logger.log(Level.INFO, "Registering new ride")
        val ride = Ride(userId, escooterId)
        return rideModel.addNewRide(ride)
            .onSuccess { rideDashboardPort?.notifyOngoingRidesChanged(rideModel.getOngoingRides()) }
    }

    override fun getRide(id: String): Result<Ride> {
        logger.log(Level.INFO, "Getting ride with id $id")
        return rideModel.getRide(id)
    }

    override fun endRide(id: String): Result<Ride> {
        logger.log(Level.INFO, "Ending ride with id $id")
        return getRide(id).fold(onFailure = { Result.failure(RideNotFound()) }, onSuccess = {
            logger.log(Level.INFO, "got the ride")
            rideModel.endRide(it.end()).also {
                rideDashboardPort?.notifyOngoingRidesChanged(rideModel.getOngoingRides())
            }
        }

        )
    }

}

fun RideService(rideModel: RideModel) = RideServiceImpl.new(rideModel)
