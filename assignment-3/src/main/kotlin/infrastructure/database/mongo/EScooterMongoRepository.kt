package infrastructure.database.mongo

import application.exceptions.EScooterAlreadyExists
import application.exceptions.EScooterNotFound
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Projections
import com.mongodb.kotlin.client.coroutine.MongoCollection
import domain.EScooter
import infrastructure.database.EScooterRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

interface EScooterMongoRepository : EScooterRepository {
    val collection: MongoCollection<EScooter>
}

class EScooterMongoRepositoryImpl(override val collection: MongoCollection<EScooter>) : EScooterMongoRepository {
    private val projectionFields = Projections.fields(
        Projections.include(EScooter::id.name), Projections.excludeId()
    )

    override fun saveEScooter(escooter: EScooter): Result<EScooter> = runBlocking {
        runCatching {
            collection.insertOne(escooter)
            escooter
        }
    }


    override fun getEScooter(escooterId: String): Result<EScooter> = runBlocking {
        collection.find(eq(EScooter::id.name, escooterId)).projection(projectionFields).firstOrNull()?.let {
            Result.success(it)
        } ?: Result.failure(EScooterNotFound())
    }

}

fun EScooterMongoRepository(collection: MongoCollection<EScooter>) = EScooterMongoRepositoryImpl(collection)