package business.logic.layer.ride

import business.logic.layer.escooter.EScooter
import business.logic.layer.user.User
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
    override val startDate: Date,
    override var endDate: Date?,
    override val user: User,
    override val escooter: EScooter
) : Ride {
    override fun isOngoing(): Boolean {
        return this.endDate == null
    }
}

fun Ride(id: String, startDate: Date, endDate: Date?, user: User, escooter: EScooter) =
    RideImpl(id, startDate, endDate, user, escooter)