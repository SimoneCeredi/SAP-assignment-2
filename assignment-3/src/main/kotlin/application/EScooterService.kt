package application

import domain.EScooter
import java.util.logging.Logger

interface EScooterService {
    fun addEScooter(id: String)
    fun getEScooter(id: String): Result<EScooter>
}

class EScooterServiceImpl : EScooterService {
    val logger = Logger.getLogger("[EScooterService]")
    override fun addEScooter(id: String) {
        TODO("Not yet implemented")
    }

    override fun getEScooter(id: String): Result<EScooter> {
        TODO("Not yet implemented")
    }
}

fun EScooterService() = EScooterServiceImpl()
