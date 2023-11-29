package infrastructure.database

import domain.Ride

interface RideRepository {
    fun saveRide(ride: Ride): Result<Ride>
    fun getRide(rideId: String): Result<Ride>
    fun getNextRideId(): String
    fun getAllRides(): Sequence<Ride>
    fun updateRide(ride: Ride): Result<Ride>

}
