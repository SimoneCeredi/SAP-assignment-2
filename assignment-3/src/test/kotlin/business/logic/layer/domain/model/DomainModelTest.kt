package business.logic.layer.domain.model

import business.logic.layer.escooter.EScooter
import business.logic.layer.user.User
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DomainModelTest {

    val domainModel = DomainModel()
    val userId = "a"
    val userName = "foo"
    val userSurname = "bar"
    val escooterId = "b"

    @Test
    fun `add new User test`() {
        domainModel.addNewUser(userId, userName, userSurname)
        assertEquals(userId, domainModel.getUser(userId)?.id)
    }

    @Test
    fun `add new EScooter test`() {
        domainModel.addNewEscooter(escooterId)
        assertEquals(escooterId, domainModel.getEscooter(escooterId)?.id)
    }

    @Test
    fun `start new Ride test`() {
        val user = User(userId, userName, userSurname)
        val escooter = EScooter(escooterId)
        val rideId = domainModel.startNewRide(user, escooter)
        assertEquals(1, domainModel.getOngoingRides().size)
        assertEquals(rideId, domainModel.getRide(rideId)?.id)
    }
}