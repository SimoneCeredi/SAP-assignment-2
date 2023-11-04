package business.logic.layer.escooter

import business.logic.layer.location.Location

class EScooterImpl(
    override val id: String,
    override var state: Escooter.EScooterState,
    override var location: Location?
) : Escooter