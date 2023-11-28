package infrastructure.database

import domain.User

interface UserRepository {
    fun saveUser(user: User): Result<User>
    fun getUser(userId: String): Result<User>
}