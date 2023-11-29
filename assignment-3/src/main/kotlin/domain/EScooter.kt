package domain

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

interface EScooter {
    val id: String
}

data class EScooterImpl(override val id: String) : EScooter

data class MongoEScooter(
    @BsonId val objectId: ObjectId?,
    override val id: String
) : EScooter

fun EScooter(id: String) = EScooterImpl(id)
