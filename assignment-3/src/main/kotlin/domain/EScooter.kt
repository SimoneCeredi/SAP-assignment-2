package domain

interface EScooter {
    val id: String
}

data class EScooterImpl(override val id: String) : EScooter

fun EScooter(id: String) = EScooterImpl(id)
