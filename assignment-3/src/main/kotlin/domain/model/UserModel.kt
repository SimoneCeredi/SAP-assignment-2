package domain

import infrastructure.database.UserDatabaseAdapter

interface UserModel {
    val databasePort: UserDatabaseAdapter

    fun addNewUser(user: User): Result<User>
    fun getUser(id: String): Result<User>
}

class UserModelImpl(override val databasePort: UserDatabaseAdapter) : UserModel {
    override fun addNewUser(user: User): Result<User> = databasePort.saveUser(user)

    override fun getUser(id: String): Result<User> = databasePort.getUser(id)
}

fun UserModel(databasePort: UserDatabaseAdapter) = UserModelImpl(databasePort)
