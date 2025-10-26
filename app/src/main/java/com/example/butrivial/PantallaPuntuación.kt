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
                        totalPreguntas = totalPreguntas, // PASAR EL TOTAL DE PREGUNTAS
                        onVolverAJugar = {
                            // Al volver a jugar, se lanza MainActivity sin extras, que usará valores por defecto o los que se carguen allí.
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        },
                        onSalirAlMenu = {
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

// -------------------------------------------------------------------------------------------------

@Composable
fun PantallaPuntuacion(
    puntuacion: Int,
    totalPreguntas: Int, // AÑADIDO: Número total de preguntas
    onVolverAJugar: () -> Unit,
    onSalirAlMenu: () -> Unit
) {
    // CALCULAR SI EL JUGADOR APRUEBA (la mitad o más)
    // Usamos 2.0 para asegurar una división de punto flotante.
    val porcentajeAcierto = (puntuacion.toFloat() / totalPreguntas.toFloat()) * 100
    val aprobado = porcentajeAcierto >= 50.0

    // LÓGICA DE RESULTADOS ACTUALIZADA
    val (tituloResultado, mensaje, imagenRes) = when {
        // APROBADO: Si la puntuación es la mitad o más
        aprobado -> Triple(
            "¡APROBADO!",
            if (porcentajeAcierto >= 80) "¡Felicidades! Resultado excelente." else "Aprobado, buen trabajo.",
            R.drawable.logo_feliz
        )
        // SUSPENSO: Si la puntuación es menor a la mitad
        else -> Triple(
            "SUSPENSO",
            if (puntuacion == 0) "Necesitas repasar, cero aciertos." else "Inténtalo de nuevo",
            R.drawable.logo_enfadado
        )
    }

    // Calcular la puntuación necesaria para aprobar
    val puntuacionNecesaria = (totalPreguntas / 2.0).toInt() + (totalPreguntas % 2)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003049))
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = tituloResultado,
            color = Color(0xFFFFB700),
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tu puntuación:",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "$puntuacion / $totalPreguntas",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Text(
            text = "(${"%.1f".format(porcentajeAcierto)}% de acierto)",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = mensaje,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        if (!aprobado) {
            Text(
                text = "Necesitas $puntuacionNecesaria para aprobar.",
                color = Color(0xFFE63946),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
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
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE63946)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salir al menú principal", fontSize = 18.sp, color = Color.White)
        }
    }
}