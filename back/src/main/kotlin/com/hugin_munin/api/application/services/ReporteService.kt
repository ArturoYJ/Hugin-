package com.hugin_munin.api.application.services

import com.hugin_munin.api.domain.models.Reporte
import com.hugin_munin.api.domain.models.ReporteClinico
import com.hugin_munin.api.domain.ports.ReporteRepository
import com.hugin_munin.api.infrastructure.api.dto.ReporteRequest
import com.hugin_munin.api.infrastructure.api.dto.ReporteResponse
import com.hugin_munin.api.infrastructure.api.dto.ReporteUpdateRequest
import com.hugin_munin.api.infrastructure.api.dto.ReporteClinicoRequest
import com.hugin_munin.api.infrastructure.api.dto.ReporteClinicoResponse
import com.hugin_munin.api.infrastructure.api.dto.ReporteClinicoUpdateRequest

// Solución: Una sola declaración de clase
class ReporteService(
    private val reporteRepository: ReporteRepository
) {

    private val TIPO_REPORTE_CLINICO_ID = 1

    suspend fun getAllReportes(): List<ReporteResponse> {
        return reporteRepository.findAll().map { it.toResponse() }
    }

    suspend fun getReporteById(id: Int): ReporteResponse? {
        return reporteRepository.findById(id)?.toResponse()
    }

    suspend fun getReportesByEspecimen(especimenId: Int): List<ReporteResponse> {
        return reporteRepository.findByEspecimenId(especimenId).map { it.toResponse() }
    }

    suspend fun createReporte(request: ReporteRequest): ReporteResponse {
        val nuevoReporte = Reporte(
            tipoReporteId = request.tipoReporteId,
            especimenId = request.especimenId,
            responsableId = request.responsableId,
            asunto = request.asunto,
            contenido = request.contenido,
            fechaReporte = request.fechaReporte
        )
        return reporteRepository.save(nuevoReporte).toResponse()
    }

    suspend fun updateReporte(id: Int, request: ReporteUpdateRequest): ReporteResponse? {
        val existente = reporteRepository.findById(id) ?: return null

        val actualizado = existente.copy(
            tipoReporteId = request.tipoReporteId ?: existente.tipoReporteId,
            asunto = request.asunto ?: existente.asunto,
            contenido = request.contenido ?: existente.contenido,
            fechaReporte = request.fechaReporte ?: existente.fechaReporte
        )

        return reporteRepository.update(id, actualizado)?.toResponse()
    }

    suspend fun deleteReporte(id: Int): Boolean {
        return reporteRepository.delete(id)
    }

    suspend fun createReporteClinico(request: ReporteClinicoRequest): ReporteClinicoResponse {

        val reporteBase = Reporte(
            tipoReporteId = TIPO_REPORTE_CLINICO_ID,
            especimenId = request.especimenId,
            responsableId = request.responsableId,
            asunto = request.asunto,
            contenido = request.contenido,
            fechaReporte = request.fechaReporte
        )

        val reporteGuardado = reporteRepository.save(reporteBase)

        val reporteClinico = ReporteClinico(
            reporteId = reporteGuardado.id!!,
            diagnostico = request.diagnostico,
            tratamiento = request.tratamiento,
            medicamentos = request.medicamentos,
            dosis = request.dosis,
            frecuenciaTratamiento = request.frecuenciaTratamiento,
            fechaProximoControl = request.fechaProximoControl,
            estadoSalud = request.estadoSalud
        )

        reporteRepository.saveClinico(reporteClinico)

        return ReporteClinicoResponse(
            id = reporteGuardado.id,
            especimenId = reporteGuardado.especimenId!!,
            responsableId = reporteGuardado.responsableId,
            asunto = reporteGuardado.asunto,
            contenido = reporteGuardado.contenido,
            fechaReporte = reporteGuardado.fechaReporte,
            diagnostico = reporteClinico.diagnostico,
            estadoSalud = reporteClinico.estadoSalud
        )
    }

    suspend fun getReporteClinicoById(id: Int): ReporteClinicoResponse? {
        return reporteRepository.findClinicoById(id)
    }

    suspend fun updateReporteClinico(id: Int, request: ReporteClinicoUpdateRequest): Boolean {
        return reporteRepository.updateClinico(id, request)
    }

    private fun Reporte.toResponse() = ReporteResponse(
        id = this.id!!,
        tipoReporteId = this.tipoReporteId,
        especimenId = this.especimenId ?: 0,
        responsableId = this.responsableId,
        asunto = this.asunto,
        contenido = this.contenido,
        fechaReporte = this.fechaReporte
    )
}