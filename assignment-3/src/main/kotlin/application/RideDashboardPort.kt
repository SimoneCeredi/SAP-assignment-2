package application

import domain.Ride

interface RideDashboardPort {
    fun notifyOngoingRidesChanged(ongoingRides: Sequence<Ride>)
}