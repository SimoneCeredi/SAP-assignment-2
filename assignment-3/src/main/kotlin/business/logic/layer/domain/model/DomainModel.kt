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

    fun getOngoingRides(): List<Ride>
}

class DomainModelImpl : DomainModel {

    // TODO: Add datasourceport

    private var users: Map<String, User> = emptyMap()
    private var escooters: Map<String, EScooter> = emptyMap()
    private var rides: Map<String, EScooter> = emptyMap()

    override fun addNewUser(id: String, name: String, surname: String) {
        users += Pair(id, User(id, name, surname))
    }

    override fun getUser(id: String): User? {
        return users.get(id)
    }

    override fun addNewEscooter(id: String) {
        escooters += Pair(id, EScooter(id, EScooter.EScooterState.AVAILABLE, null))
    }

    override fun getEscooter(id: String): EScooter? {
        return escooters.get(id)
    }

    override fun startNewRide(user: User, escooter: EScooter): String {
        TODO("not yet implemented")
    }

    override fun getRide(id: String): Ride? {
        TODO("not yet implemented")
    }

    override fun getOngoingRides(): List<Ride> {
        TODO("not yet implemented")
    }
}

