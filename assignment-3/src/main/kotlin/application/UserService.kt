package application

import domain.User
import java.util.logging.Logger

interface UserService {
    fun addUser(id: String, name: String, surname: String)
    fun getUser(id: String): Result<User>
}

class UserServiceImpl : UserService {
    val logger = Logger.getLogger("[UserService]")
    override fun addUser(id: String, name: String, surname: String) {
        TODO("Not yet implemented")
    }

    override fun getUser(id: String): Result<User> {
        TODO("Not yet implemented")
    }
}

fun UserService() = UserServiceImpl()
