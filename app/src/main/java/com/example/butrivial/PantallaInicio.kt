package com.example.butrivial

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.butrivial.ui.theme.BuTrivialTheme

class PantallaInicioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuTrivialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaInicio(
                        onJugarClick = {
                            // Ir a la pantalla del juego
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        },
                        onCreditosClick = {
                            val intent = Intent(this, PantallaCreditosActivity::class.java)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaInicio(
    onJugarClick: () -> Unit,
    onCreditosClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003049))
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- Logo del juego ---
        Image(
            painter = painterResource(id = R.drawable.pantalla_inicio),
            contentDescription = "Logo BuTrivial",
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 30.dp)
        )

        // --- Botón Jugar ---
        Button(
            onClick = onJugarClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB700))
        ) {
            Text(
                text = "Jugar",
                fontSize = 22.sp,
                color = Color.White
            )
        }

        // --- Botón Créditos ---
        Button(
            onClick = onCreditosClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF669BBC))
        ) {
            Text(
                text = "Créditos",
                fontSize = 22.sp,
                color = Color.White
            )
        }
    }
}


