package infrastructure.handlers

import application.UserService
import io.vertx.ext.web.RoutingContext

interface UserHandler {
    val userService: UserService
    fun registerNewUser(context: RoutingContext)
    fun getUser(context: RoutingContext)
}

class UserHandlerImpl(override val userService: UserService) : UserHandler {
    override fun registerNewUser(context: RoutingContext) {
        TODO("Not yet implemented")
    }

    override fun getUser(context: RoutingContext) {
        TODO("Not yet implemented")
    }
}

fun UserHandler(userService: UserService) = UserHandlerImpl(userService)
