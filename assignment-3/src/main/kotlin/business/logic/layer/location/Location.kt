package business.logic.layer.location

interface Location {
    val longitude: Double
    val latitude: Double
}

data class LocationImpl(override val longitude: Double, override val latitude: Double) : Location
