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
    private val escooterProjectionFields = Projections.fields(
        Projections.include(EScooter::id.name), Projections.excludeId()
    )

    override fun saveEScooter(escooter: EScooter): Result<EScooter> = runBlocking {
        if (collection.insertOne(escooter).wasAcknowledged()) {
            Result.success(escooter)
        } else {
            Result.failure(EScooterAlreadyExists())
        }
    }


    override fun getEScooter(escooterId: String): Result<EScooter> = runBlocking {
        println("Getting escooter $escooterId")
        collection.find(eq(EScooter::id.name, escooterId)).projection(escooterProjectionFields).firstOrNull()?.let {
            println("got ${it.id}")
            Result.success(it)
        } ?: Result.failure(EScooterNotFound())
    }

}

fun EScooterMongoRepository(collection: MongoCollection<EScooter>) = EScooterMongoRepositoryImpl(collection)