package infrastructure.database.file.system.jsonifier

import domain.EScooter
import domain.Ride
import domain.User
import io.vertx.core.json.JsonObject

class UserJsonifier(val user: User) {
    fun toJson(): JsonObject = JsonObject().put("id", user.id).put("name", user.name).put("surname", user.surname)
}

class EScooterJsonifier(val eScooter: EScooter) {
    fun toJson(): JsonObject = JsonObject().put("id", eScooter.id)
}

class RideJsonifier(val ride: Ride) {
    fun toJson(): JsonObject =
        JsonObject().put("id", ride.id).put("userId", ride.userId).put("escooterId", ride.escooterId)
            .put("startDate", ride.startDate.toString()).put("endDate", ride.endDate.toString()).putNull("location")
}


