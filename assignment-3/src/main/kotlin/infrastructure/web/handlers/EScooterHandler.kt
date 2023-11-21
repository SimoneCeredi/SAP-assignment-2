package infrastructure.web.handlers

import application.EScooterService
import application.exceptions.EScooterAlreadyExists
import infrastructure.database.file.system.jsonifier.EScooterJsonifier
import infrastructure.web.sendReply
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import java.util.logging.Level
import java.util.logging.Logger

interface EScooterHandler {
    fun registerNewEscooter(context: RoutingContext)
    fun getEscooter(context: RoutingContext)

    val escooterService: EScooterService
}

class EScooterHandlerImpl(override val escooterService: EScooterService) : EScooterHandler {

    private val logger: Logger = Logger.getLogger("[EScooterHandler]")
    override fun registerNewEscooter(context: RoutingContext) {
        logger.log(Level.INFO, "New escooter registration request")
        context.body().asJsonObject()?.apply {
            logger.log(Level.INFO, encodePrettily())
            val _id: String? = getString("id")
            _id?.let { id ->
                escooterService.registerNewEScooter(id)
            }?.fold(onSuccess = { context.sendReply(JsonObject().put("result", "ok")) }, onFailure = { exception ->
                when (exception) {
                    EScooterAlreadyExists() -> context.sendReply(
                        JsonObject().put(
                            "result", "escooter-id-already-exists"
                        )
                    )

                    else -> context.sendReply(JsonObject().put("result", "error-saving-escooter"))
                }
            })
        } ?: context.sendReply(JsonObject().put("result", "ERROR: some-fields-were-null"))
    }

    override fun getEscooter(context: RoutingContext) {
        logger.log(Level.INFO, "Get escooter request")
        context.apply {
            logger.log(Level.INFO, currentRoute().path)
            val _id: String? = pathParam("escooterId")
            _id?.let {
                escooterService.getEscooter(it)
            }?.onSuccess { escooter ->
                context.sendReply(
                    JsonObject().put("result", "ok").put("escooter", EScooterJsonifier(escooter).toJson())
                )
            }?.onFailure {
                context.sendReply(JsonObject().put("result", "escooter-not-found"))
            } ?: context.sendReply(JsonObject().put("result", "ERROR: some-fields-were-null"))
        }
    }
}

fun EScooterHandler(escooterService: EScooterService) = EScooterHandlerImpl(escooterService)