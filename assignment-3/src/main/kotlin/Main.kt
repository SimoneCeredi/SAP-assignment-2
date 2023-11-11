import sap.escooters.layers.application.ApplicationApi
import sap.escooters.layers.business.logic.domain.model.DomainModel
import sap.escooters.layers.infrastructure.data.source.FileSystemAdapter
import sap.escooters.layers.infrastructure.ui.WebUIAdapter

fun main(args: Array<String>) {
    val dataSourceAdapter = FileSystemAdapter("dbase")
    val domainLayer = DomainModel(sequenceOf(dataSourceAdapter))
    val ui = WebUIAdapter(8080)
    val appLayer = ApplicationApi(domainLayer, ui)

    dataSourceAdapter.init()
    ui.init(appLayer)
}