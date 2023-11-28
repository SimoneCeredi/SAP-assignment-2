package infrastructure.database.file.system

import domain.EScooter
import infrastructure.database.EScooterRepository
import infrastructure.database.file.system.jsonifier.EScooterJsonifier

interface EScooterFileSystemAdapter : EScooterRepository {
    val fileSystemAdapter: FileSystemAdapter
}


class EScooterFileSystemAdapterImpl(override val fileSystemAdapter: FileSystemAdapter) : EScooterFileSystemAdapter {
    private val path = "escooters"

    init {
        fileSystemAdapter.makeDir(path)
    }

    override fun saveEScooter(escooter: EScooter): Result<EScooter> =
        fileSystemAdapter.saveObj(path, escooter.id, EScooterJsonifier(escooter).toJson()).mapCatching { escooter }

    override fun getEScooter(escooterId: String): Result<EScooter> = fileSystemAdapter.getObj(path, escooterId).mapCatching {
        EScooter(it.getString("id"))
    }
}

fun EScooterFileSystemAdapter(fileSystemAdapter: FileSystemAdapter) = EScooterFileSystemAdapterImpl(fileSystemAdapter)