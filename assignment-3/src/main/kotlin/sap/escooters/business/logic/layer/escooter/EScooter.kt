package sap.escooters.business.logic.layer.escooter

import sap.escooters.business.logic.layer.location.Location

interface EScooter {
    val id: String

    enum class EScooterState {
        AVAILABLE,
        IN_USE,
        MAINTENANCE
    }

    var state: EScooterState
    var location: Location?
}

class EScooterImpl(
    override val id: String,
) : EScooter {

    override var state: EScooter.EScooterState = EScooter.EScooterState.AVAILABLE
    override var location: Location? = null
}

fun EScooter(id: String) = EScooterImpl(id)