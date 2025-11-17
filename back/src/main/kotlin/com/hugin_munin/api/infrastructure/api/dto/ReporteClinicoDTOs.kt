package com.hugin_munin.api.infrastructure.api.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class ReporteClinicoRequest(
    val especimenId: Int,
    val responsableId: Int,
    val asunto: String,
    val contenido: String,
    val fechaReporte: LocalDate,

    val diagnostico: String,
    val tratamiento: String?,
    val medicamentos: String?,
    val dosis: String?,
    val frecuenciaTratamiento: String?,
    val fechaProximoControl: LocalDate?,
    val estadoSalud: String
)

@Serializable
data class ReporteClinicoResponse(
    val id: Int,
    val especimenId: Int,
    val responsableId: Int,
    val asunto: String,
    val contenido: String,
    val fechaReporte: LocalDate,
    // Datos Clínicos
    val diagnostico: String,
    val estadoSalud: String?
)