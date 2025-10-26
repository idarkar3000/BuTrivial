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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.butrivial.ui.theme.BuTrivialTheme

// Constante clave para pasar el nombre del Tema a la actividad de juego
const val EXTRA_TEMA_SELECCIONADO = "com.example.butrivial.TEMA_SELECCIONADO"
// Constante clave para pasar el número de preguntas
const val EXTRA_NUMERO_PREGUNTAS = "com.example.butrivial.NUMERO_PREGUNTAS"

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
                        onTemaSelected = { tema, numPreguntas ->
                            // Creamos el Intent hacia la actividad del juego (MainActivity)
                            val intent = Intent(this, MainActivity::class.java).apply {
                                // Añadimos el nombre del tema (convertido a String) como extra
                                putExtra(EXTRA_TEMA_SELECCIONADO, tema.name)
                                // AÑADIMOS EL NÚMERO DE PREGUNTAS COMO EXTRA
                                putExtra(EXTRA_NUMERO_PREGUNTAS, numPreguntas)
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
    onTemaSelected: (Tema, Int) -> Unit
) {
    val context = LocalContext.current

    // --- ESTADO PARA EL SLIDER ---
    // Inicializado en 10f. Este estado controla la posición del slider.
    var sliderPosition by remember { mutableFloatStateOf(10f) }
    // Convertimos el float del slider a un entero para el número de preguntas a usar
    val numPreguntasSeleccionadas = sliderPosition.toInt()

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

        // --- SLIDER DE PREGUNTAS ---
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(
                // Muestra el número de preguntas seleccionado
                text = "Preguntas por Partida: $numPreguntasSeleccionadas",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                // Rango: de 5.0f a 10.0f
                valueRange = 5f..10f,
                // 5 pasos: esto permite que el slider se detenga en 5, 6, 7, 8, 9 y 10.
                steps = 4,
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFFFB700),
                    activeTrackColor = Color(0xFFFFB700),
                    inactiveTrackColor = Color(0xFF669BBC)
                )
            )
        }
        // --- FIN SLIDER DE PREGUNTAS ---


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            // Recorre todos los valores del enum Tema (incluido MIXTO)
            items(Tema.entries.toTypedArray()) { tema ->
                CategoriaCard(tema = tema, onClick = {
                    // Al hacer click, pasamos el tema y el valor actual del slider
                    onTemaSelected(it, numPreguntasSeleccionadas)
                })
            }
        }

        // --- Botón de Tema Aleatorio ---
        Button(
            onClick = {
                // Selecciona un tema al azar del Enum Tema (excluyendo MIXTO, ya que es la categoría "Un poco de todo")
                val temaAleatorio = Tema.entries.toTypedArray().filter { it != Tema.MIXTO }.random()
                // Llama al callback con el tema aleatorio y el número de preguntas
                onTemaSelected(temaAleatorio, numPreguntasSeleccionadas)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A676))
        ) {
            Text("¡Tema Aleatorio! (Sorpresa)", color = Color.White, fontSize = 18.sp)
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
                .padding(top = 12.dp),
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
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaSeleccionCategoriaPreview() {
    BuTrivialTheme {
        PantallaSeleccionCategoria(onTemaSelected = { _, _ -> })
    }
}