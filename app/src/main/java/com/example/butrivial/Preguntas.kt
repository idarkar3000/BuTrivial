// Preguntas.kt
package com.example.butrivial

data class Pregunta(
    val texto: String,
    val opciones: List<String>,
    val respuestaCorrecta: Int, // Índice 0, 1, 2, o 3
)

// Definición de los 5 temas como un Enum
enum class Tema(val nombreMostrar: String) {
    CIENCIAS_NATURALES("Ciencias"),
    HISTORIA("Historia"),
    VIDEOJUEGOS("Videojuegos"),
    DEPORTES("Deportes"),
    ARTE("Arte")
}

// Estructura de datos principal: Un mapa que almacena una lista de preguntas por cada tema.
val PreguntasPorTema: Map<Tema, List<Pregunta>> = mapOf(
    // ----------------------------------------------------
    // POOL DE 10 PREGUNTAS: CIENCIAS NATURALES (NUEVO)
    // ----------------------------------------------------
    Tema.CIENCIAS_NATURALES to listOf(
        // Enunciado: ¿Cuál es el proceso por el cual las plantas fabrican su propio alimento? (Opción 2: Fotosíntesis)
        Pregunta(
            texto = "¿Cuál es el proceso por el cual las plantas fabrican su propio alimento?",
            opciones = listOf("Respiración", "Fotosíntesis", "Transpiración", "Germinación"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Qué parte de la célula es la encargada de controlar todas sus funciones? (Opción 4: Núcleo)
        Pregunta(
            texto = "¿Qué parte de la célula es la encargada de controlar todas sus funciones?",
            opciones = listOf("Mitocondria", "Membrana", "Citoplasma", "Núcleo"),
            respuestaCorrecta = 3
        ),

        // Enunciado: ¿Cuál es el principal gas de efecto invernadero responsable del calentamiento global? (Opción 2: Dióxido de Carbono)
        Pregunta(
            texto = "¿Cuál es el principal gas de efecto invernadero responsable del calentamiento global?",
            opciones = listOf("Oxígeno", "Dióxido de Carbono", "Nitrógeno", "Helio"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Cuál es el nombre científico del ser humano? (Opción 3: Homo sapiens)
        Pregunta(
            texto = "¿Cuál es el nombre científico del ser humano?",
            opciones = listOf("Felis catus", "Canis familiaris", "Homo sapiens", "Pan troglodytes"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Qué tipo de roca se forma a partir del enfriamiento y solidificación del magma? (Opción 3: Ígnea)
        Pregunta(
            texto = "¿Qué tipo de roca se forma a partir del enfriamiento y solidificación del magma?",
            opciones = listOf("Metamórfica", "Sedimentaria", "Ígnea", "Arenisca"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Cuál de los siguientes órganos produce la bilis? (Opción 3: Hígado)
        Pregunta(
            texto = "¿Cuál de los siguientes órganos produce la bilis?",
            opciones = listOf("Riñón", "Páncreas", "Hígado", "Estómago"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Qué unidad se utiliza para medir la intensidad del sonido? (Opción 3: Decibelio)
        Pregunta(
            texto = "¿Qué unidad se utiliza para medir la intensidad del sonido?",
            opciones = listOf("Voltio", "Julio", "Decibelio", "Amperio"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Cómo se llama el proceso de cambio de estado de sólido a gas, sin pasar por líquido? (Opción 4: Sublimación)
        Pregunta(
            texto = "¿Cómo se llama el proceso de cambio de estado de sólido a gas, sin pasar por líquido?",
            opciones = listOf("Condensación", "Fusión", "Vaporización", "Sublimación"),
            respuestaCorrecta = 3
        ),

        // Enunciado: ¿Cuál es el hueso más largo del cuerpo humano? (Opción 4: Fémur)
        Pregunta(
            texto = "¿Cuál es el hueso más largo del cuerpo humano?",
            opciones = listOf("Húmero", "Radio", "Tibia", "Fémur"),
            respuestaCorrecta = 3
        ),

        // Enunciado: ¿Qué capa de la atmósfera contiene la capa de ozono, que protege de la radiación UV? (Opción 3: Estratósfera)
        Pregunta(
            texto = "¿Qué capa de la atmósfera contiene la capa de ozono, que protege de la radiación UV?",
            opciones = listOf("Troposfera", "Mesosfera", "Estratósfera", "Termosfera"),
            respuestaCorrecta = 2
        ),
    ),

    // ----------------------------------------------------
    // Añadir aqui las preguntas de otras weas
    // ----------------------------------------------------
    Tema.HISTORIA to listOf(),
    Tema.VIDEOJUEGOS  to emptyList(),
    Tema.DEPORTES to emptyList(),
    Tema.ARTE to emptyList()
)