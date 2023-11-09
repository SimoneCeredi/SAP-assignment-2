package sap.escooters.layers.business.logic.ride

import sap.escooters.layers.business.logic.escooter.EScooter
import sap.escooters.layers.business.logic.user.User
import java.util.*

interface Ride {
    val id: String
    val startDate: Date
    var endDate: Date?
    val user: User
    val escooter: EScooter

    fun isOngoing(): Boolean

}

class RideImpl(
    override val id: String,
    override val user: User,
    override val escooter: EScooter
) : Ride {

    override val startDate: Date = Date()
    override var endDate: Date? = null
    override fun isOngoing(): Boolean {
        return this.endDate == null
    }
}

fun Ride(id: String, user: User, escooter: EScooter) =
    RideImpl(id, user, escooter)