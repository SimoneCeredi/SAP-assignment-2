package sap.escooters.layers.infrastructure.data.source

import io.vertx.core.json.JsonObject
import java.io.File

interface DataSourcePort {
    fun saveUser(user: JsonObject)
    fun saveEScooter(escooter: JsonObject)
    fun saveRide(ride: JsonObject)
}

class FileSystemAdapter(val databaseFolder: String) : DataSourcePort {
    val USERS_PATH = "${databaseFolder}${File.separator}users"
    val ESCOOTERS_PATH = "${databaseFolder}${File.separator}escooters"
    val RIDES_PATH = "${databaseFolder}${File.separator}rides"

    fun init() {
        makeDir(databaseFolder)
        makeDir(USERS_PATH)
        makeDir(ESCOOTERS_PATH)
        makeDir(RIDES_PATH)
    }


    override fun saveUser(user: JsonObject) {
        saveObj(USERS_PATH, user.getString("id"), user)
    }

    override fun saveEScooter(escooter: JsonObject) {
        saveObj(ESCOOTERS_PATH, escooter.getString("id"), escooter)
    }

    override fun saveRide(ride: JsonObject) {
        saveObj(RIDES_PATH, ride.getString("id"), ride)
    }

    private fun makeDir(folder: String) {
        try {
            val dir = File(folder)
            dir.mkdir()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun saveObj(path: String, id: String, obj: JsonObject) {
        File("$path${File.separator}${id}.json").writeText(obj.encodePrettily())
    }
}