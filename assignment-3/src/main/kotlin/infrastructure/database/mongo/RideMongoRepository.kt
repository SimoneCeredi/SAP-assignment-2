package infrastructure.database.mongo

import application.exceptions.RideNotFound
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Projections
import com.mongodb.client.model.Sorts
import com.mongodb.kotlin.client.coroutine.MongoCollection
import domain.MongoRide
import domain.Ride
import infrastructure.database.RideRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.bson.types.ObjectId

interface RideMongoRepository : RideRepository {
    val collection: MongoCollection<MongoRide>
}

class RideMongoRepositoryImpl(override val collection: MongoCollection<MongoRide>) : RideMongoRepository {
    private val projectionFields = Projections.fields(
        Projections.include(
            Ride::id.name,
            Ride::escooterId.name,
            Ride::userId.name,
            Ride::startDate.name,
            Ride::endDate.name
        )
    )

    override fun saveRide(ride: Ride): Result<Ride> = runBlocking {
        runCatching {
            collection.insertOne(
                MongoRide(
                    ObjectId(),
                    ride.id,
                    ride.userId,
                    ride.escooterId,
                    ride.startDate,
                    ride.endDate
                )
            )
            ride
        }
    }

    override fun getRide(rideId: String): Result<Ride> = runBlocking {
        collection.find(eq(Ride::id.name, rideId)).projection(projectionFields).firstOrNull()?.let {
            Result.success(it)
        } ?: Result.failure(RideNotFound())
    }

    override fun getNextRideId(): String = runBlocking {
        collection.find()
            .sort(Sorts.descending("id"))
            .limit(1)
            .projection(projectionFields)
            .firstOrNull()?.let {
                it.id?.substringAfterLast('-')?.toIntOrNull()?.let {
                    "ride-${it + 1}"
                }
            } ?: "ride-1"
    }

    override fun getAllRides(): Sequence<Ride> = runBlocking {
        collection.find().toList().asSequence()
    }
}

fun RideMongoRepository(collection: MongoCollection<MongoRide>) = RideMongoRepositoryImpl(collection)