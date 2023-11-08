package business.logic.layer.user

interface User {
    val id: String
    val name: String
    val surname: String
}

data class UserImpl(override val id: String, override val name: String, override val surname: String) : User {}
