package application

import domain.Ride
import java.util.logging.Logger

interface RideService {
    fun addRide(userId: String, escooterId: String)
    fun getRide(id: String): Result<Ride>
}

class RideServiceImpl : RideService {

    val logger: Logger = Logger.getLogger("[RideService]")
    override fun addRide(userId: String, escooterId: String) {
        TODO("Not yet implemented")
    }

    override fun getRide(id: String): Result<Ride> {
        TODO("Not yet implemented")
    }
}

fun RideService() = RideServiceImpl()
