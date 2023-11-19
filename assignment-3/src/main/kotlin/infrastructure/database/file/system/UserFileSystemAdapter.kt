package infrastructure.database.file.system

import domain.User
import infrastructure.database.UserDatabaseAdapter
import infrastructure.database.file.system.jsonifier.UserJsonifier
import java.util.logging.Logger

interface UserFileSystemAdapter : UserDatabaseAdapter {
    val fileSystemAdapter: FileSystemAdapter
}

class UserFileSystemAdapterImpl(override val fileSystemAdapter: FileSystemAdapter) : UserFileSystemAdapter {
    private val usersPath = "users"
    private val logger = Logger.getLogger("[UserFileSystem]")

    init {
        fileSystemAdapter.makeDir(usersPath)
    }

    override fun saveUser(user: User): Result<User> =
        fileSystemAdapter.saveObj(usersPath, user.id, UserJsonifier(user).toJson()).mapCatching { user }


    override fun getUser(userId: String): Result<User> = fileSystemAdapter.getObj(usersPath, userId).mapCatching {
        User(it.getString("id"), it.getString("name"), it.getString("surname"))
    }
}

fun UserFileSystemAdapter(fileSystemAdapter: FileSystemAdapter) = UserFileSystemAdapterImpl(fileSystemAdapter)