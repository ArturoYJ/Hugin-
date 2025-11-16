package com.hugin_munin.api.infrastructure.api.routes

import com.hugin_munin.api.application.services.RegistroBajaService
import com.hugin_munin.api.domain.models.RegistroBaja
import com.hugin_munin.api.infrastructure.api.dto.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.registroBajaRouting(registroBajaService: RegistroBajaService) {
    route("/registro-baja") {

        get {
            val registros = registroBajaService.getAllRegistros()
            val responses = registros.map { it.toResponse() }
            call.respond(HttpStatusCode.OK, responses)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "ID inválido")

            val registro = registroBajaService.getRegistroById(id)
            if (registro != null) {
                call.respond(HttpStatusCode.OK, registro.toResponse())
            } else {
                call.respond(HttpStatusCode.NotFound, "Registro de baja no encontrado")
            }
        }

        get("/especimen/{especimenId}") {
            val especimenId = call.parameters["especimenId"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "ID de especimen inválido")

            val registro = registroBajaService.getRegistroByEspecimenId(especimenId)
            if (registro != null) {
                call.respond(HttpStatusCode.OK, registro.toResponse())
            } else {
                call.respond(HttpStatusCode.NotFound, "Registro de baja no encontrado para el especimen")
            }
        }

        post {
            val request = call.receive<RegistroBajaRequest>()

            val bajaData = RegistroBaja(
                especimenId = request.especimenId,
                causaBajaId = request.causaBajaId,
                responsableId = request.responsableId,
                fechaBaja = request.fechaBaja,
                observacion = request.observacion
            )

            try {
                val nuevoRegistro = registroBajaService.createRegistroBaja(bajaData)
                call.respond(HttpStatusCode.Created, nuevoRegistro.toResponse())
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Error en la solicitud")
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "ID inválido")

            val request = call.receive<RegistroBajaUpdateRequest>()

            val bajaData = RegistroBaja(
                especimenId = 0,
                causaBajaId = request.causaBajaId,
                responsableId = 0,
                fechaBaja = request.fechaBaja,
                observacion = request.observacion
            )

            try {
                val updated = registroBajaService.updateRegistroBaja(id, bajaData)
                if (updated != null) {
                    call.respond(HttpStatusCode.OK, updated.toResponse())
                } else {
                    call.respond(HttpStatusCode.NotFound, "Registro de baja no encontrado")
                }
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Error en la solicitud")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID inválido")

            val deleted = registroBajaService.deleteRegistroBaja(id)
            if (deleted) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, "Registro de baja no encontrado")
            }
        }
    }

    route("/causa-baja") {
        get {
            val causas = registroBajaService.getAllCausasBaja()
            val responses = causas.map {
                CausaBajaResponse(
                    id = it.id!!,
                    nombreCausaBaja = it.nombreCausaBaja
                )
            }
            call.respond(HttpStatusCode.OK, responses)
        }
    }
}

private fun RegistroBaja.toResponse() = RegistroBajaResponse(
    id = this.id!!,
    especimenId = this.especimenId,
    causaBajaId = this.causaBajaId,
    responsableId = this.responsableId,
    fechaBaja = this.fechaBaja,
    observacion = this.observacion
)