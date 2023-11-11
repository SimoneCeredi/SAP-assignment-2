package sap.escooters.layers.infrastructure.ui

import io.vertx.core.Vertx
import sap.escooters.layers.application.ApplicationApi
import sap.escooters.layers.application.RideDashboardPort
import java.util.logging.Logger

class WebUIAdapter(private val port: Int): RideDashboardPort {
    private val logger: Logger = Logger.getLogger("[EScooter Server]")
    private lateinit var server: EScooterServer

    fun init(applicationApi: ApplicationApi) {
        val vertx = Vertx.vertx()
        server = EScooterServer(port, applicationApi)
        vertx.deployVerticle(server)
    }
    override fun notifyNumOngoingRidesChanged(nOngoingRides: Int) {
        server.notifyNumOngoingRidesChanged(nOngoingRides)
    }
}