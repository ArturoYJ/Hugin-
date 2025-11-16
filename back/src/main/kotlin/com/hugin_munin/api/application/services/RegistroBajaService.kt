package com.hugin_munin.api.application.services

import com.hugin_munin.api.domain.models.RegistroBaja
import com.hugin_munin.api.domain.ports.*

class RegistroBajaService(
    private val registroBajaRepository: RegistroBajaRepository,
    private val especimenRepository: EspecimenRepository,
    private val causaBajaRepository: CausaBajaRepository
) {

    suspend fun getAllRegistros(): List<RegistroBaja> {
        return registroBajaRepository.findAll()
    }

    suspend fun getRegistroById(id: Int): RegistroBaja? {
        return registroBajaRepository.findById(id)
    }

    suspend fun getRegistroByEspecimenId(especimenId: Int): RegistroBaja? {
        return registroBajaRepository.findByEspecimenId(especimenId)
    }

    suspend fun createRegistroBaja(baja: RegistroBaja): RegistroBaja {
        val especimen = especimenRepository.findById(baja.especimenId)
            ?: throw IllegalArgumentException("Especimen con ID ${baja.especimenId} no encontrado")

        if (!especimen.activo) {
            throw IllegalArgumentException("El especimen ya está dado de baja")
        }

        val causaBaja = causaBajaRepository.findById(baja.causaBajaId)
            ?: throw IllegalArgumentException("Causa de baja con ID ${baja.causaBajaId} no encontrada")

        val existingBaja = registroBajaRepository.findByEspecimenId(baja.especimenId)
        if (existingBaja != null) {
            throw IllegalArgumentException("Ya existe un registro de baja para este especimen")
        }

        val especimenInactivo = especimen.copy(activo = false)
        especimenRepository.update(especimen.id!!, especimenInactivo)

        return registroBajaRepository.save(baja)
    }

    suspend fun updateRegistroBaja(id: Int, baja: RegistroBaja): RegistroBaja? {
        val existing = registroBajaRepository.findById(id) ?: return null

        val causaBaja = causaBajaRepository.findById(baja.causaBajaId)
            ?: throw IllegalArgumentException("Causa de baja con ID ${baja.causaBajaId} no encontrada")

        val updated = existing.copy(
            causaBajaId = baja.causaBajaId,
            fechaBaja = baja.fechaBaja,
            observacion = baja.observacion
        )

        return registroBajaRepository.update(id, updated)
    }

    suspend fun deleteRegistroBaja(id: Int): Boolean {
        val baja = registroBajaRepository.findById(id) ?: return false

        val especimen = especimenRepository.findById(baja.especimenId)
        if (especimen != null) {
            val especimenActivo = especimen.copy(activo = true)
            especimenRepository.update(especimen.id!!, especimenActivo)
        }

        return registroBajaRepository.delete(id)
    }

    suspend fun getAllCausasBaja(): List<com.hugin_munin.api.domain.models.CausaBaja> {
        return causaBajaRepository.findAll()
    }
}