package application

import application.exceptions.UserAlreadyExists
import domain.User
import domain.UserModel
import java.util.logging.Level
import java.util.logging.Logger

interface UserService {
    val userModel: UserModel
    fun registerNewUser(id: String, name: String, surname: String): Result<User>
    fun getUser(id: String): Result<User>
}

class UserServiceImpl(override val userModel: UserModel) : UserService {
    val logger = Logger.getLogger("[UserService]")

    override fun registerNewUser(id: String, name: String, surname: String): Result<User> {
        logger.log(Level.INFO, "Registering new user")
        val user = User(id, name, surname)
        getUser(id).onSuccess { throw UserAlreadyExists() }
        return userModel.addNewUser(user).onSuccess {
            Result.success(user)
        }.onFailure {
            Result.failure<User>(it)
        }
    }

    override fun getUser(id: String): Result<User> {
        TODO("Not yet implemented")
    }
}

fun UserService(userModel: UserModel) = UserServiceImpl(userModel)
