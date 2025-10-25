package com.example.butrivial

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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

// Asegúrate de que esta constante esté definida en PantallaSeleccionCategoria.kt
// o la copias aquí si ese archivo no está siendo importado correctamente:
// const val EXTRA_TEMA_SELECCIONADO = "com.example.butrivial.TEMA_SELECCIONADO"


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temaNombre: String? = intent.getStringExtra(EXTRA_TEMA_SELECCIONADO)

        val temaSeleccionado: Tema = if (temaNombre != null) {
            try {
                Tema.valueOf(temaNombre)
            } catch (e: IllegalArgumentException) {
                // Tema por defecto
                Tema.CIENCIAS_NATURALES
            }
        } else {
            // Tema por defecto
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
    val context = LocalContext.current

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

    // --- ESTADO PARA EL DIÁLOGO DE PAUSA (NUEVO) ---
    var mostrarDialogoPausa by remember { mutableStateOf(false) }


    // --- FUNCIÓN PARA NAVEGAR A INICIO (NUEVA) ---
    val irAInicio: () -> Unit = {
        val intent = Intent(context, PantallaInicioActivity::class.java)
        context.startActivity(intent)
        if (context is ComponentActivity) context.finish()
    }

    // --- FUNCIÓN PARA REINICIAR EL JUEGO COMPLETO (AHORA COMO VAL LAMBDA) ---
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
        val context = LocalContext.current

        // Lanzar la PantallaPuntuacionActivity al terminar el juego
        LaunchedEffect(Unit) {
            val intent = Intent(context, PantallaPuntuacionActivity::class.java)
            intent.putExtra("puntuacion", puntuacion)
            intent.putExtra("totalPreguntas", poolDePreguntas.size)
            context.startActivity(intent)

            // Cerrar la Activity actual para que no se pueda volver con "Atrás"
            if (context is ComponentActivity) context.finish()
        }

        // Mostrar pantalla temporal mientras carga la puntuación
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF003049)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Cargando resultados...",
                color = Color.White,
                fontSize = 24.sp
            )
        }

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

    // --- CONTENEDOR PRINCIPAL: COLUMN ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003049))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // --- 1. ÁREA SUPERIOR: CABECERA CON BOTÓN DE PAUSA (ROW) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {

            // A. BOTÓN DE PAUSA
            Button(
                onClick = {
                    mostrarDialogoPausa = true
                },
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF669BBC))
            ) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Salir",
                    tint = Color.White
                )
            }

            // B. EL RESTO DE LA CABECERA (Centrado en el espacio restante)
            Column(
                modifier = Modifier
                    .weight(1f) // Ocupa el espacio restante
                    .padding(start = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "BuTrivial",
                    color = Color(0xFFFFB700),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Tema: ${temaInicial.nombreMostrar} (${preguntaIndex + 1}/${poolDePreguntas.size})",
                    color = Color.LightGray,
                    fontSize = 16.sp
                )
                Text(
                    text = "Puntuación: $puntuacion",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                // --- INDICADOR DE TEMPORIZADOR ---
                Text(
                    text = "Tiempo: $tiempoRestante s",
                    color = if (tiempoRestante <= 5 && juegoActivo) Color.Red else Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // C. Espaciador para equilibrar visualmente
            Spacer(modifier = Modifier.width(48.dp))
        } // Fin del Row de Cabecera

        // --- 2. ÁREA CENTRAL: PREGUNTA ---
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

        // --- 3. ÁREA OPCIONES (BOTONES) - CON COLORES DE FEEDBACK ---
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

        // --- 4. MENSAJES Y CONTROLES INFERIORES ---
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
                onClick = reiniciarJuego,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF669BBC))
            ) {
                Text("Reiniciar Juego")
            }

            Button(
                onClick = { siguientePregunta() },
                enabled = !juegoActivo,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A676))
            ) {
                Text("Siguiente Pregunta")
            }
        }
    } // Fin del Contenedor Principal (Column)

    // --- DIÁLOGO DE PAUSA (Se superpone a la Column) ---
    if (mostrarDialogoPausa) {
        AlertDialog(
            onDismissRequest = {
                mostrarDialogoPausa = false // Cerrar al tocar fuera
            },
            title = {
                Text(text = "¿Salir de la Partida?", fontWeight = FontWeight.Bold)
            },
            text = {
                Text(text = "¿Estás seguro de que quieres volver al menú principal? Perderás tu progreso actual.")
            },
            confirmButton = {
                Button(
                    onClick = irAInicio,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC1121F)) // Rojo
                ) {
                    Text("Salir y Perder Progreso", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        mostrarDialogoPausa = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A676)) // Verde
                ) {
                    Text("Continuar Partida", color = Color.White)
                }
            }
        )
    }

} // Fin de PantallaButrivial



@Preview(showBackground = true)
@Composable
fun PantallaButrivialPreview() {
    PantallaButrivial(Tema.CIENCIAS_NATURALES)
}