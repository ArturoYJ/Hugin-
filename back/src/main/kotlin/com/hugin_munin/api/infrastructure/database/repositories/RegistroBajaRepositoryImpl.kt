package com.hugin_munin.api.infrastructure.database.repositories

import com.hugin_munin.api.domain.models.RegistroBaja
import com.hugin_munin.api.domain.ports.RegistroBajaRepository
import com.hugin_munin.api.infrastructure.database.DatabaseFactory.dbQuery
import com.hugin_munin.api.infrastructure.database.schemas.RegistroBajaTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class RegistroBajaRepositoryImpl : RegistroBajaRepository {

    private fun resultRowToRegistroBaja(row: ResultRow) = RegistroBaja(
        id = row[RegistroBajaTable.id],
        especimenId = row[RegistroBajaTable.especimenId],
        causaBajaId = row[RegistroBajaTable.causaBajaId],
        responsableId = row[RegistroBajaTable.responsableId],
        fechaBaja = row[RegistroBajaTable.fechaBaja],
        observacion = row[RegistroBajaTable.observacion]
    )

    override suspend fun findAll(): List<RegistroBaja> = dbQuery {
        RegistroBajaTable.selectAll()
            .map(::resultRowToRegistroBaja)
    }

    override suspend fun findById(id: Int): RegistroBaja? = dbQuery {
        RegistroBajaTable.select { RegistroBajaTable.id eq id }
            .map(::resultRowToRegistroBaja)
            .singleOrNull()
    }

    override suspend fun findByEspecimenId(especimenId: Int): RegistroBaja? = dbQuery {
        RegistroBajaTable.select { RegistroBajaTable.especimenId eq especimenId }
            .map(::resultRowToRegistroBaja)
            .singleOrNull()
    }

    override suspend fun save(baja: RegistroBaja): RegistroBaja = dbQuery {
        val insertStatement = RegistroBajaTable.insert {
            it[especimenId] = baja.especimenId
            it[causaBajaId] = baja.causaBajaId
            it[responsableId] = baja.responsableId
            it[fechaBaja] = baja.fechaBaja
            it[observacion] = baja.observacion
        }
        val id = insertStatement.resultedValues?.singleOrNull()?.get(RegistroBajaTable.id)
            ?: throw IllegalStateException("No se pudo crear el registro de baja")
        baja.copy(id = id)
    }

    override suspend fun update(id: Int, baja: RegistroBaja): RegistroBaja? = dbQuery {
        val rowsAffected = RegistroBajaTable.update({ RegistroBajaTable.id eq id }) {
            it[causaBajaId] = baja.causaBajaId
            it[fechaBaja] = baja.fechaBaja
            it[observacion] = baja.observacion
        }
        if (rowsAffected > 0) baja.copy(id = id) else null
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        RegistroBajaTable.deleteWhere { RegistroBajaTable.id eq id } > 0
    }
}