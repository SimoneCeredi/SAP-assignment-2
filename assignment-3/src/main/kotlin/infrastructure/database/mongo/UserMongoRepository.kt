package infrastructure.database.mongo

import application.exceptions.UserNotFound
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Projections
import com.mongodb.kotlin.client.coroutine.MongoCollection
import domain.EScooter
import domain.User
import infrastructure.database.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

interface UserMongoRepository : UserRepository {
    val collection: MongoCollection<User>
}


class UserMongoRepositoryImpl(override val collection: MongoCollection<User>) : UserMongoRepository {
    private val projectionFields = Projections.fields(
        Projections.include(User::id.name, User::name.name, User::surname.name),
    )

    override fun saveUser(user: User): Result<User> = runBlocking {
        runCatching {
            collection.insertOne(user)
            user
        }
    }

    override fun getUser(userId: String): Result<User> = runBlocking {
        collection.find(eq(EScooter::id.name, userId)).projection(projectionFields).firstOrNull()?.let {
            Result.success(it)
        } ?: Result.failure(UserNotFound())
    }
}

fun UserMongoRepository(collection: MongoCollection<User>) = UserMongoRepositoryImpl(collection)