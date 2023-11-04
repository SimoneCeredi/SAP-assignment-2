package business.logic.layer.escooter

import business.logic.layer.location.Location

interface Escooter {
    val id: String

    enum class EScooterState {
        AVAILABLE,
        IN_USE,
        MAINTENANCE
    }

    var state: EScooterState
    var location: Location?
}
