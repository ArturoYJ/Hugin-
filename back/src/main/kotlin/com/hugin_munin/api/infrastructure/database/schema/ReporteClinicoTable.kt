package com.hugin_munin.api.infrastructure.database.schemas

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date

object ReporteClinicoTable : Table("reporte_clinico") {
    // La PK es también FK a la tabla reporte
    val reporteId = integer("id_reporte").references(ReporteTable.id)

    val diagnostico = text("diagnostico")
    val tratamiento = text("tratamiento").nullable()
    val medicamentos = text("medicamentos").nullable()
    val dosis = varchar("dosis", 100).nullable()
    val frecuenciaTratamiento = varchar("frecuencia_tratamiento", 100).nullable()
    val fechaProximoControl = date("fecha_proximo_control").nullable()
    val estadoSalud = varchar("estado_salud", 20).nullable() // 'Crítico', 'Grave', etc.

    override val primaryKey = PrimaryKey(reporteId)
}