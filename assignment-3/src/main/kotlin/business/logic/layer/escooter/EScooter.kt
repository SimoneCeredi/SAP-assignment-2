package business.logic.layer.escooter

import business.logic.layer.location.Location

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
    override var state: EScooter.EScooterState,
    override var location: Location?
) : EScooter

fun EScooter(id: String, state: EScooter.EScooterState, location: Location?) = EScooterImpl(id, state, location)