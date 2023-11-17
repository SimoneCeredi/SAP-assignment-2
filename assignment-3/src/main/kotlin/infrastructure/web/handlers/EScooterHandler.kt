package infrastructure.web.handlers

import application.EScooterService
import java.util.logging.Logger

interface EScooterHandler {
    val escooterService: EScooterService
}

class EScooterHandlerImpl(override val escooterService: EScooterService) : EScooterHandler {

    val logger = Logger.getLogger("[EScooterHandler]")
}

fun EScooterHandler(escooterService: EScooterService) = EScooterHandlerImpl(escooterService)