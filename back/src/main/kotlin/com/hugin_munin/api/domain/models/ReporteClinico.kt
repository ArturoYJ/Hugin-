package com.hugin_munin.api.domain.models

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate

@Serializable
data class ReporteClinico(
    val reporteId: Int,
    val diagnostico: String,
    val tratamiento: String?,
    val medicamentos: String?,
    val dosis: String?,
    val frecuenciaTratamiento: String?,
    val fechaProximoControl: LocalDate?,
    val estadoSalud: String?
)