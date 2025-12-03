package com.example.examen

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val lasPreferencias = PreferenciasTema(application)
    private val elDao = BaseDatosProvider.db.elDao()

    //TEMA
    var esTemaOscuro by mutableStateOf(false)
        private set

    init {
        // Vigilar si cambia el tema en DataStore
        viewModelScope.launch {
            lasPreferencias.esOscuroFlow.collect { valorGuardado ->
                esTemaOscuro = valorGuardado
            }
        }
    }

    fun cambiarElTema(nuevoValor: Boolean) {
        viewModelScope.launch {
            lasPreferencias.cambiarTema(nuevoValor)
        }
    }

    //FORMULARIO
    // Variables para los TextFields
    var campoNombre by mutableStateOf("")
    var campoCorreo by mutableStateOf("")
    var campoEdad by mutableStateOf("")

    // Variable para mostrar la lista de usuarios cuando le piquen al boton
    var listaUsuariosMostrar by mutableStateOf(listOf<Usuario>())

    fun guardarDatos() {
        // Una corrutina para guardar en segundo plano
        viewModelScope.launch {
            val nuevoUsuario = Usuario(
                nombre = campoNombre,
                correo = campoCorreo,
                edad = campoEdad
            )
            elDao.guardarUsuario(nuevoUsuario)
            // Limpiamos los campos para que se vea bien
            campoNombre = ""
            campoCorreo = ""
            campoEdad = ""
        }
    }

    fun mostrarDatos() {
        viewModelScope.launch {
            // Jalamos todo de la base de datos
            listaUsuariosMostrar = elDao.obtenerTodos()
        }
    }
}