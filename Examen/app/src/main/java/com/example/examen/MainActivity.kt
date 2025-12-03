package com.example.examen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// IMPORTANTE: Agregar esta importación
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        BaseDatosProvider.inicializar(this)

        // Opcional: Mantener el splash screen visible si necesitas cargar algo inicial
        // splashScreen.setKeepOnScreenCondition { ... }

        setContent {
            val miViewModel: AppViewModel = viewModel()

            MaterialTheme(
                colorScheme = if (miViewModel.esTemaOscuro) darkColorScheme() else lightColorScheme()
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavegacionPrincipal(miViewModel)
                }
            }
        }
    }
}

@Composable
fun NavegacionPrincipal(viewModel: AppViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") { VistaDashboard(navController) }
        composable("vista_tema") { VistaTema(navController, viewModel) }
        composable("vista_form") { VistaFormulario(navController, viewModel) }
    }
}

//DASHBOARD
@Composable
fun VistaDashboard(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("DASHBOARD PRINCIPAL", style = MaterialTheme.typography.headlineMedium)

        Button(onClick = { navController.navigate("vista_tema") }) {
            Text("ir a cambiar tema")
        }

        Button(onClick = { navController.navigate("vista_form") }) {
            Text("ir al formulario")
        }
    }
}

//CAMBIAR TEMA
@Composable
fun VistaTema(navController: NavController, viewModel: AppViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Boton de regresar obligado
        Button(onClick = { navController.popBackStack() }, modifier = Modifier.align(Alignment.Start)) {
            Text("Dashboard")
        }

        Text("Cambia el tema:")

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Modo Oscuro")
            // Un Switch para cambiar el tema usando el ViewModel
            Switch(
                checked = viewModel.esTemaOscuro,
                onCheckedChange = { nuevoValor ->
                    viewModel.cambiarElTema(nuevoValor)
                }
            )
        }
    }
}

//FORMULARIO
@Composable
fun VistaFormulario(navController: NavController, viewModel: AppViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.popBackStack() }, modifier = Modifier.align(Alignment.Start)) {
            Text("Dashboard")
        }

        Text("Datos del Usuario", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = viewModel.campoNombre,
            onValueChange = { viewModel.campoNombre = it },
            label = { Text("tu nombre") }
        )
        OutlinedTextField(
            value = viewModel.campoCorreo,
            onValueChange = { viewModel.campoCorreo = it },
            label = { Text("tu correo @gmail") }
        )
        OutlinedTextField(
            value = viewModel.campoEdad,
            onValueChange = { viewModel.campoEdad = it },
            label = { Text("tu edad") }
        )

        Row {
            Button(onClick = { viewModel.guardarDatos() }) {
                Text("GUARDAR")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = { viewModel.mostrarDatos() }) {
                Text("MOSTRAR DATOS")
            }
        }

        // Usamos LazyColumn para mostrar la lista que viene de la BD
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(viewModel.listaUsuariosMostrar) { usuario ->
                Card(modifier = Modifier.padding(4.dp).fillMaxWidth()) {
                    Text(
                        text = "Nombre: ${usuario.nombre}, Edad: ${usuario.edad}\nCorreo: ${usuario.correo}",
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}