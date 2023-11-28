package domain

import org.bson.codecs.pojo.annotations.BsonId

interface EScooter {
    val id: String
}

data class EScooterImpl(@BsonId override val id: String) : EScooter

fun EScooter(id: String) = EScooterImpl(id)
