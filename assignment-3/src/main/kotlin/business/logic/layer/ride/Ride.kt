package business.logic.layer.ride

import business.logic.layer.escooter.Escooter
import business.logic.layer.user.User
import java.util.*

interface Ride {
    val id: String
    val startDate: Date
    var endDate: Date?
    val user: User
    val EScooter: Escooter

    fun isOngoing(): Boolean

}

class RideImpl(
    override val id: String,
    override val startDate: Date,
    override var endDate: Date?,
    override val user: User,
    override val EScooter: Escooter
) : Ride {
    override fun isOngoing(): Boolean {
        return this.endDate == null
    }
}
