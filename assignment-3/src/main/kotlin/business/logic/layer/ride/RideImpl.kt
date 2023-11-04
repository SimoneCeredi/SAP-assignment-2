package business.logic.layer.ride

import business.logic.layer.escooter.Escooter
import business.logic.layer.user.User
import java.util.*

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