import application.EScooterService
import application.RideService
import application.UserService
import domain.model.UserModel
import domain.model.EScooterModel
import infrastructure.database.file.system.EScooterFileSystemAdapter
import infrastructure.database.file.system.FileSystemAdapter
import infrastructure.database.file.system.UserFileSystemAdapter
import infrastructure.web.RestService
import infrastructure.web.handlers.EScooterHandler
import infrastructure.web.handlers.RideHandler
import infrastructure.web.handlers.UserHandler

class RestServiceLauncher {
    val port = 8080
    val dbaseFolder = "dbase"

    val fileSystemAdapter = FileSystemAdapter(dbaseFolder)

    val userHandler = UserHandler(UserService(UserModel(UserFileSystemAdapter(fileSystemAdapter))))
    val escooterHandler = EScooterHandler(EScooterService(EScooterModel(EScooterFileSystemAdapter(fileSystemAdapter))))
    val rideHandler = RideHandler(RideService())
    
    fun launch() = RestService(port, userHandler, escooterHandler, rideHandler).init()
}