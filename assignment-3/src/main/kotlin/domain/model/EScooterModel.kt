package domain.model

import domain.EScooter
import infrastructure.database.EScooterRepository

interface EScooterModel {
    val databasePort: EScooterRepository
    fun addNewEscooter(escooter: EScooter): Result<EScooter>
    fun getEscooter(id: String): Result<EScooter>
}

class EScooterModelImpl(override val databasePort: EScooterRepository) : EScooterModel {
    override fun addNewEscooter(escooter: EScooter): Result<EScooter>  = databasePort.saveEScooter(escooter)

    override fun getEscooter(id: String): Result<EScooter> = databasePort.getEScooter(id)
}

fun EScooterModel(databasePort: EScooterRepository) = EScooterModelImpl(databasePort)