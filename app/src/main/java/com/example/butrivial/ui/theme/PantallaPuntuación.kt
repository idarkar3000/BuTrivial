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

        setContent {
            BuTrivialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaPuntuacion(
                        puntuacion = puntuacion,
                        onVolverAJugar = {
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

@Composable
fun PantallaPuntuacion(
    puntuacion: Int,
    onVolverAJugar: () -> Unit,
    onSalirAlMenu: () -> Unit
) {
    val (mensaje, imagenRes) = when {
        puntuacion < 50 -> "Suspenso 😡" to R.drawable.logo_enfadado
        puntuacion in 50..60 -> "Aprobado por los pelos 😅" to R.drawable.logo_disgustado
        else -> "¡Muy bien! 😎" to R.drawable.logo_feliz
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003049))
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tu puntuación es de:",
            color = Color(0xFFFFB700),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "$puntuacion puntos",
            color = Color.White,
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = mensaje,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(id = imagenRes),
            contentDescription = null,
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

        // ✅ Nuevo botón para volver al menú principal
        Button(
            onClick = onSalirAlMenu,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE63946)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salir al menú principal", fontSize = 18.sp, color = Color.White)
        }
    }
}
