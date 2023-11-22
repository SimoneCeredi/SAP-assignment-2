package domain.model

import domain.Ride
import infrastructure.database.RideDatabaseAdapter

interface RideModel {
    val databasePort: RideDatabaseAdapter

    fun addNewRide(ride: Ride): Result<Ride>
    fun getRide(id: String): Result<Ride>
}

class RideModelImpl(override val databasePort: RideDatabaseAdapter) : RideModel {
    override fun addNewRide(ride: Ride): Result<Ride>  =
        databasePort.saveRide(ride.setId(databasePort.getNextRideId()))

    override fun getRide(id: String): Result<Ride> = databasePort.getRide(id)
}

fun RideModel(databasePort: RideDatabaseAdapter) = RideModelImpl(databasePort)
