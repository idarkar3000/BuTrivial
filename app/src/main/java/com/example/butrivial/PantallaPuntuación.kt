package com.example.butrivial

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.butrivial.ui.theme.BuTrivialTheme


class PantallaPuntuacionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // La puntuación es ahora el total de segundos acumulados
        val puntuacion = intent.getIntExtra("puntuacion", 0)
        // OBTENER EL NÚMERO TOTAL DE PREGUNTAS DEL INTENT
        val totalPreguntas = intent.getIntExtra("totalPreguntas", 10)

        setContent {
            BuTrivialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaPuntuacion(
                        puntuacion = puntuacion,
                        totalPreguntas = totalPreguntas,
                        onVolverAJugar = {
                            // Volver a la pantalla de selección de categoría para jugar de nuevo
                            val intent = Intent(this, PantallaSeleccionCategoriaActivity::class.java)
                            startActivity(intent)
                            finish()
                        },
                        onSalirAlMenu = {
                            // Volver a la pantalla de inicio
                            val intent = Intent(this, PantallaInicioActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    )
                }
            }
        }
    }
}

// Data class para manejar los cuatro componentes del resultado (soluciona el error de Triple)
private data class ResultadoData(
    val titulo: String,
    val mensaje: String,
    val colorFondo: Color,
    val imagenRes: Int
)

@Composable
fun PantallaPuntuacion(
    puntuacion: Int,
    totalPreguntas: Int,
    onVolverAJugar: () -> Unit,
    onSalirAlMenu: () -> Unit
) {
    // Máxima puntuación posible (tiempo máximo * número de preguntas).
    val maxPuntuacion = totalPreguntas * 30

    // Lógica de resultado basada en el porcentaje de puntos obtenidos
    val porcentajePuntos = (puntuacion.toFloat() / maxPuntuacion.toFloat()) * 100

    // Establecemos tres rangos de rendimiento
    val (titulo, mensaje, colorFondo, imagenRes) = when {
        porcentajePuntos >= 75 -> ResultadoData(
            "¡Eres un Genio!",
            "Resultado Épico. ¡Tu cerebro es más rápido que la luz!",
            Color(0xFF00A676),
            R.drawable.logo_feliz
        )
        porcentajePuntos >= 40 -> ResultadoData(
            "¡Buen Trabajo!",
            "Has acumulado una buena cantidad de tiempo. ¡A seguir practicando!",
            Color(0xFFFFB700),
            R.drawable.logo_disgustado
        )
        else -> ResultadoData(
            "¡A por la próxima!",
            "El tiempo fue tu mayor enemigo. ¡Mejora tu velocidad de respuesta!",
            Color(0xFFC1121F),
            R.drawable.logo_enfadado
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003049))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- TÍTULO DE RESULTADO ---
        Text(
            text = titulo,
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- PUNTUACIÓN FINAL ---
        Text(
            text = "Tu puntuación final es:",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal
        )
        Text(
            text = "$puntuacion puntos",
            color = Color(0xFFFFB700),
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "(De un máximo de $maxPuntuacion puntos)",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // --- MENSAJE DE RENDIMIENTO ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = colorFondo)
        ) {
            Text(
                text = mensaje,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(id = imagenRes),
            contentDescription = "Resultado del juego",
            modifier = Modifier
                .size(180.dp)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Botón para volver a jugar
        Button(
            onClick = onVolverAJugar,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A676)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver a jugar", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para volver al menú principal
        Button(
            onClick = onSalirAlMenu,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC1121F)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salir al Menú Principal", fontSize = 18.sp, color = Color.White)
        }
    }
}