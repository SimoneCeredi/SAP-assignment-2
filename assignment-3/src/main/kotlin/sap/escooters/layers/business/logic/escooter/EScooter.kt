package sap.escooters.layers.business.logic.escooter

import io.vertx.core.json.JsonObject
import sap.escooters.layers.business.logic.location.Location

interface EScooter {
    val id: String

    enum class EScooterState {
        AVAILABLE,
        IN_USE,
        MAINTENANCE
    }

    var state: EScooterState
    var location: Location?

    fun toJson(): JsonObject
}

class EScooterImpl(
    override val id: String,
) : EScooter {

    override var state: EScooter.EScooterState = EScooter.EScooterState.AVAILABLE
    override var location: Location? = null
    override fun toJson(): JsonObject {
        return JsonObject().put("id", id)
    }
}

fun EScooter(id: String) = EScooterImpl(id)