package business.logic.layer.user

data class UserImpl(override val id: String, override val name: String, override val surname: String) : User {
}