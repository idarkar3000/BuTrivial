package com.example.butrivial

import android.content.Intent
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

// Asegúrate de que esta constante esté definida en PantallaSeleccionCategoria.kt
// o la copias aquí si ese archivo no está siendo importado correctamente:
// const val EXTRA_TEMA_SELECCIONADO = "com.example.butrivial.TEMA_SELECCIONADO"


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Usamos PantallaSeleccionCategoriaActivity.EXTRA_TEMA_SELECCIONADO para asegurar la referencia
        val temaNombre: String? = intent.getStringExtra("com.example.butrivial.TEMA_SELECCIONADO")

        val temaSeleccionado: Tema = if (temaNombre != null) {
            try {
                Tema.valueOf(temaNombre)
            } catch (e: IllegalArgumentException) {
                Tema.CIENCIAS_NATURALES
            }
        } else {
            Tema.CIENCIAS_NATURALES
        }
        setContent {
            BuTrivialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
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
        val listaCompleta = PreguntasPorTema[temaInicial] ?: emptyList()
        listaCompleta
            .shuffled()
            .take(10) // Usamos 10 preguntas por juego
    }

    // --- ESTADOS ---
    var puntuacion by remember { mutableIntStateOf(0) }
    var mensajeEstado by remember { mutableStateOf("¡A jugar!") }
    var tiempoRestante by remember { mutableIntStateOf(30) }
    var preguntaIndex by remember { mutableIntStateOf(0) }
    var juegoActivo by remember { mutableStateOf(poolDePreguntas.isNotEmpty()) }

    // --- ESTADOS para Feedback Visual ---
    var respuestaSeleccionadaIndex by remember { mutableIntStateOf(-1) }
    var respuestaCorrectaIndex by remember { mutableIntStateOf(-1) }

    // --- FUNCIÓN PARA REINICIAR EL JUEGO COMPLETO (AHORA COMO VAL LAMBDA) ---
    // ESTA DEFINICIÓN CORRIGE EL ERROR DE 'UNRESOLVED REFERENCE'
    val reiniciarJuego: () -> Unit = {
        puntuacion = 0
        preguntaIndex = 0
        tiempoRestante = 30
        juegoActivo = poolDePreguntas.isNotEmpty()
        mensajeEstado = "Juego Reiniciado. ¡A por la primera pregunta!"
        // Resetear el feedback visual
        respuestaSeleccionadaIndex = -1
        respuestaCorrectaIndex = -1
    }

    // --- FUNCIÓN PARA PASAR A LA SIGUIENTE PREGUNTA ---
    fun siguientePregunta() {
        if (preguntaIndex < poolDePreguntas.size - 1) {
            preguntaIndex++
            mensajeEstado = "¡Siguiente pregunta!"
        } else {
            preguntaIndex++ // Para activar la PantallaFinDeJuego (preguntaActual == null)
            mensajeEstado = "¡Juego terminado!"
            juegoActivo = false
        }
        // Resetear el feedback visual
        respuestaSeleccionadaIndex = -1
        respuestaCorrectaIndex = -1
    }

    // Manejar el final del juego o pool vacío
    val preguntaActual = poolDePreguntas.getOrNull(preguntaIndex)

    // --- LÓGICA DE FIN DE JUEGO ---
    if (preguntaActual == null) {
        PantallaFinDeJuego(
            puntuacion = puntuacion,
            totalPreguntas = poolDePreguntas.size,
            onReiniciar = reiniciarJuego // <--- USAMOS LA PROPIEDAD VAL
        )
        return
    }

    // --- EFECTO DE TEMPORIZADOR ---
    LaunchedEffect(key1 = preguntaIndex) {
        if (preguntaActual != null) {
            tiempoRestante = 30
            juegoActivo = true

            while (tiempoRestante > 0 && juegoActivo) {
                delay(1000L)
                if (juegoActivo) {
                    tiempoRestante--
                }
            }

            // Si el bucle terminó por timeout
            if (tiempoRestante == 0 && juegoActivo) {
                respuestaCorrectaIndex = preguntaActual.respuestaCorrecta
                mensajeEstado = "¡Tiempo agotado! La respuesta era: ${preguntaActual.opciones[preguntaActual.respuestaCorrecta]}"
                juegoActivo = false
            }
        }
    }

    // --- FUNCIÓN PARA PROCESAR RESPUESTA ---
    fun procesarRespuesta(seleccionIndex: Int) {
        if (!juegoActivo) return

        respuestaSeleccionadaIndex = seleccionIndex
        respuestaCorrectaIndex = preguntaActual.respuestaCorrecta

        if (seleccionIndex == preguntaActual.respuestaCorrecta) {
            puntuacion += 10
            mensajeEstado = "¡Correcto! +10 puntos."
        } else {
            mensajeEstado = "Incorrecto. La respuesta correcta era: ${preguntaActual.opciones[preguntaActual.respuestaCorrecta]}"
        }
        juegoActivo = false
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

        // --- 3. Área Opciones (Botones) - CON COLORES DE FEEDBACK ---
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            preguntaActual.opciones.forEachIndexed { index, opcion ->

                val botonColor = when {
                    !juegoActivo && index == respuestaCorrectaIndex -> Color(0xFF00A676) // VERDE: Respuesta Correcta
                    !juegoActivo && index == respuestaSeleccionadaIndex -> Color(0xFFC1121F) // ROJO: Respuesta Incorrecta seleccionada
                    else -> Color(0xFFFFB700) // NARANJA: Estado normal o juego activo
                }

                Button(
                    onClick = { procesarRespuesta(index) },
                    enabled = juegoActivo,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = botonColor)
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
                onClick = reiniciarJuego, // <--- USAMOS LA PROPIEDAD VAL CORREGIDA
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


// --- NUEVO COMPOSABLE: PANTALLA DE FIN DE JUEGO ---
@Composable
fun PantallaFinDeJuego(puntuacion: Int, totalPreguntas: Int, onReiniciar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003049))
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¡FIN DEL JUEGO!",
            color = Color(0xFFFFB700),
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF0D5)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tu Puntuación Final:",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$puntuacion / ${totalPreguntas * 10} puntos",
                    color = Color(0xFF003049),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onReiniciar,
            modifier = Modifier
                .fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A676))
        ) {
            Text("Jugar de Nuevo", fontSize = 20.sp, color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaButrivialPreview() {
    PantallaButrivial(Tema.CIENCIAS_NATURALES)
}