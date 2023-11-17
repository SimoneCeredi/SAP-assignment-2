package application

import domain.User

interface UserService {
    fun addUser(id: String, name: String, surname: String)
    fun getUser(id: String): Result<User>
}

class UserServiceImpl : UserService {
    override fun addUser(id: String, name: String, surname: String) {
        TODO("Not yet implemented")
    }

    override fun getUser(id: String): Result<User> {
        TODO("Not yet implemented")
    }
}

fun UserService() = UserServiceImpl()
