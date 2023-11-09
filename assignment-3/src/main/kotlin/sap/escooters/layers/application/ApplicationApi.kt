package sap.escooters.layers.application

import io.vertx.core.json.JsonObject
import sap.escooters.layers.business.logic.domain.model.DomainModel
import sap.escooters.layers.business.logic.escooter.EScooter
import sap.escooters.layers.business.logic.ride.Ride
import sap.escooters.layers.business.logic.user.User
import java.lang.IllegalStateException

interface ApplicationApi {

    fun registerNewUser(id: String, name: String, surname: String): User?
    fun getUser(id: String): JsonObject?

    fun registerNewEScooter(id: String): EScooter?
    fun getEScooter(id: String): JsonObject?

    fun startNewRide(userId: String, escooterId: String): Ride?
    fun getRide(id: String): JsonObject?
    fun endRide(id: String)
}

class ApplicationApiImpl(val layer: DomainModel, val adapter: RideDashboardPort) : ApplicationApi {
    override fun registerNewUser(id: String, name: String, surname: String): User? {
        return layer.addNewUser(id, name, surname)
    }

    override fun getUser(id: String): JsonObject? {
        return layer.getUser(id)?.toJson()
    }

    override fun registerNewEScooter(id: String): EScooter? {
        return layer.addNewEscooter(id)
    }

    override fun getEScooter(id: String): JsonObject? {
        return layer.getEscooter(id)?.toJson()
    }

    override fun startNewRide(userId: String, escooterId: String): Ride? {
        val user = layer.getUser(userId)
        val escooter = layer.getEscooter(escooterId)
        return user?.let { escooter?.let { layer.startNewRide(user, escooter) } }
    }

    override fun getRide(id: String): JsonObject? {
        return layer.getRide(id)?.toJson()
    }

    override fun endRide(id: String) {
        layer.getRide(id)?.end() ?: throw IllegalStateException("Ride not found")
    }
}

fun ApplicationApi(layer: DomainModel, adapter: RideDashboardPort) = ApplicationApiImpl(layer, adapter)
