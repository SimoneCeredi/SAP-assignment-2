package infrastructure.database.file.system

import domain.User
import infrastructure.database.file.system.jsonifier.UserJsonifier

interface UserFileSystemAdapter {
    val fileSystemAdapter: FileSystemAdapter

    fun saveUser(user: User): Result<User>
    fun getUser(userId: String): Result<User>
}

class UserFileSystemAdapterImpl(override val fileSystemAdapter: FileSystemAdapter) : UserFileSystemAdapter {
    private val usersPath = "users"
    override fun saveUser(user: User): Result<User> =
        fileSystemAdapter.saveObj(usersPath, user.id, UserJsonifier(user).toJson()).mapCatching { user }


    override fun getUser(userId: String): Result<User> {
        TODO("Not yet implemented")
    }
}

fun UserFileSystemAdapter(fileSystemAdapter: FileSystemAdapter) = UserFileSystemAdapterImpl(fileSystemAdapter)