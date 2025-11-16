package com.hugin_munin.api.infrastructure.api.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class EspecimenUpdateRequest(
    val nombreEspecimen: String,
    val fechaIngreso: LocalDate,
    val origenAltaId: Int,
    val procedencia: String?,
    val observacion: String?
)