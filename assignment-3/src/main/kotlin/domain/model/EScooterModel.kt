package domain.model

import domain.EScooter
import infrastructure.database.EScooterDatabaseAdapter

interface EScooterModel {
    val databasePort: EScooterDatabaseAdapter
    fun addNewEscooter(escooter: EScooter): Result<EScooter>
    fun getEscooter(id: String): Result<EScooter>
}

class EScooterModelImpl(override val databasePort: EScooterDatabaseAdapter) : EScooterModel {
    override fun addNewEscooter(escooter: EScooter): Result<EScooter>  = databasePort.saveEScooter(escooter)

    override fun getEscooter(id: String): Result<EScooter> = databasePort.getEScooter(id)
}

fun EScooterModel(databasePort: EScooterDatabaseAdapter) = EScooterModelImpl(databasePort)