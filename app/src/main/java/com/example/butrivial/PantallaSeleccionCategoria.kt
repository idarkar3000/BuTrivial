// PantallaSeleccionCategoria.kt
package com.example.butrivial

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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

// Constante clave para pasar el nombre del Tema a la actividad de juego
const val EXTRA_TEMA_SELECCIONADO = "com.example.butrivial.TEMA_SELECCIONADO"

class PantallaSeleccionCategoriaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuTrivialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaSeleccionCategoria(
                        // Lógica de navegación al seleccionar un tema
                        onTemaSelected = { tema ->
                            // 1. Creamos el Intent hacia la actividad del juego (MainActivity)
                            val intent = Intent(this, MainActivity::class.java).apply {
                                // 2. Añadimos el nombre del tema (convertido a String) como extra
                                putExtra(EXTRA_TEMA_SELECCIONADO, tema.name)
                            }
                            startActivity(intent)
                            finish() // Cerramos esta actividad
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaSeleccionCategoria(
    onTemaSelected: (Tema) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003049))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Selecciona un Tema",
            color = Color(0xFFFFB700),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp, top = 16.dp)
        )

        LazyVerticalGrid(
            // El error "Unresolved reference: Tema" ocurre si Tema no está definido en otro archivo
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            // Recorre todos los valores del enum Tema (¡Requiere que Tema exista!)
            items(Tema.entries.toTypedArray()) { tema ->
                CategoriaCard(tema = tema, onClick = onTemaSelected)
            }
        }

        // Botón de Volver al Inicio
        Button(
            onClick = {
                val intent = Intent(context, PantallaInicioActivity::class.java)
                context.startActivity(intent)
                if (context is ComponentActivity) context.finish()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF669BBC))
        ) {
            Text("Volver al Inicio", color = Color.White, fontSize = 18.sp)
        }
    }
}

@Composable
fun CategoriaCard(tema: Tema, onClick: (Tema) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        onClick = { onClick(tema) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF0D5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = tema.nombreMostrar,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
