package sap.escooters.layers.business.logic.location

interface Location {
    val longitude: Double
    val latitude: Double
}

data class LocationImpl(override val longitude: Double, override val latitude: Double) : Location

fun Location(longitude: Double, latitude: Double) = LocationImpl(longitude, latitude)
