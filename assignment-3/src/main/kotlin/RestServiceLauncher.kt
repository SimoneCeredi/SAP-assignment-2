import application.EScooterService
import application.RideService
import application.UserService
import infrastructure.web.RestService
import infrastructure.web.handlers.EScooterHandler
import infrastructure.web.handlers.RideHandler
import infrastructure.web.handlers.UserHandler

class RestServiceLauncher {
    val port = 8080

    val userHandler = UserHandler(UserService())
    val escooterHandler = EScooterHandler(EScooterService())
    val rideHandler = RideHandler(RideService())
    
    fun launch() = RestService(port, userHandler, escooterHandler, rideHandler).init()
}