package com.example.butrivial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.butrivial.ui.theme.BuTrivialTheme
import kotlinx.coroutines.delay

// IMPORTACIONES DEL ARCHIVO EXTERNO

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temaNombre: String? = intent.getStringExtra(EXTRA_TEMA_SELECCIONADO)

        val temaSeleccionado: Tema = if (temaNombre != null) {
            try {
                Tema.valueOf(temaNombre)
            } catch (e: IllegalArgumentException) {
                // Fallback: Si el nombre no existe, usamos un tema por defecto
                Tema.CIENCIAS_NATURALES
            }
        } else {
            // Fallback: Si no se pasó ningún Intent, usamos un tema por defecto
            Tema.CIENCIAS_NATURALES
        }
        setContent {
            BuTrivialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Preguntas de naturales
                    PantallaButrivial(temaSeleccionado)
                }
            }
        }
    }
}

@Composable
fun PantallaButrivial(temaInicial: Tema) {

    // --- LÓGICA DE CARGA DE PREGUNTAS ---
    val poolDePreguntas: List<Pregunta> = remember(temaInicial) {
        // Obtenemos la lista completa del tema
        val listaCompleta = PreguntasPorTema[temaInicial] ?: emptyList()

        // Mezclamos pregunta y elige 10.
        listaCompleta
            .shuffled()
            .take(10)
    }

    // --- ESTADOS ---
    var puntuacion by remember { mutableIntStateOf(0) }
    var mensajeEstado by remember { mutableStateOf("¡A jugar!") }
    var tiempoRestante by remember { mutableIntStateOf(30) }
    var preguntaIndex by remember { mutableIntStateOf(0) }

    // El juego está activo si hay preguntas y el tiempo no se ha acabado por respuesta o timeout
    var juegoActivo by remember { mutableStateOf(poolDePreguntas.isNotEmpty()) }

    // Manejar el final del juego o pool vacío
    val preguntaActual = poolDePreguntas.getOrNull(preguntaIndex)
    if (preguntaActual == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "¡Fin del Trivial!\nTu puntuación final es: $puntuacion",
                color = Color.White,
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        }
        return
    }

    // --- EFECTO DE TEMPORIZADOR ---
    LaunchedEffect(key1 = juegoActivo, key2 = preguntaIndex) {
        if (juegoActivo) {
            tiempoRestante = 30
            while (tiempoRestante > 0) {
                delay(1000L)
                tiempoRestante--
            }
            if (tiempoRestante == 0) {
                mensajeEstado = "¡Tiempo agotado! La respuesta era: ${preguntaActual.opciones[preguntaActual.respuestaCorrecta]}"
                juegoActivo = false
            }
        }
    }

    // --- FUNCIÓN PARA PROCESAR RESPUESTA ---
    fun procesarRespuesta(seleccionIndex: Int) {
        if (!juegoActivo) return

        if (seleccionIndex == preguntaActual.respuestaCorrecta) {
            puntuacion += 10
            mensajeEstado = "¡Correcto! +10 puntos."
        } else {
            mensajeEstado = "Incorrecto. La respuesta correcta era: ${preguntaActual.opciones[preguntaActual.respuestaCorrecta]}"
        }
        juegoActivo = false
    }

    // --- FUNCIÓN PARA PASAR A LA SIGUIENTE PREGUNTA ---
    fun siguientePregunta() {
        if (preguntaIndex < poolDePreguntas.size - 1) {
            preguntaIndex++
            juegoActivo = true
            mensajeEstado = "¡Siguiente pregunta!"
        } else {
            preguntaIndex++ // Para que la comprobación 'preguntaActual == null' se active (Fin del juego)
            mensajeEstado = "¡Juego terminado! Pulsa Reiniciar."
            juegoActivo = false
        }
    }

    // --- FUNCIÓN PARA REINICIAR EL JUEGO COMPLETO ---
    fun reiniciarJuego() {
        puntuacion = 0
        preguntaIndex = 0
        tiempoRestante = 30
        juegoActivo = poolDePreguntas.isNotEmpty()
        mensajeEstado = "Juego Reiniciado. ¡A por la primera pregunta!"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003049))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // --- 1. Área Superior: Puntuación y Tiempo ---
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "BuTrivial",
                color = Color(0xFFFFB700),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Tema: ${temaInicial.nombreMostrar} (${preguntaIndex + 1}/${poolDePreguntas.size})",
                color = Color.LightGray,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Puntuación: $puntuacion",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // --- INDICADOR DE TEMPORIZADOR ---
            Text(
                text = "Tiempo: $tiempoRestante s",
                color = if (tiempoRestante <= 5 && juegoActivo) Color.Red else Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        // --- 2. Área Central: Pregunta ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF0D5)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Text(
                text = preguntaActual.texto,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(24.dp)

            )
        }

        // --- 3. Área Opciones (Botones) ---
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            preguntaActual.opciones.forEachIndexed { index, opcion ->
                Button(
                    onClick = { procesarRespuesta(index) },
                    enabled = juegoActivo,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB700))
                ) {
                    Text(opcion, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                }
            }
        }

        // --- Mensajes y Controles ---
        Text(
            text = mensajeEstado,
            color = Color(0xFFFFFFFF),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp).heightIn(min = 40.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = ::reiniciarJuego,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF669BBC))
            ) {
                Text("Reiniciar Juego")
            }

            Button(
                onClick = ::siguientePregunta,
                enabled = !juegoActivo,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A676))
            ) {
                Text("Siguiente Pregunta")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaButrivialPreview() {
    PantallaButrivial(Tema.CIENCIAS_NATURALES)
}
