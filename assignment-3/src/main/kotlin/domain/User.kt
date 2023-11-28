package domain

import org.bson.codecs.pojo.annotations.BsonId

interface User {
    val id: String
    val name: String
    val surname: String
}

data class UserImpl(@BsonId override val id: String, override val name: String, override val surname: String) :
    User {
        companion object {
            fun new(id: String, name: String, surname: String) = UserImpl(id, name, surname)
        }
}

fun User(id: String, name: String, surname: String) = UserImpl.new(id, name, surname)
