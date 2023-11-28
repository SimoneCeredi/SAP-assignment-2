import application.EScooterService
import application.RideService
import application.UserService
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoClient
import domain.EScooter
import domain.model.EScooterModel
import domain.model.RideModel
import domain.model.UserModel
import infrastructure.database.file.system.FileSystemAdapter
import infrastructure.database.file.system.RideFileSystemAdapter
import infrastructure.database.file.system.UserFileSystemAdapter
import infrastructure.database.mongo.EScooterMongoRepository
import infrastructure.web.RestService
import infrastructure.web.handlers.EScooterHandler
import infrastructure.web.handlers.RideHandler
import infrastructure.web.handlers.UserHandler
import org.bson.UuidRepresentation
import org.bson.codecs.UuidCodec
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider

class RestServiceLauncher {


    fun launch() {
        val port = 8080
        val dbaseFolder = "dbase"
        val connectionString = ConnectionString("mongodb://localhost:27017")
        val settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .codecRegistry(
                CodecRegistries.fromRegistries(
                    CodecRegistries.fromCodecs(UuidCodec(UuidRepresentation.STANDARD)),
                    MongoClientSettings.getDefaultCodecRegistry(),
                    CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
                )
            )
            .build()

        val fileSystemAdapter = FileSystemAdapter(dbaseFolder)

        val userHandler = UserHandler(UserService(UserModel(UserFileSystemAdapter(fileSystemAdapter))))
//        val escooterHandler =
//            EScooterHandler(EScooterService(EScooterModel(EScooterFileSystemAdapter(fileSystemAdapter))))
        val rideHandler = RideHandler(RideService(RideModel(RideFileSystemAdapter(fileSystemAdapter))))

        val client = MongoClient.create(settings)
        val db = client.getDatabase("escooter_service_db")
        val collection = db.getCollection<EScooter>("escooter")

        val escooterHandler = EScooterHandler(EScooterService(EScooterModel(EScooterMongoRepository(collection))))

        RestService(port, userHandler, escooterHandler, rideHandler).init()

    }
}