package infrastructure.database.mongo

import application.exceptions.EScooterAlreadyExists
import application.exceptions.EScooterNotFound
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Projections
import com.mongodb.kotlin.client.coroutine.MongoCollection
import domain.EScooter
import domain.MongoEScooter
import infrastructure.database.EScooterRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.bson.types.ObjectId

interface EScooterMongoRepository : EScooterRepository {
    val collection: MongoCollection<MongoEScooter>
}

class EScooterMongoRepositoryImpl(override val collection: MongoCollection<MongoEScooter>) : EScooterMongoRepository {
    private val projectionFields = Projections.fields(
        Projections.include(EScooter::id.name), Projections.excludeId()
    )

    override fun saveEScooter(escooter: EScooter): Result<EScooter> = runBlocking {
        runCatching {
            collection.insertOne(MongoEScooter(ObjectId(), escooter.id))
            escooter
        }
    }


    override fun getEScooter(escooterId: String): Result<EScooter> = runBlocking {
        collection.find(eq(EScooter::id.name, escooterId)).projection(projectionFields).firstOrNull()?.let {
            Result.success(it)
        } ?: Result.failure(EScooterNotFound())
    }

}

fun EScooterMongoRepository(collection: MongoCollection<MongoEScooter>) = EScooterMongoRepositoryImpl(collection)