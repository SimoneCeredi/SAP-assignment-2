package business.logic.layer.domain.model

import business.logic.layer.escooter.EScooterImpl
import business.logic.layer.user.User
import business.logic.layer.escooter.Escooter
import business.logic.layer.ride.Ride
import business.logic.layer.user.UserImpl

interface DomainModel {
    fun addNewUser(id: String, name: String, surname: String): Unit
    fun getUser(id: String): User?

    fun addNewEscooter(id:String): Unit
    fun getEscooter(id: String): Escooter?

    fun startNewRide(user: User, escooter: Escooter): String
    fun getRide(id: String): Ride?

    fun getOngoingRides(): List<Ride>
}

class DomainModelImpl : DomainModel {

    // TODO: Add datasourceport

    private var users: Map<String, User> = emptyMap()
    private var escooters: Map<String, Escooter> = emptyMap()
    private var rides: Map<String, Escooter> = emptyMap()

    override fun addNewUser(id: String, name: String, surname: String) {
        users += Pair(id, UserImpl(id, name, surname))
    }

    override fun getUser(id: String): User? {
        return users.get(id)
    }

    override fun addNewEscooter(id: String) {
        escooters += Pair(id, EScooterImpl(id, Escooter.EScooterState.AVAILABLE, null))
    }

    override fun getEscooter(id: String): Escooter? {
        return escooters.get(id)
    }

    override fun startNewRide(user: User, escooter: Escooter): String {
        TODO("not yet implemented")
    }

    override fun getRide(id: String): Ride? {
        TODO("not yet implemented")
    }

    override fun getOngoingRides(): List<Ride> {
        TODO("not yet implemented")
    }
}

