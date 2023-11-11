package sap.escooters.layers.business.logic.domain.model

import sap.escooters.layers.business.logic.escooter.EScooter
import sap.escooters.layers.business.logic.ride.Ride
import sap.escooters.layers.business.logic.user.User
import sap.escooters.layers.infrastructure.data.source.DataSourcePort

interface DomainModel {
    /**
     * @return the newly added user or null if the user was already present
     */
    fun addNewUser(id: String, name: String, surname: String): User?
    fun getUser(id: String): User?

    /**
     * @return the newly added escooter or null if the escooter was already present
     */
    fun addNewEscooter(id: String): EScooter?
    fun getEscooter(id: String): EScooter?

    /**
     * @return the newly added ride or null if the ride was already present
     */
    fun startNewRide(user: User, escooter: EScooter): Ride?
    fun getRide(id: String): Ride?

    fun getOngoingRides(): Collection<Ride>
}

class DomainModelImpl(val databases: Sequence<DataSourcePort>) : DomainModel {


    private val users: MutableMap<String, User> = mutableMapOf()
    private val escooters: MutableMap<String, EScooter> = mutableMapOf()
    private val rides: MutableMap<String, Ride> = mutableMapOf()

    override fun addNewUser(id: String, name: String, surname: String): User? {
        val user = User(id, name, surname)
        val res = users.reversedPutIfAbsent(id, user)
        res?.apply { databases.forEach { it.saveUser(user.toJson()) } }
        return res
    }

    override fun getUser(id: String): User? {
        return users[id]
    }

    override fun addNewEscooter(id: String): EScooter? {
        val escooter = EScooter(id)
        val res = escooters.reversedPutIfAbsent(id, escooter)
        res?.apply { databases.forEach { it.saveEScooter(escooter.toJson()) } }
        return res
    }

    override fun getEscooter(id: String): EScooter? {
        return escooters[id]
    }

    override fun startNewRide(user: User, escooter: EScooter): Ride? {
        escooter.state = EScooter.EScooterState.IN_USE
        val rideId = "ride-${rides.size + 1}"
        if (getRide(rideId) != null) {
            throw IllegalStateException("Ride already exist")
        }
        val ride = Ride(rideId, user, escooter)
        val res = rides.reversedPutIfAbsent(rideId, ride)
        res?.apply { databases.forEach { it.saveRide(ride.toJson()) } }
        return res

    }

    override fun getRide(id: String): Ride? {
        return rides[id]
    }

    override fun getOngoingRides(): Collection<Ride> {
        return rides.values
    }

}

fun <K, V> MutableMap<K, V>.reversedPutIfAbsent(key: K, value: V): V? {
    return if (this.putIfAbsent(key, value) == null) {
        value
    } else {
        null
    }
}

fun DomainModel(databases: Sequence<DataSourcePort>) = DomainModelImpl(databases)