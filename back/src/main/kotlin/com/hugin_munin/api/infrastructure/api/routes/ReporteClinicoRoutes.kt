package com.hugin_munin.api.infrastructure.api.routes

import com.hugin_munin.api.application.services.ReporteService
import com.hugin_munin.api.infrastructure.api.dto.ReporteClinicoRequest
import com.hugin_munin.api.infrastructure.api.dto.ReporteClinicoUpdateRequest
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.reporteClinicoRouting(reporteService: ReporteService) {
    route("/reportes/clinico") {

        post {
            try {
                val request = call.receive<ReporteClinicoRequest>()
                val created = reporteService.createReporteClinico(request)
                call.respond(HttpStatusCode.Created, created)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "ID inválido")

            val reporte = reporteService.getReporteClinicoById(id)
            if (reporte != null) {
                call.respond(HttpStatusCode.OK, reporte)
            } else {
                call.respond(HttpStatusCode.NotFound, "Reporte clínico no encontrado")
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "ID inválido")

            try {
                val request = call.receive<ReporteClinicoUpdateRequest>()
                val actualizado = reporteService.updateReporteClinico(id, request)

                if (actualizado) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Reporte clínico actualizado"))
                } else {
                    call.respond(HttpStatusCode.NotFound, "No se pudo actualizar")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }
    }
}