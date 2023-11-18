package infrastructure.database.file.system

import io.vertx.core.json.JsonObject
import java.io.File

interface FileSystemAdapter {
    val dbFolder: String
    fun saveObj(db: String, id: String, obj: JsonObject): Result<Unit>

}

class FileSystemAdapterImpl(override val dbFolder: String) : FileSystemAdapter {
    override fun saveObj(db: String, id: String, obj: JsonObject): Result<Unit> = runCatching {
        File("$dbFolder${File.separator}$db${File.separator}${id}.json").bufferedWriter().use { out ->
            out.write(obj.encodePrettily())
            out.flush()
            out.close()
        }
    }
}

fun FileSystemAdapter(dbFolder: String) = FileSystemAdapterImpl(dbFolder)

