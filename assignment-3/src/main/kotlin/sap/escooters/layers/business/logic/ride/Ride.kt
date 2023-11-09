package sap.escooters.layers.business.logic.ride

import io.vertx.core.json.JsonObject
import sap.escooters.layers.business.logic.escooter.EScooter
import sap.escooters.layers.business.logic.user.User
import java.util.*

interface Ride {
    val id: String
    val startDate: Date
    var endDate: Date?
    val user: User
    val escooter: EScooter

    fun end()
    fun isOngoing(): Boolean
    fun toJson(): JsonObject

}

class RideImpl(
    override val id: String, override val user: User, override val escooter: EScooter
) : Ride {

    override val startDate: Date = Date()
    override var endDate: Date? = null
    override fun end() {
        endDate = Date()
    }

    override fun isOngoing(): Boolean {
        return this.endDate == null
    }

    override fun toJson(): JsonObject {
        return JsonObject().put("id", id).put("userId", user.id).put("escooterId", escooter.id)
            .put("startDate", startDate.toString()).put("endDate", endDate.toString()).putNull("location")
    }
}

fun Ride(id: String, user: User, escooter: EScooter) = RideImpl(id, user, escooter)