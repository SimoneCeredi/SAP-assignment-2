package infrastructure.database.file.system

import application.exceptions.RideIdIsNull
import domain.Ride
import infrastructure.database.RideDatabaseAdapter
import infrastructure.database.file.system.jsonifier.RideJsonifier
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.logging.Level
import java.util.logging.Logger

interface RideFileSystemAdapter : RideDatabaseAdapter {
    val fileSystemAdapter: FileSystemAdapter
}

class RideFileSystemAdapterImpl(override val fileSystemAdapter: FileSystemAdapter) : RideFileSystemAdapter {
    private val path = "rides"
    private val logger = Logger.getLogger("[RideFileSystem]")

    init {
        fileSystemAdapter.makeDir(path)
    }

    override fun saveRide(ride: Ride): Result<Ride> =
        ride.id?.let {
            fileSystemAdapter.saveObj(path, ride.id!!, RideJsonifier(ride).toJson()).mapCatching { ride }
        } ?: Result.failure(RideIdIsNull())

    override fun getRide(rideId: String): Result<Ride> = fileSystemAdapter.getObj(path, rideId).mapCatching {
        Ride(
            it.getString("id"),
            it.getString("userId"),
            it.getString("escooterId"),
            LocalDateTime.parse(it.getString("startDate"), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            it.getString("endDate").let {
                if (it == null || it == "null") {
                    null
                } else {
                    LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                }
            }
        )
    }

    override fun getNextRideId(): String {
        val ret = File("${fileSystemAdapter.dbFolder}${File.separator}$path").listFiles()?.let { files ->
            "ride-${
                files.mapNotNull { file -> Regex("""ride-(\d+)\.json""").find(file.name)?.groupValues?.get(1)?.toInt() }
                    .maxByOrNull { it }?.inc()
            }"
        } ?: "ride-1"
        logger.log(Level.INFO, ret)
        return ret
    }

}

fun RideFileSystemAdapter(fileSystemAdapter: FileSystemAdapter) = RideFileSystemAdapterImpl(fileSystemAdapter)