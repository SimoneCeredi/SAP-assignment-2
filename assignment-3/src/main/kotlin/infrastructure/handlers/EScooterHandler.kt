package infrastructure.handlers

import application.EScooterService

interface EScooterHandler {
    val escooterService: EScooterService
}

class EScooterHandlerImpl(override val escooterService: EScooterService) : EScooterHandler

fun EScooterHandler(escooterService: EScooterService) = EScooterHandlerImpl(escooterService)