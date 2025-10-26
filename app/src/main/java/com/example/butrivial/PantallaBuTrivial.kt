// PantallaBuTrivial.kt
package com.example.butrivial

import android.content.Intent
import android.os.Bundle
import android.media.MediaPlayer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.butrivial.ui.theme.BuTrivialTheme
import kotlinx.coroutines.delay

// Duración del temporizador por pregunta en segundos
const val TIEMPO_MAXIMO_POR_PREGUNTA = 30

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- RECIBIR EXTRAS ---
        val temaNombre: String? = intent.getStringExtra(EXTRA_TEMA_SELECCIONADO)
        val numPreguntas: Int = intent.getIntExtra(EXTRA_NUMERO_PREGUNTAS, 10)

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
                    PantallaButrivial(temaSeleccionado, numPreguntas)
                }
            }
        }
    }
}

@Composable
fun PantallaButrivial(temaInicial: Tema, maxPreguntas: Int) {
    val context = LocalContext.current

    // --- LÓGICA DE CARGA DE PREGUNTAS ---
    val poolDePreguntas: List<Pregunta> = remember(temaInicial, maxPreguntas) {
        val listaCompleta = obtenerPoolDePreguntas(temaInicial)
        val listaBarajada = listaCompleta.shuffled()
        listaBarajada.take(maxPreguntas)
    }

    // --- ESTADOS DE LA PARTIDA ---
    var preguntaActual by remember { mutableStateOf(0) }
    // Puntuación ahora almacena la suma de los segundos restantes acumulados
    var puntuacion by remember { mutableStateOf(0) }
    var juegoTerminado by remember { mutableStateOf(false) }
    var respuestaSeleccionada: Int? by remember { mutableStateOf(null) }
    var respuestaBloqueada by remember { mutableStateOf(false) }
    var mostrarDialogoPausa by remember { mutableStateOf(false) }
    // Estado para guardar los segundos ganados en la pregunta actual
    var segundosGanados by remember { mutableStateOf(0) }

    // --- ESTADO DEL TEMPORIZADOR ---
    var tiempoRestante by remember(preguntaActual) {
        mutableStateOf(TIEMPO_MAXIMO_POR_PREGUNTA)
    }

    val pregunta: Pregunta = poolDePreguntas.getOrElse(preguntaActual) {
        Pregunta("Error de pregunta", listOf("", "", "", ""), 0)
    }

    // Media Players para efectos de sonido
    val mpAcierto: MediaPlayer? = remember { MediaPlayer.create(context, R.raw.correcto) }
    val mpError: MediaPlayer? = remember { MediaPlayer.create(context, R.raw.fallo) }

    // --- Media Player para la música de fondo del juego ---
    val mpJuego: MediaPlayer? = remember {
        MediaPlayer.create(context, R.raw.cancioningame).apply {
            isLooping = true // Reproducción en bucle
        }
    }

    // --- CONTROL DEL CICLO DE VIDA Y REPRODUCCIÓN DE MÚSICA DE FONDO ---
    DisposableEffect(Unit) {
        // La música comienza con la primera pregunta
        mpJuego?.start()

        onDispose {
            // Liberar todos los recursos de MediaPlayer al salir de la pantalla
            mpJuego?.stop()
            mpJuego?.release()
            mpAcierto?.release()
            mpError?.release()
        }
    }

    // --- LÓGICA DE REINICIO DE MÚSICA POR PREGUNTA ---
    // Este efecto se dispara cada vez que cambia preguntaActual
    LaunchedEffect(preguntaActual) {
        // Detiene la música (si está sonando) y la reinicia desde el principio
        mpJuego?.seekTo(0)
        mpJuego?.start()
    }

    val irAInicio: () -> Unit = {
        val intent = Intent(context, PantallaInicioActivity::class.java)
        context.startActivity(intent)
        if (context is ComponentActivity) (context as ComponentActivity).finish()
    }

    // --- FUNCIÓN PARA AVANZAR A LA SIGUIENTE PREGUNTA ---
    val siguientePregunta: () -> Unit = {
        if (preguntaActual < poolDePreguntas.size - 1) {
            preguntaActual++
            respuestaSeleccionada = null
            respuestaBloqueada = false
            segundosGanados = 0 // Reiniciar segundos ganados
        } else {
            juegoTerminado = true
        }
    }

    // --- FUNCIÓN DE RESPUESTA (Puntuación por tiempo restante) ---
    fun comprobarRespuesta(indiceOpcion: Int?) {
        if (respuestaBloqueada) return

        respuestaBloqueada = true
        respuestaSeleccionada = indiceOpcion

        // Pausar la música al responder
        mpJuego?.pause()

        if (indiceOpcion != null && indiceOpcion == pregunta.respuestaCorrecta) {
            mpAcierto?.start()
            // NUEVA LÓGICA: Puntuación es el tiempo restante
            segundosGanados = tiempoRestante
            puntuacion += segundosGanados
        } else {
            mpError?.start()
            // Puntuación de 0 por fallo o tiempo agotado
            segundosGanados = 0
        }
    }

    // --- LÓGICA DEL TEMPORIZADOR ---
    LaunchedEffect(preguntaActual, respuestaBloqueada) {
        if (!respuestaBloqueada) {
            tiempoRestante = TIEMPO_MAXIMO_POR_PREGUNTA
            while (tiempoRestante > 0) {
                delay(1000)
                if (respuestaBloqueada) break // Salir si ya se ha respondido
                tiempoRestante--
            }
            if (tiempoRestante == 0 && !respuestaBloqueada) {
                // Tiempo agotado, se comprueba la respuesta con 'null' (0 puntos)
                comprobarRespuesta(null)
            }
        }
    }


    // Lanzar la PantallaPuntuacionActivity al terminar el juego
    LaunchedEffect(juegoTerminado) {
        if (juegoTerminado) {
            val intent = Intent(context, PantallaPuntuacionActivity::class.java)
            intent.putExtra("puntuacion", puntuacion)
            intent.putExtra("totalPreguntas", poolDePreguntas.size)
            context.startActivity(intent)
            if (context is ComponentActivity) (context as ComponentActivity).finish()
        }
    }


    // --- INTERFAZ DE USUARIO (COMPOSABLE) ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003049))
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // --- CABECERA (Puntuación, Contador de preguntas y Salir) ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // NUEVO: Mostrar Puntuación Acumulada

                Text(
                    text = "$puntuacion puntos",
                    color = Color(0xFFFFB700),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )

            // Contador de preguntas
            Text(
                text = "${preguntaActual + 1} / ${poolDePreguntas.size}",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            // Botón de Salida
            IconButton(
                onClick = { mostrarDialogoPausa = true },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFC1121F), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Salir de la partida",
                    tint = Color.White
                )
            }
        }

        // --- Tema (Separado para que esté centrado visualmente) ---
        Text(
            text = temaInicial.nombreMostrar,
            color = Color(0xFFFFB700),
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
        )

        // ESPACIADOR AUMENTADO (Empuja el cronómetro hacia abajo)
        Spacer(modifier = Modifier.height(24.dp))

        // --- TEMPORIZADOR ---
        LinearProgressIndicator(
            progress = { tiempoRestante.toFloat() / TIEMPO_MAXIMO_POR_PREGUNTA.toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            color = if (tiempoRestante > 5) Color(0xFF00A676) else Color(0xFFC1121F),
            trackColor = Color(0xFF669BBC)
        )
        Text(
            text = "$tiempoRestante segundos",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        // ESPACIADOR AUMENTADO (Empuja la pregunta hacia abajo)
        Spacer(modifier = Modifier.height(24.dp))

        // --- PREGUNTA ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF0D5)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = pregunta.texto,
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }

        // ESPACIADOR REDUCIDO (Tira de las opciones/botón Siguiente hacia arriba)
        Spacer(modifier = Modifier.height(16.dp))

        // --- OPCIONES DE RESPUESTA ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            pregunta.opciones.forEachIndexed { index, opcion ->

                // Determinamos el color de cada botón (la lógica es la misma)
                val colorBoton = when {
                    respuestaSeleccionada == index && index == pregunta.respuestaCorrecta -> Color(0xFF00A676)
                    respuestaSeleccionada == index && index != pregunta.respuestaCorrecta -> Color(0xFFC1121F)
                    respuestaBloqueada && index == pregunta.respuestaCorrecta -> Color(0xFF00A676)
                    // Mostrar el botón correcto en verde al agotarse el tiempo
                    respuestaBloqueada && respuestaSeleccionada == null && index == pregunta.respuestaCorrecta -> Color(0xFF00A676)
                    else -> Color(0xFF669BBC)
                }

                Button(
                    onClick = { comprobarRespuesta(index) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    enabled = !respuestaBloqueada,
                    colors = ButtonDefaults.buttonColors(containerColor = colorBoton)
                ) {
                    Text(
                        text = opcion,
                        color = Color.White,
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // --- ESPACIO ADICIONAL ANTES DE LA RETROALIMENTACIÓN ---
            Spacer(modifier = Modifier.height(8.dp))
        }

        // --- RETROALIMENTACIÓN DE LA RESPUESTA (Acierto/Fallo - El "Cuadro de Texto") ---
        if (respuestaBloqueada) {

            // Colores basados en si se ganó puntuación o no
            val colorFondo = if (segundosGanados > 0) Color(0xFF00A676) else Color(0xFFC1121F)

            // Mensaje basado en el resultado
            val mensajeRetroalimentacion = when {
                segundosGanados > 0 -> {
                    "¡CORRECTO! Ganaste $segundosGanados puntos."
                }
                respuestaSeleccionada != null -> {
                    // Fallo normal
                    "¡INCORRECTO! Ganas 0 puntos.\nLa respuesta correcta era: ${pregunta.opciones[pregunta.respuestaCorrecta]}"
                }
                else -> {
                    // Tiempo agotado
                    "¡TIEMPO AGOTADO! Ganas 0 puntos.\nLa respuesta correcta era: ${pregunta.opciones[pregunta.respuestaCorrecta]}"
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = colorFondo),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = mensajeRetroalimentacion,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    // CORRECCIÓN para centrar el texto horizontalmente
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
        }

        // --- BOTÓN SIGUIENTE PREGUNTA (Texto centrado garantizado) ---
        if (respuestaBloqueada) {
            Button(
                onClick = siguientePregunta,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB700))
            ) {
                Text(
                    "Siguiente Pregunta",
                    color = Color.White,
                    fontSize = 18.sp,
                    // Asegura que el Text ocupe todo el ancho y se centre dentro del Button
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    } // Fin del Contenedor Principal (Column)

    // --- DIÁLOGO DE PAUSA ---
    if (mostrarDialogoPausa) {
        AlertDialog(
            onDismissRequest = {
                mostrarDialogoPausa = false
            },
            title = {
                Text(text = "Abandonar Partida", fontWeight = FontWeight.Bold)
            },
            text = {
                Text(text = "¿Estás seguro de que quieres volver al menú principal? Perderás tu progreso actual.")
            },
            confirmButton = {
                Button(
                    onClick = irAInicio,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC1121F))
                ) {
                    Text("Salir y Perder Progreso", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        mostrarDialogoPausa = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A676))
                ) {
                    Text("Continuar Partida", color = Color.White)
                }
            }
        )
    }

} // Fin de PantallaButrivial