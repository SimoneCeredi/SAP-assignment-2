package infrastructure.database

import domain.EScooter

interface EScooterRepository {
    fun saveEScooter(escooter: EScooter): Result<EScooter>
    fun getEScooter(escooterId: String): Result<EScooter>
}