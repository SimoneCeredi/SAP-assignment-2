package sap.escooters.layers.business.logic.ride

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import sap.escooters.layers.business.logic.escooter.EScooter
import sap.escooters.layers.business.logic.user.User

class RideTest {

    val ride = Ride("ride1", User("user1", "foo", "bar"), EScooter("baz"))

    @Test
    fun toJson() {
        assertDoesNotThrow { ride.toJson() }
        println(ride.toJson().toString())
    }
}