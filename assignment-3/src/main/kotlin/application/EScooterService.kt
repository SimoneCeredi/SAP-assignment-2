package application

import application.exceptions.EScooterAlreadyExists
import domain.EScooter
import domain.model.EScooterModel
import java.util.logging.Level
import java.util.logging.Logger

interface EScooterService {
    val escooterModel: EScooterModel
    fun registerNewEScooter(id: String): Result<EScooter>
    fun getEscooter(id: String): Result<EScooter>
}

class EScooterServiceImpl(override val escooterModel: EScooterModel) : EScooterService {
    private val logger: Logger = Logger.getLogger("[EScooterService]")
    override fun registerNewEScooter(id: String): Result<EScooter> {
        logger.log(Level.INFO, "Registering new escooter")
        val escooter = EScooter(id)
        return getEscooter(id).fold(
            onFailure = {
                escooterModel.addNewEscooter(escooter).onSuccess {
                    Result.success(it)
                }.onFailure {
                    Result.failure<EScooter>(it)
                }
            },
            onSuccess = {
                Result.failure(EScooterAlreadyExists())
            }
        )
    }

    override fun getEscooter(id: String): Result<EScooter> = escooterModel.getEscooter(id)
}

fun EScooterService(escooterModel: EScooterModel) = EScooterServiceImpl(escooterModel)
