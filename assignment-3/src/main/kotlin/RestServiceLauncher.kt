import application.EScooterService
import application.RideService
import application.UserService
import infrastructure.RestService
import infrastructure.handlers.EScooterHandler
import infrastructure.handlers.RideHandler
import infrastructure.handlers.UserHandler

class RestServiceLauncher {
    val port = 8080

    val userHandler = UserHandler(UserService())
    val escooterHandler = EScooterHandler(EScooterService())
    val rideHandler = RideHandler(RideService())
    
    fun launch() = RestService(port, userHandler, escooterHandler, rideHandler).init()
}