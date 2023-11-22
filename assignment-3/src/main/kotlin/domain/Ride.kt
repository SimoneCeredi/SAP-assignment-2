package domain

import java.time.LocalDateTime
import java.util.*

interface Ride {
    val id: String?
    val userId: String
    val escooterId: String
    val startDate: LocalDateTime
    var endDate: LocalDateTime?

    fun setId(id: String): Ride
}

class RideImpl private constructor(
    override val id: String?,
    override val userId: String,
    override val escooterId: String,
    override val startDate: LocalDateTime,
    override var endDate: LocalDateTime?,
) : Ride {
    override fun setId(id: String) = Ride(id, userId, escooterId, startDate, endDate)
    companion object {
        fun new(userId: String, escooterId: String) = RideImpl.new(null, userId, escooterId)
        fun new(id: String?, userId: String, escooterId: String) = RideImpl(id, userId, escooterId, LocalDateTime.now(), null)
        fun new(id: String?, userId: String, escooterId: String, startDate: LocalDateTime, endDate: LocalDateTime?) =
            RideImpl(id, userId, escooterId, startDate, endDate)
    }
}

fun Ride(userId: String, escooterId: String) = RideImpl.new(userId, escooterId)
fun Ride(id: String, userId: String, escooterId: String) = RideImpl.new(id, userId, escooterId)
fun Ride(id: String, userId: String, escooterId: String, startDate: LocalDateTime, endDate: LocalDateTime?) =
    RideImpl.new(id, userId, escooterId, startDate, endDate)
