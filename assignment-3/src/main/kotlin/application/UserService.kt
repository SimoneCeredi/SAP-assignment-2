package application

import domain.User
import java.util.logging.Logger

interface UserService {
    fun registerNewUser(id: String, name: String, surname: String): Result<User>
    fun getUser(id: String): Result<User>
}

class UserServiceImpl : UserService {
    val logger = Logger.getLogger("[UserService]")
    override fun registerNewUser(id: String, name: String, surname: String): Result<User> {
        TODO("Not yet implemented")
    }

    override fun getUser(id: String): Result<User> {
        TODO("Not yet implemented")
    }
}

fun UserService() = UserServiceImpl()
