package business.logic.layer.domain.model

import business.logic.layer.user.User
import business.logic.layer.escooter.EScooter
import business.logic.layer.ride.Ride

interface DomainModel {
    fun addNewUser(id: String, name: String, surname: String): Unit
    fun getUser(id: String): User?

    fun addNewEscooter(id:String): Unit
    fun getEscooter(id: String): EScooter?

    fun startNewRide(user: User, escooter: EScooter): String
    fun getRide(id: String): Ride?

    fun getOngoingRides(): Collection<Ride>
}

class DomainModelImpl : DomainModel {

    // TODO: Add datasourceport

    private var users: Map<String, User> = emptyMap()
    private var escooters: Map<String, EScooter> = emptyMap()
    private var rides: Map<String, Ride> = emptyMap()

    override fun addNewUser(id: String, name: String, surname: String) {
        users += Pair(id, User(id, name, surname))
    }

    override fun getUser(id: String): User? {
        return users[id]
    }

    override fun addNewEscooter(id: String) {
        escooters += Pair(id, EScooter(id, EScooter.EScooterState.AVAILABLE, null))
    }

    override fun getEscooter(id: String): EScooter? {
        return escooters[id]
    }

    override fun startNewRide(user: User, escooter: EScooter): String {
        escooter.state = EScooter.EScooterState.IN_USE
        val rideId = "ride-${rides.size+1}"
        rides += Pair(rideId, Ride(rideId, user, escooter))
        return rideId
    }

    override fun getRide(id: String): Ride? {
        return rides[id]
    }

    override fun getOngoingRides(): Collection<Ride> {
        return rides.values
    }
}


fun DomainModel() = DomainModelImpl()