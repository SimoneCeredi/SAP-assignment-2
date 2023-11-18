package infrastructure.database.file.system

import io.vertx.core.json.JsonObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

interface FileSystemAdapter {
    val dbFolder: String

    fun saveObj(db: String, id: String, obj: JsonObject): Result<Unit>

    fun getObj(db: String, id: String): Result<JsonObject>

    fun makeDir(dir: String)
}

class FileSystemAdapterImpl(override val dbFolder: String) : FileSystemAdapter {
    override fun saveObj(db: String, id: String, obj: JsonObject): Result<Unit> = runCatching {
        BufferedWriter(FileWriter("$dbFolder${File.separator}$db${File.separator}${id}.json")).use { out ->
            out.write(obj.encodePrettily())
            out.flush()
            out.close()
        }
    }


    override fun getObj(db: String, id: String): Result<JsonObject> = runCatching {
        File("$dbFolder${File.separator}$db${File.separator}${id}.json").bufferedReader().use {
            JsonObject(it.readText())
        }
    }

    override fun makeDir(dir: String) {
        File("$dbFolder${File.separator}$dir").apply {
            if (!exists()) {
                mkdir()
            }
        }
    }
}

fun FileSystemAdapter(dbFolder: String) = FileSystemAdapterImpl(dbFolder)

