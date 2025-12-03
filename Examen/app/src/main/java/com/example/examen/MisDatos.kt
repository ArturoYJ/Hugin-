package com.example.examen

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

    //TABLA
@Entity(tableName = "tabla_usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val correo: String,
    val edad: String
)

@Dao
interface UsuarioDao {
    @Insert
    suspend fun guardarUsuario(usuario: Usuario)

    @Query("SELECT * FROM tabla_usuarios")
    suspend fun obtenerTodos(): List<Usuario>
}

@Database(entities = [Usuario::class], version = 1)
abstract class MiBaseDeDatos : RoomDatabase() {
    abstract fun elDao(): UsuarioDao
}

// Un singleton medio hecho a la rápida para que no truene xd
object BaseDatosProvider {
    lateinit var db: MiBaseDeDatos
    fun inicializar(context: Context) {
        db = Room.databaseBuilder(
            context.applicationContext,
            MiBaseDeDatos::class.java,
            "mi_base_chida.db"
        ).build()
    }
}

//DATASTORE
val Context.miDataStore by preferencesDataStore(name = "ajustes_tema")

class PreferenciasTema(private val context: Context) {
    private val LLAVE_OSCURO = booleanPreferencesKey("es_tema_oscuro")

    // Para leer el valor esto devuelve un flow que es como un tubo de datos
    val esOscuroFlow: Flow<Boolean> = context.miDataStore.data
        .map { preferencias ->
            preferencias[LLAVE_OSCURO] ?: false // Por defecto falso (tema claro)
        }

    suspend fun cambiarTema(esOscuro: Boolean) {
        context.miDataStore.edit { preferencias ->
            preferencias[LLAVE_OSCURO] = esOscuro
        }
    }
}