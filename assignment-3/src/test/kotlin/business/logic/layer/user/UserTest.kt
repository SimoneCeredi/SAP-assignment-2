package business.logic.layer.user

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import sap.escooters.business.logic.layer.user.User

class UserTest {
    val id = "abc"
    val name = "Mario"
    val surname = "Rossi"
    lateinit var user: User

    @BeforeEach
    fun setUp() {
        user = User(id, name, surname)
    }

    @Test
    fun testGetId() {
        assertEquals(id, user.id)
    }

    @Test
    fun testGetName() {
        assertEquals(name, user.name)
    }

    @Test
    fun testGetSurname() {
        assertEquals(surname, user.surname)
    }
}