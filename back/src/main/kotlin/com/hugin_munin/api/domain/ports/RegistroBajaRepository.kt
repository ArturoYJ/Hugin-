package com.hugin_munin.api.domain.ports
import com.hugin_munin.api.domain.models.RegistroBaja

interface RegistroBajaRepository {
    suspend fun findAll(): List<RegistroBaja>
    suspend fun findById(id: Int): RegistroBaja?
    suspend fun findByEspecimenId(especimenId: Int): RegistroBaja?
    suspend fun save(baja: RegistroBaja): RegistroBaja
    suspend fun update(id: Int, baja: RegistroBaja): RegistroBaja?
    suspend fun delete(id: Int): Boolean
}
