package domain

import java.util.*

interface Ride {
    val id: String
    val user: String
    val escooter: String
    val startDate: Date
    var endDate: Date?
}

class RideImpl private constructor(
    override val id: String,
    override val user: String,
    override val escooter: String,
    override val startDate: Date,
    override var endDate: Date?,
) : Ride {
    companion object {
        fun new(id: String, user: String, escooter: String) = RideImpl(id, user, escooter, Date(), null)
        fun new(id: String, user: String, escooter: String, startDate: Date, endDate: Date?) =
            RideImpl(id, user, escooter, startDate, endDate)
    }
}

fun Ride(id: String, user: String, escooter: String) = RideImpl.new(id, user, escooter)
fun Ride(id: String, user: String, escooter: String, startDate: Date, endDate: Date?) =
    RideImpl.new(id, user, escooter, startDate, endDate)
