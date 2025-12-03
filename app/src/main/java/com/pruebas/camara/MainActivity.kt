package com.pruebas.camara // Asegúrate que coincida con tu paquete

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CameraScreen()
                }
            }
        }
    }
}

@Composable
fun CameraScreen() {
    // 1. ESTADO: Guardamos la imagen capturada. Es nullable porque al inicio no hay foto.
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // 2. EL LANZADOR (El sustituto moderno del Intent explícito manual):
    // 'TakePicturePreview' es un contrato predefinido que lanza la cámara y devuelve un Bitmap (miniatura).
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        // Este bloque se ejecuta cuando el usuario toma la foto y regresa
        imageBitmap = bitmap
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 3. MOSTRAR IMAGEN: Si el estado 'imageBitmap' tiene datos, mostramos la imagen
        imageBitmap?.let { btm ->
            Image(
                bitmap = btm.asImageBitmap(), // Convertimos el Bitmap de Android a ImageBitmap de Compose
                contentDescription = "Imagen capturada",
                modifier = Modifier
                    .size(300.dp)
                    .padding(bottom = 16.dp)
            )
        }

        // 4. BOTÓN: Lanza el intent
        Button(
            onClick = {
                // Esto equivale a crear el Intent de MediaStore.ACTION_IMAGE_CAPTURE
                cameraLauncher.launch()
            }
        ) {
            Text(text = "Abrir Cámara")
        }
    }
}