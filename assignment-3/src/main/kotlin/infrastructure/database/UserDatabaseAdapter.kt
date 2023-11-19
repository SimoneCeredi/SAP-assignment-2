package infrastructure.database

import domain.User

interface UserDatabaseAdapter {
    fun saveUser(user: User): Result<User>
    fun getUser(userId: String): Result<User>
}