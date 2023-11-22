package domain

import java.time.LocalDateTime
import java.util.*

interface Ride {
    val id: String?
    val userId: String
    val escooterId: String
    val startDate: LocalDateTime
    val endDate: LocalDateTime?

    fun setId(id: String): Ride
    fun end(): Ride
}

class RideImpl private constructor(
    override val id: String?,
    override val userId: String,
    override val escooterId: String,
    override val startDate: LocalDateTime,
    override val endDate: LocalDateTime?,
) : Ride {
    override fun setId(id: String) = Ride(id, userId, escooterId, startDate, endDate)
    override fun end(): Ride = Ride(id!!, userId, escooterId, startDate, LocalDateTime.now())

    companion object {
        fun new(userId: String, escooterId: String) = new(null, userId, escooterId)
        private fun new(id: String?, userId: String, escooterId: String) = new(id, userId, escooterId, LocalDateTime.now(), null)
        fun new(id: String?, userId: String, escooterId: String, startDate: LocalDateTime, endDate: LocalDateTime?) =
            RideImpl(id, userId, escooterId, startDate, endDate)
    }
}

fun Ride(userId: String, escooterId: String) = RideImpl.new(userId, escooterId)
fun Ride(id: String, userId: String, escooterId: String, startDate: LocalDateTime, endDate: LocalDateTime?) =
    RideImpl.new(id, userId, escooterId, startDate, endDate)
