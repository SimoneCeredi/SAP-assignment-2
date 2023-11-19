package infrastructure.database

import domain.EScooter
import domain.User

interface EScooterDatabaseAdapter {
    fun saveEScooter(escooter: EScooter): Result<EScooter>
    fun getEScooter(escooterId: String): Result<EScooter>
}