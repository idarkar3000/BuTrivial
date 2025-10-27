package com.example.butrivial

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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

    // Colores personalizados del tema
    val primaryColor = Color(0xFF003049) // Azul Oscuro de Fondo/Elementos
    val secondaryColor = Color(0xFFFFB700) // Amarillo/Naranja (Botón)
    val cardColor = Color(0xFFFDF0D5) // Color de fondo de tarjeta
    val cardNameColor = Color(0xFF669BBC) // Color para el fondo de los nombres

    // Lista de integrantes
    val integrantes = listOf(
        "Adrián Espínola Gumiel",
        "Guillermo Hauschild Arencibia",
        "Hugo Orejas Peláez",
        "David Antonio Paz Gullón",
        "Luis Fernando Rodriguez Rivera",
        "Carlos Vega San Román"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor) // Fondo oscuro
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- TÍTULO DE LA PANTALLA ---
        Text(
            text = "BU TRIVIAL",
            color = secondaryColor,
            fontSize = 36.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
        )

        // --- TARJETA DE CREADORES (CRÉDITOS) ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Creadores del Juego",
                    color = primaryColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(integrantes) { nombre ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.9f),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = cardNameColor),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Text(
                                text = nombre,
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- BOTÓN VOLVER AL INICIO ---
        Button(
            onClick = {
                // Ir a la pantalla de inicio
                val intent = Intent(context, PantallaInicioActivity::class.java)
                context.startActivity(intent)
                if (context is ComponentActivity) context.finish()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = secondaryColor)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
            Spacer(Modifier.width(8.dp))
            Text("Volver al inicio", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}