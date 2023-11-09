package sap.escooters.layers.business.logic.user

import io.vertx.core.json.JsonObject

interface User {
    val id: String
    val name: String
    val surname: String

    fun toJson(): JsonObject
}

data class UserImpl(override val id: String, override val name: String, override val surname: String) : User {
    override fun toJson(): JsonObject {
        return JsonObject().put("id", id).put("name", name).put("surname", surname)
    }

}

fun User(id: String, name: String, surname: String) = UserImpl(id, name, surname)
