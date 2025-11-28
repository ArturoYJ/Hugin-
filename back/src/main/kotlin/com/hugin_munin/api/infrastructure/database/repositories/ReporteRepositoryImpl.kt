package com.hugin_munin.api.infrastructure.database.repositories

import com.hugin_munin.api.domain.models.Reporte
import com.hugin_munin.api.domain.models.ReporteTraslado
import com.hugin_munin.api.domain.models.OrigenAlta
import com.hugin_munin.api.domain.models.ReporteClinico
import com.hugin_munin.api.domain.ports.ReporteRepository
import com.hugin_munin.api.infrastructure.api.dto.ReporteClinicoResponse
import com.hugin_munin.api.infrastructure.api.dto.ReporteClinicoUpdateRequest
import com.hugin_munin.api.infrastructure.database.DatabaseFactory.dbQuery
import com.hugin_munin.api.infrastructure.database.schemas.ReporteTable
import com.hugin_munin.api.infrastructure.database.schemas.ReporteTrasladoTable
import com.hugin_munin.api.infrastructure.database.schemas.OrigenAltaTable
import com.hugin_munin.api.infrastructure.database.schemas.ReporteClinicoTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ReporteRepositoryImpl : ReporteRepository {

    private fun toReporte(row: ResultRow) = Reporte(
        id = row[ReporteTable.id],
        tipoReporteId = row[ReporteTable.tipoReporteId],
        especimenId = row[ReporteTable.especimenId],
        responsableId = row[ReporteTable.responsableId],
        asunto = row[ReporteTable.asunto],
        fechaReporte = row[ReporteTable.fechaReporte],
        contenido = row[ReporteTable.contenido]
    )

    override suspend fun findAll(): List<Reporte> = dbQuery {
        ReporteTable.selectAll().map { toReporte(it) }
    }

    override suspend fun findById(id: Int): Reporte? = dbQuery {
        ReporteTable.select { ReporteTable.id eq id }
            .map { toReporte(it) }
            .singleOrNull()
    }

    override suspend fun findByEspecimenId(especimenId: Int): List<Reporte> = dbQuery {
        ReporteTable.select { ReporteTable.especimenId eq especimenId }
            .map { toReporte(it) }
    }

    override suspend fun save(reporte: Reporte): Reporte = dbQuery {
        val insertStatement = ReporteTable.insert {
            it[tipoReporteId] = reporte.tipoReporteId
            it[especimenId] = reporte.especimenId!!
            it[responsableId] = reporte.responsableId
            it[asunto] = reporte.asunto
            it[fechaReporte] = reporte.fechaReporte
            it[contenido] = reporte.contenido
        }
        val id = insertStatement.resultedValues?.singleOrNull()?.get(ReporteTable.id)
        reporte.copy(id = id)
    }

    override suspend fun update(id: Int, reporte: Reporte): Reporte? = dbQuery {
        val rows = ReporteTable.update({ ReporteTable.id eq id }) {
            it[tipoReporteId] = reporte.tipoReporteId
            it[asunto] = reporte.asunto
            it[fechaReporte] = reporte.fechaReporte
            it[contenido] = reporte.contenido
        }
        if (rows > 0) reporte.copy(id = id) else null
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        ReporteTable.deleteWhere { ReporteTable.id eq id } > 0
    }

    override suspend fun saveTraslado(traslado: ReporteTraslado): Unit = dbQuery {
        ReporteTrasladoTable.insert {
            it[reporteId] = traslado.reporteId
            it[areaOrigen] = traslado.areaOrigen
            it[areaDestino] = traslado.areaDestino
            it[ubicacionOrigen] = traslado.ubicacionOrigen
            it[ubicacionDestino] = traslado.ubicacionDestino
            it[motivo] = traslado.motivo
        }
    }

    override suspend fun findTrasladoByReporteId(reporteId: Int): ReporteTraslado? = dbQuery {
        ReporteTrasladoTable.select { ReporteTrasladoTable.reporteId eq reporteId }
            .map { row ->
                ReporteTraslado(
                    reporteId = row[ReporteTrasladoTable.reporteId],
                    areaOrigen = row[ReporteTrasladoTable.areaOrigen],
                    areaDestino = row[ReporteTrasladoTable.areaDestino],
                    ubicacionOrigen = row[ReporteTrasladoTable.ubicacionOrigen],
                    ubicacionDestino = row[ReporteTrasladoTable.ubicacionDestino],
                    motivo = row[ReporteTrasladoTable.motivo]
                )
            }
            .singleOrNull()
    }

    override suspend fun findOrigenAltaById(id: Int): OrigenAlta? = dbQuery {
        OrigenAltaTable.select { OrigenAltaTable.id eq id }
            .map { row ->
                OrigenAlta(
                    id = row[OrigenAltaTable.id],
                    nombre = row[OrigenAltaTable.nombre]
                )
            }
            .singleOrNull()
    }

    override suspend fun saveClinico(clinico: ReporteClinico): Unit = dbQuery {
        ReporteClinicoTable.insert {
            it[idReporte] = clinico.reporteId
            it[diagnostico] = clinico.diagnostico
            it[tratamiento] = clinico.tratamiento
            it[medicamentos] = clinico.medicamentos
            it[dosis] = clinico.dosis
            it[frecuenciaTratamiento] = clinico.frecuenciaTratamiento
            it[fechaProximoControl] = clinico.fechaProximoControl
            it[estadoSalud] = clinico.estadoSalud
        }
    }

    override suspend fun findClinicoById(id: Int): ReporteClinicoResponse? = dbQuery {
        (ReporteTable innerJoin ReporteClinicoTable)
            .select { ReporteTable.id eq id }
            .map { row ->
                ReporteClinicoResponse(
                    id = row[ReporteTable.id],
                    especimenId = row[ReporteTable.especimenId],
                    responsableId = row[ReporteTable.responsableId],
                    asunto = row[ReporteTable.asunto],
                    contenido = row[ReporteTable.contenido],
                    fechaReporte = row[ReporteTable.fechaReporte],
                    diagnostico = row[ReporteClinicoTable.diagnostico],
                    estadoSalud = row[ReporteClinicoTable.estadoSalud]
                )
            }
            .singleOrNull()
    }

    override suspend fun updateClinico(id: Int, clinico: ReporteClinicoUpdateRequest): Boolean = dbQuery {

        // actualiza tabla padre
        ReporteTable.update({ ReporteTable.id eq id }) {
            clinico.asunto?.let { v -> it[asunto] = v }
            clinico.contenido?.let { v -> it[contenido] = v }
            clinico.fechaReporte?.let { v -> it[fechaReporte] = v }
        }

        val filasHijas = ReporteClinicoTable.update({ ReporteClinicoTable.idReporte eq id }) {
            clinico.diagnostico?.let { v -> it[diagnostico] = v }
            clinico.tratamiento?.let { v -> it[tratamiento] = v }
            clinico.medicamentos?.let { v -> it[medicamentos] = v }
            clinico.dosis?.let { v -> it[dosis] = v }
            clinico.frecuenciaTratamiento?.let { v -> it[frecuenciaTratamiento] = v }
            clinico.fechaProximoControl?.let { v -> it[fechaProximoControl] = v }
            clinico.estadoSalud?.let { v -> it[estadoSalud] = v }
        }

        filasHijas > 0
    }
}