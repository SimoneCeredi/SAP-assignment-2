package domain

import infrastructure.database.file.system.FileSystemAdapter
import infrastructure.database.file.system.UserFileSystemAdapter

interface UserModel {
    val databasePort: UserFileSystemAdapter

    fun addNewUser(user: User): Result<User>
    fun getUser(id: String): Result<User>
}

class UserModelImpl(override val databasePort: UserFileSystemAdapter) :UserModel {
    override fun addNewUser(user: User): Result<User> = databasePort.saveUser(user)

    override fun getUser(id: String): Result<User> = databasePort.getUser(id)
}

fun UserModel(databasePort: UserFileSystemAdapter) = UserModelImpl(databasePort)
