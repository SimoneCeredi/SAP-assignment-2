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

}