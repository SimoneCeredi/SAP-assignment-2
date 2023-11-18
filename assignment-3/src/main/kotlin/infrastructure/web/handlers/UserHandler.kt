package infrastructure.web.handlers

import application.UserService
import application.exceptions.UserAlreadyExists
import infrastructure.web.sendReply
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import java.util.logging.Level
import java.util.logging.Logger

interface UserHandler {
    val userService: UserService
    fun registerNewUser(context: RoutingContext)
    fun getUser(context: RoutingContext)
}

class UserHandlerImpl(override val userService: UserService) : UserHandler {
    val logger = Logger.getLogger("[UserHandler]")
    override fun registerNewUser(context: RoutingContext) {
        logger.log(Level.INFO, "New user registration request")
        context.body().asJsonObject().apply {
            logger.log(Level.INFO, this.encodePrettily())
            val _id: String? = getString("id")
            val _name: String? = getString("name")
            val _surname: String? = getString("surname")
            _id?.let { id ->
                _name?.let { name ->
                    _surname?.let { surname ->
                        userService.registerNewUser(id, name, surname)
                    }
                }
            }?.fold(onSuccess = { context.sendReply(JsonObject().put("result", "ok")) },
                onFailure = { exception ->  when(exception) {
                    UserAlreadyExists() -> context.sendReply(JsonObject().put("result", "user-id-already-existing"))
                    else -> context.sendReply(JsonObject().put("result", "error-saving-user"))
                } })
                ?: context.sendReply(JsonObject().put("result", "ERROR: some-fields-were-null"))
        }
    }

    override fun getUser(context: RoutingContext) {
        TODO("Not yet implemented")
    }
}

fun UserHandler(userService: UserService) = UserHandlerImpl(userService)
