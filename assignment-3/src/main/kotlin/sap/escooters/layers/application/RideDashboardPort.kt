package sap.escooters.layers.application

interface RideDashboardPort {

    fun notifyNumOngoingRidesChanged(nOngoingRides: Int): Unit
}
