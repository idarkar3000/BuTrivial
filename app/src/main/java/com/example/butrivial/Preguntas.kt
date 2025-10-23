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

val PreguntasPorTema: Map<Tema, List<Pregunta>> = mapOf(
    // ----------------------------------------------------
    // POOL DE 10 PREGUNTAS: CIENCIAS NATURALES
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
    // POOL DE 10 PREGUNTAS: HISTORIA
    // ----------------------------------------------------
    Tema.HISTORIA to listOf(
        // Enunciado: ¿Qué tratado puso fin a la Guerra de Sucesión Española? (Opción 2: Tratado de Utrecht)
        Pregunta(
            texto = "¿Qué tratado puso fin a la Guerra de Sucesión Española?",
            opciones = listOf("Tratado de Tordesillas", "Tratado de Utrecht", "Tratado de Versalles", "Tratado de los Pirineos"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Durante qué reinado se produjo la Revolución de 1868, conocida como “La Gloriosa”? (Opción 1: Isabel II)
        Pregunta(
            texto = "¿Durante qué reinado se produjo la Revolución de 1868, conocida como “La Gloriosa”?",
            opciones = listOf("Isabel II", "Alfonso XII", "Amadeo I", "Alfonso XIII"),
            respuestaCorrecta = 0
        ),

        // Enunciado: ¿Qué general dirigió el golpe de Estado que dio inicio a la Guerra Civil Española en 1936? (Opción 3: Francisco Franco)
        Pregunta(
            texto = "¿Qué general dirigió el golpe de Estado que dio inicio a la Guerra Civil Española en 1936?",
            opciones = listOf("Miguel Primo de Rivera", "Emilio Mola", "Francisco Franco", "José Sanjurjo"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Qué dinastía sustituyó a los Austrias en el trono español tras la Guerra de Sucesión? (Opción 3: Los Borbones)
        Pregunta(
            texto = "¿Qué dinastía sustituyó a los Austrias en el trono español tras la Guerra de Sucesión?",
            opciones = listOf("Los Trastámara", "Los Habsburgo", "Los Borbones", "Los Saboya"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Qué evento marcó el fin de la Reconquista? (Opción 3: La rendición de Granada)
        Pregunta(
            texto = "¿Qué evento marcó el fin de la Reconquista?",
            opciones = listOf("La toma de Toledo", "La batalla de las Navas de Tolosa", "La rendición de Granada", "La unión de Castilla y Aragón"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Quién fue el líder republicano que presidió el Gobierno durante los primeros meses de la Guerra Civil? (Opción 2: Manuel Azaña)
        Pregunta(
            texto = "¿Quién fue el líder republicano que presidió el Gobierno durante los primeros meses de la Guerra Civil?",
            opciones = listOf("Niceto Alcalá-Zamora", "Manuel Azaña", "Largo Caballero", "Juan Negrín"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Qué hecho histórico se conoce como el “Desastre del 98”? (Opción 2: La pérdida de Cuba, Filipinas y Puerto Rico)
        Pregunta(
            texto = "¿Qué hecho histórico se conoce como el “Desastre del 98”?",
            opciones = listOf("La derrota española frente a Napoleón", "La pérdida de Cuba, Filipinas y Puerto Rico", "La proclamación de la Primera República", "La invasión francesa de la Península"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Qué rey impulsó la construcción del Palacio Real de Madrid tal como se conserva hoy? (Opción 2: Felipe V)
        Pregunta(
            texto = "¿Qué rey impulsó la construcción del Palacio Real de Madrid tal como se conserva hoy?",
            opciones = listOf("Carlos II", "Felipe V", "Carlos III", "Felipe IV"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Qué constitución española estableció por primera vez la soberanía nacional y la división de poderes? (Opción 3: Constitución de Cádiz de 1812)
        Pregunta(
            texto = "¿Qué constitución española estableció por primera vez la soberanía nacional y la división de poderes?",
            opciones = listOf("Constitución de 1837", "Constitución de 1869", "Constitución de Cádiz de 1812", "Constitución de 1931"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Qué plan económico impulsó el desarrollo industrial y turístico de España durante el franquismo? (Opción 1: Plan de Estabilización de 1959)
        Pregunta(
            texto = "¿Qué plan económico impulsó el desarrollo industrial y turístico de España durante el franquismo?",
            opciones = listOf("Plan de Estabilización de 1959", "Plan Marshall", "Plan de Reconstrucción Nacional", "Plan de Modernización de 1967"),
            respuestaCorrecta = 0
        ),
    ),

    // ----------------------------------------------------
    // POOL DE 10 PREGUNTAS: VIDEOJUEGOS
    // ----------------------------------------------------
    Tema.VIDEOJUEGOS to listOf(
        // Enunciado: ¿Cómo se llama el juego al que pertenece esta imagen? (Opción 2: Devil May Cry 5)
        Pregunta(
            texto = "¿Cual de estos juegos pertenece a la saga mas larga?",
            opciones = listOf("Red Dead Redemption 2", "Devil May Cry 5", "Dragon Age Origins", "Baldur's Gate III"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Cual de estos juegos uso antes el 3D? (Opción 3: Final Fantasy VII)
        Pregunta(
            texto = "¿Cual de estos juegos uso antes el 3D?",
            opciones = listOf("GTA III", "Call of Duty 4: Modern Warfare", "Final Fantasy VII", "Dragon Quest VIII"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Cual de estos juegos gano mas premios? (Opción 3: Elden Ring)
        Pregunta(
            texto = "¿Cual de estos juegos gano mas premios?",
            opciones = listOf("God of War (2018)", "Sekiro: Shadows Die Twice", "Elden Ring", "Red Dead Redemption 2"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Cuál de estos juegos tubo mayor impacto en la industria? (Opción 3: Super Mario Bros)
        Pregunta(
            texto = "¿Cuál de estos juegos tubo mayor impacto en la industria?",
            opciones = listOf("Doom", "Minecraft", "Super Mario Bros", "World of Warcraft"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Cuál de estos juegos utiliza dos métodos de iluminación? (Opción 4: Cyberpunk 2077)
        Pregunta(
            texto = "¿Cuál de estos juegos utiliza dos métodos de iluminación?",
            opciones = listOf("Super Mario Galaxy", "Mass Effect", "Dragon Age Inquisition", "Cyberpunk 2077"),
            respuestaCorrecta = 3
        ),

        // Enunciado: ¿Cual de estos juegos es mas antiguo? (Opción 3: Pac-Man)
        Pregunta(
            texto = "¿Cual de estos juegos es mas antiguo?",
            opciones = listOf("The Legend of Zelda", "Metroid", "Pac-Man", "Street Fighter"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Cuál de estos personajes pertenece a la saga Metal Gear? (Opción 1: Solid Snake)
        Pregunta(
            texto = "¿Cuál de estos personajes pertenece a la saga Metal Gear?",
            opciones = listOf("Solid Snake", "Marcus Fenix", "Sam Fisher", "Leon S. Kennedy"),
            respuestaCorrecta = 0
        ),

        // Enunciado: ¿Qué compañía creó la consola Dreamcast? (Opción 2: Sega)
        Pregunta(
            texto = "¿Qué compañía creó la consola Dreamcast?",
            opciones = listOf("Sony", "Sega", "Nintendo", "Atari"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Cuál de estos juegos es considerado un pionero del género Battle Royale? (Opción 4: H1Z1)
        Pregunta(
            texto = "¿Cuál de estos juegos es considerado un pionero del género Battle Royale?",
            opciones = listOf("PUBG", "Fortnite", "Apex Legends", "H1Z1"),
            respuestaCorrecta = 3
        ),

        // Enunciado: ¿Qué juego fue el primero en incluir un mundo abierto en 3D completamente explorable? (Opción 2: Super Mario 64)
        Pregunta(
            texto = "¿Qué juego fue el primero en incluir un mundo abierto en 3D completamente explorable?",
            opciones = listOf("The Elder Scrolls III: Morrowind", "Super Mario 64", "Assassin’s Creed", "The Legend of Zelda: Ocarina of Time"),
            respuestaCorrecta = 1
        ),
    ),

    // ----------------------------------------------------
    // POOL DE 10 PREGUNTAS: DEPORTES
    // ----------------------------------------------------
    Tema.DEPORTES to listOf(
        // Enunciado: ¿Qué club de fútbol español ha ganado más títulos de LaLiga? (Opción 3: Real Madrid)
        Pregunta(
            texto = "¿Qué club de fútbol español ha ganado más títulos de LaLiga?",
            opciones = listOf("Atlético de Madrid", "FC Barcelona", "Real Madrid", "Athletic Club"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿En qué año ganó España su primer Mundial de fútbol? (Opción 3: 2010)
        Pregunta(
            texto = "¿En qué año ganó España su primer Mundial de fútbol?",
            opciones = listOf("2006", "2008", "2010", "2012"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Quién es el máximo goleador histórico de la selección española de fútbol? (Opción 3: David Villa)
        Pregunta(
            texto = "¿Quién es el máximo goleador histórico de la selección española de fútbol?",
            opciones = listOf("Fernando Torres", "Raúl González", "David Villa", "Álvaro Morata"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Qué piloto español ha ganado más títulos de Fórmula 1? (Opción 2: Fernando Alonso)
        Pregunta(
            texto = "¿Qué piloto español ha ganado más títulos de Fórmula 1?",
            opciones = listOf("Carlos Sainz", "Fernando Alonso", "Pedro de la Rosa", "Marc Gené"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Qué tenista español es conocido como el “Rey de la Tierra Batida”? (Opción 2: Rafael Nadal)
        Pregunta(
            texto = "¿Qué tenista español es conocido como el “Rey de la Tierra Batida”?",
            opciones = listOf("David Ferrer", "Rafael Nadal", "Carlos Alcaraz", "Juan Carlos Ferrero"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Qué ciudad española fue sede de los Juegos Olímpicos de 1992? (Opción 2: Barcelona)
        Pregunta(
            texto = "¿Qué ciudad española fue sede de los Juegos Olímpicos de 1992?",
            opciones = listOf("Madrid", "Barcelona", "Sevilla", "Valencia"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿En qué deporte destacó la atleta española Mireia Belmonte? (Opción 3: Natación)
        Pregunta(
            texto = "¿En qué deporte destacó la atleta española Mireia Belmonte?",
            opciones = listOf("Atletismo", "Gimnasia", "Natación", "Ciclismo"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Cuál es el apodo del club de fútbol Athletic Club de Bilbao? (Opción 1: Los Leones)
        Pregunta(
            texto = "¿Cuál es el apodo del club de fútbol Athletic Club de Bilbao?",
            opciones = listOf("Los Leones", "Los Tigres", "Los Toros", "Los Guerreros"),
            respuestaCorrecta = 0
        ),

        // Enunciado: ¿Qué equipo ganó la Liga ACB de baloncesto en 2024? (Opción 2: Real Madrid)
        Pregunta(
            texto = "¿Qué equipo ganó la Liga ACB de baloncesto en 2024?",
            opciones = listOf("FC Barcelona", "Real Madrid", "Valencia Basket", "Joventut Badalona"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Qué jugador español fue MVP del Mundial de baloncesto FIBA 2006? (Opción 2: Pau Gasol)
        Pregunta(
            texto = "¿Qué jugador español fue MVP del Mundial de baloncesto FIBA 2006?",
            opciones = listOf("Juan Carlos Navarro", "Pau Gasol", "José Calderón", "Rudy Fernández"),
            respuestaCorrecta = 1
        ),
    ),

    // ----------------------------------------------------
    // POOL DE 10 PREGUNTAS: ARTE
    // ----------------------------------------------------
    Tema.ARTE to listOf(
        // Enunciado: ¿Quién pintó “La noche estrellada”? (Opción 3: Vincent van Gogh)
        Pregunta(
            texto = "¿Quién pintó “La noche estrellada”?",
            opciones = listOf("Claude Monet", "Pablo Picasso", "Vincent van Gogh", "Salvador Dalí"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Quién compuso la banda sonora original de The Legend of Zelda? (Opción 2: Koji Kondo)
        Pregunta(
            texto = "¿Quién compuso la banda sonora original de The Legend of Zelda?",
            opciones = listOf("Nobuo Uematsu", "Koji Kondo", "Yoko Shimomura", "Akira Yamaoka"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Qué instrumento musical tiene teclas, cuerdas y martillos? (Opción 3: Piano)
        Pregunta(
            texto = "¿Qué instrumento musical tiene teclas, cuerdas y martillos?",
            opciones = listOf("Violín", "Arpa", "Piano", "Guitarra"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿En qué museo se encuentra “La Gioconda” (Mona Lisa)? (Opción 2: Museo del Louvre)
        Pregunta(
            texto = "¿En qué museo se encuentra “La Gioconda” (Mona Lisa)?",
            opciones = listOf("Museo del Prado", "Museo del Louvre", "National Gallery", "Uffizi"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Qué videojuego es conocido por su innovadora banda sonora dinámica que cambia según las acciones del jugador? (Opción 2: Journey)
        Pregunta(
            texto = "¿Qué videojuego es conocido por su innovadora banda sonora dinámica que cambia según las acciones del jugador?",
            opciones = listOf("Tetris", "Journey", "Cuphead", "Minecraft"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Quién es conocido como “El Rey del Pop”? (Opción 1: Michael Jackson)
        Pregunta(
            texto = "¿Quién es conocido como “El Rey del Pop”?",
            opciones = listOf("Michael Jackson", "Elvis Presley", "Prince", "Freddie Mercury"),
            respuestaCorrecta = 0
        ),

        // Enunciado: ¿A qué corriente artística pertenece Salvador Dalí? (Opción 3: Surrealismo)
        Pregunta(
            texto = "¿A qué corriente artística pertenece Salvador Dalí?",
            opciones = listOf("Cubismo", "Impresionismo", "Surrealismo", "Expresionismo"),
            respuestaCorrecta = 2
        ),

        // Enunciado: ¿Cuál de los siguientes videojuegos es famoso por su estilo visual inspirado en el arte japonés tradicional? (Opción 2: Ōkami)
        Pregunta(
            texto = "¿Cuál de los siguientes videojuegos es famoso por su estilo visual inspirado en el arte japonés tradicional?",
            opciones = listOf("Journey", "Ōkami", "Hollow Knight", "Celeste"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Qué compositor creó la “Sinfonía n.º 9” que incluye la “Oda a la Alegría”? (Opción 2: Beethoven)
        Pregunta(
            texto = "¿Qué compositor creó la “Sinfonía n.º 9” que incluye la “Oda a la Alegría”?",
            opciones = listOf("Mozart", "Beethoven", "Bach", "Tchaikovsky"),
            respuestaCorrecta = 1
        ),

        // Enunciado: ¿Qué videojuego musical consiste en tocar notas al ritmo con una guitarra de plástico? (Opción 2: Guitar Hero)
        Pregunta(
            texto = "¿Qué videojuego musical consiste en tocar notas al ritmo con una guitarra de plástico?",
            opciones = listOf("Rocksmith", "Guitar Hero", "Beat Saber", "Taiko no Tatsujin"),
            respuestaCorrecta = 1
        ),
    )
)