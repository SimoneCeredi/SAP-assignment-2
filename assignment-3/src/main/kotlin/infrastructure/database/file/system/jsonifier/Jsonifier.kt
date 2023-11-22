package infrastructure.database.file.system.jsonifier

import domain.EScooter
import domain.Ride
import domain.User
import io.vertx.core.json.JsonObject
import java.time.format.DateTimeFormatter

class UserJsonifier(val user: User) {
    fun toJson(): JsonObject = JsonObject().put("id", user.id).put("name", user.name).put("surname", user.surname)
}

class EScooterJsonifier(private val eScooter: EScooter) {
    fun toJson(): JsonObject = JsonObject().put("id", eScooter.id)
}

class RideJsonifier(val ride: Ride) {
    fun toJson(): JsonObject =
        JsonObject().put("id", ride.id).put("userId", ride.userId).put("escooterId", ride.escooterId)
            .put("startDate", ride.startDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .put("endDate", ride.endDate?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .putNull("location")
}


