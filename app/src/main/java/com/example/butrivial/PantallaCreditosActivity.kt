package com.example.butrivial

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.butrivial.ui.theme.BuTrivialTheme

class PantallaCreditosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuTrivialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaCreditos()
                }
            }
        }
    }
}

@Composable
fun PantallaCreditos() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003049))
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Texto de créditos ---
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Créditos:\nAdrián Espínola Gumiel\nGuillermo Hauschild Arenciba\nHugo Orejas Peláez\nDavid Antonio Paz Gullón\nLuis Fernando Rodriguez Rivera\nCarlos Vega San Román",
                color = Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }

        // --- Botón Volver al inicio ---
        Button(
            onClick = {
                // Cierra esta pantalla y vuelve a la de inicio
                val intent = Intent(context, PantallaInicioActivity::class.java)
                context.startActivity(intent)
                if (context is ComponentActivity) context.finish()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB700))
        ) {
            Text("Volver al inicio", color = Color.White, fontSize = 18.sp)
        }
    }
}
