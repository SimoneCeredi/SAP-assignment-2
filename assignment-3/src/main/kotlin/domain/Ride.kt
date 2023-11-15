package domain

import java.util.*

interface Ride {
    val id: String
    val userId: String
    val escooterId: String
    val startDate: Date
    var endDate: Date?
}

class RideImpl private constructor(
    override val id: String,
    override val userId: String,
    override val escooterId: String,
    override val startDate: Date,
    override var endDate: Date?,
) : Ride {
    companion object {
        fun new(id: String, userId: String, escooterId: String) = RideImpl(id, userId, escooterId, Date(), null)
        fun new(id: String, userId: String, escooterId: String, startDate: Date, endDate: Date?) =
            RideImpl(id, userId, escooterId, startDate, endDate)
    }
}

fun Ride(id: String, userId: String, escooterId: String) = RideImpl.new(id, userId, escooterId)
fun Ride(id: String, userId: String, escooterId: String, startDate: Date, endDate: Date?) =
    RideImpl.new(id, userId, escooterId, startDate, endDate)
