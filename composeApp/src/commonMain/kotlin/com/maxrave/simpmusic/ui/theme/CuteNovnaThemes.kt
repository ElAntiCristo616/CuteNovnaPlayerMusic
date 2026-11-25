package com.maxrave.simpmusic.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

/**
 * CuteNovna v1.0.3: sistema de temas propio.
 *
 * Cada tema cambia TODA la app (fondo, superficies, acento, texto y secundarios)
 * porque su paleta se aplica sobre el ColorScheme generado, igual que el remate
 * navideño de antes. El tema por defecto es Navidad (el de siempre).
 * La elección se guarda en DataStore (clave "cutenovna_theme") y sobrevive al
 * cerrar y abrir la app. Solo Navidad es claro de los 13; el resto son oscuros.
 * Para las pantallas inmersivas (reproductor, artista...) que siempre van en
 * oscuro, cada tema claro trae su [darkPalette].
 */

/** Partícula animada que acompaña a cada tema. */
enum class ParticleKind {
    SNOW,
    SPARKLE,
    PETALS,
    EMBERS,
    BUBBLES,
    STARS,
    LEAVES,
    GOLD_DUST,
    NEON_SPARKS,
    AUTUMN_LEAVES,
    HEARTS,
    RAIN,
    LIGHT_DUST,
}

/**
 * Los colores que definen un tema. El resto de slots del ColorScheme se derivan
 * de estos en [ColorScheme.withCuteNovnaPalette], así cada tema queda coherente
 * sin repetir ~30 líneas por tema.
 */
data class ThemePalette(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
)

data class CuteNovnaTheme(
    val id: String,
    val name: String,
    /** Semilla ARGB de 8 dígitos (sin '#') para el esquema generado. */
    val seedHex: String,
    val isDark: Boolean,
    val particle: ParticleKind,
    val palette: ThemePalette,
    /** Paleta oscura para pantallas inmersivas cuando el tema es claro. */
    val darkPalette: ThemePalette? = null,
) {
    companion object {
        val NAVIDAD =
            CuteNovnaTheme(
                id = "navidad",
                name = "Navidad",
                seedHex = "FF0E3426",
                isDark = true,
                particle = ParticleKind.SNOW,
                palette =
                    ThemePalette(
                        background = Color(0xFF04140E),
                        surface = Color(0xFF0E3426),
                        primary = Color(0xFFCD2D32),
                        onPrimary = Color(0xFFF5F8F5),
                        secondary = Color(0xFFA8C3B5),
                        onSecondary = Color(0xFF04140E),
                        tertiary = Color(0xFFD9A441),
                        onTertiary = Color(0xFF04140E),
                        onBackground = Color(0xFFF5F8F5),
                        onSurface = Color(0xFFF5F8F5),
                        onSurfaceVariant = Color(0xFFA8C3B5),
                        outline = Color(0xFF6E8A7C),
                    ),
            )

        val INVIERNO =
            CuteNovnaTheme(
                id = "invierno",
                name = "Invierno Ártico",
                seedHex = "FF0C1B2E",
                isDark = true,
                particle = ParticleKind.SPARKLE,
                palette =
                    ThemePalette(
                        background = Color(0xFF060D18),
                        surface = Color(0xFF0C1B2E),
                        primary = Color(0xFF7FD4FF),
                        onPrimary = Color(0xFF06263A),
                        secondary = Color(0xFFBFE9FF),
                        onSecondary = Color(0xFF06263A),
                        tertiary = Color(0xFFE8F6FF),
                        onTertiary = Color(0xFF0C1B2E),
                        onBackground = Color(0xFFEFF7FF),
                        onSurface = Color(0xFFEFF7FF),
                        onSurfaceVariant = Color(0xFF9CC3D9),
                        outline = Color(0xFF5E7F95),
                    ),
            )

        val SAKURA =
            CuteNovnaTheme(
                id = "sakura",
                name = "Sakura",
                seedHex = "FF2E1622",
                isDark = true,
                particle = ParticleKind.PETALS,
                palette =
                    ThemePalette(
                        background = Color(0xFF1C0E16),
                        surface = Color(0xFF2E1622),
                        primary = Color(0xFFFF7FA8),
                        onPrimary = Color(0xFF2E1622),
                        secondary = Color(0xFFFFB3C8),
                        onSecondary = Color(0xFF2E1622),
                        tertiary = Color(0xFFFFD6E4),
                        onTertiary = Color(0xFF2E1622),
                        onBackground = Color(0xFFFFF0F5),
                        onSurface = Color(0xFFFFF0F5),
                        onSurfaceVariant = Color(0xFFD9A7B8),
                        outline = Color(0xFF8A5A6E),
                    ),
            )

        val FUEGO =
            CuteNovnaTheme(
                id = "fuego",
                name = "Fuego",
                seedHex = "FF1F0A06",
                isDark = true,
                particle = ParticleKind.EMBERS,
                palette =
                    ThemePalette(
                        background = Color(0xFF0D0503),
                        surface = Color(0xFF1F0A06),
                        primary = Color(0xFFFF6B35),
                        onPrimary = Color(0xFF1F0A06),
                        secondary = Color(0xFFFFB347),
                        onSecondary = Color(0xFF1F0A06),
                        tertiary = Color(0xFFFF3B30),
                        onTertiary = Color(0xFFFFF1E6),
                        onBackground = Color(0xFFFFF1E6),
                        onSurface = Color(0xFFFFF1E6),
                        onSurfaceVariant = Color(0xFFD9A08C),
                        outline = Color(0xFF7A4A38),
                    ),
            )

        val OCEANO =
            CuteNovnaTheme(
                id = "oceano",
                name = "Océano",
                seedHex = "FF062A38",
                isDark = true,
                particle = ParticleKind.BUBBLES,
                palette =
                    ThemePalette(
                        background = Color(0xFF03141C),
                        surface = Color(0xFF062A38),
                        primary = Color(0xFF2EC4B6),
                        onPrimary = Color(0xFF062A38),
                        secondary = Color(0xFF7FE3D8),
                        onSecondary = Color(0xFF062A38),
                        tertiary = Color(0xFF4CC9F0),
                        onTertiary = Color(0xFF062A38),
                        onBackground = Color(0xFFE6F7F5),
                        onSurface = Color(0xFFE6F7F5),
                        onSurfaceVariant = Color(0xFF8FBFB8),
                        outline = Color(0xFF4A7A72),
                    ),
            )

        val GALAXIA =
            CuteNovnaTheme(
                id = "galaxia",
                name = "Galaxia",
                seedHex = "FF150F2E",
                isDark = true,
                particle = ParticleKind.STARS,
                palette =
                    ThemePalette(
                        background = Color(0xFF080614),
                        surface = Color(0xFF150F2E),
                        primary = Color(0xFFB388FF),
                        onPrimary = Color(0xFF150F2E),
                        secondary = Color(0xFFE1BEE7),
                        onSecondary = Color(0xFF150F2E),
                        tertiary = Color(0xFF7C4DFF),
                        onTertiary = Color(0xFFEFE9FF),
                        onBackground = Color(0xFFEFE9FF),
                        onSurface = Color(0xFFEFE9FF),
                        onSurfaceVariant = Color(0xFFB3A6D9),
                        outline = Color(0xFF6E6390),
                    ),
            )

        val BOSQUE =
            CuteNovnaTheme(
                id = "bosque",
                name = "Bosque",
                seedHex = "FF16260F",
                isDark = true,
                particle = ParticleKind.LEAVES,
                palette =
                    ThemePalette(
                        background = Color(0xFF0A1408),
                        surface = Color(0xFF16260F),
                        primary = Color(0xFF8BC34A),
                        onPrimary = Color(0xFF16260F),
                        secondary = Color(0xFFC5E1A5),
                        onSecondary = Color(0xFF16260F),
                        tertiary = Color(0xFF558B2F),
                        onTertiary = Color(0xFFEAF5DF),
                        onBackground = Color(0xFFEAF5DF),
                        onSurface = Color(0xFFEAF5DF),
                        onSurfaceVariant = Color(0xFFA9BFA0),
                        outline = Color(0xFF5E7A52),
                    ),
            )

        val ATARDECER =
            CuteNovnaTheme(
                id = "atardecer",
                name = "Atardecer",
                seedHex = "FF2A1420",
                isDark = true,
                particle = ParticleKind.GOLD_DUST,
                palette =
                    ThemePalette(
                        background = Color(0xFF170B12),
                        surface = Color(0xFF2A1420),
                        primary = Color(0xFFFF8A65),
                        onPrimary = Color(0xFF2A1420),
                        secondary = Color(0xFFCE93D8),
                        onSecondary = Color(0xFF2A1420),
                        tertiary = Color(0xFFFFB74D),
                        onTertiary = Color(0xFF2A1420),
                        onBackground = Color(0xFFFFEBE8),
                        onSurface = Color(0xFFFFEBE8),
                        onSurfaceVariant = Color(0xFFD9A7B0),
                        outline = Color(0xFF8A5A66),
                    ),
            )

        val NEON =
            CuteNovnaTheme(
                id = "neon",
                name = "Neón",
                seedHex = "FF0D0D12",
                isDark = true,
                particle = ParticleKind.NEON_SPARKS,
                palette =
                    ThemePalette(
                        background = Color(0xFF000000),
                        surface = Color(0xFF0D0D12),
                        primary = Color(0xFFFF2E97),
                        onPrimary = Color(0xFF0D0D12),
                        secondary = Color(0xFF00E5FF),
                        onSecondary = Color(0xFF0D0D12),
                        tertiary = Color(0xFFB967FF),
                        onTertiary = Color(0xFFF2E6FF),
                        onBackground = Color(0xFFF2F2F2),
                        onSurface = Color(0xFFF2F2F2),
                        onSurfaceVariant = Color(0xFF9A9AB0),
                        outline = Color(0xFF55556A),
                    ),
            )

        val OTONO =
            CuteNovnaTheme(
                id = "otono",
                name = "Otoño",
                seedHex = "FF241708",
                isDark = true,
                particle = ParticleKind.AUTUMN_LEAVES,
                palette =
                    ThemePalette(
                        background = Color(0xFF140C06),
                        surface = Color(0xFF241708),
                        primary = Color(0xFFE67E22),
                        onPrimary = Color(0xFF241708),
                        secondary = Color(0xFFD9A441),
                        onSecondary = Color(0xFF241708),
                        tertiary = Color(0xFFA0522D),
                        onTertiary = Color(0xFFF5E6CC),
                        onBackground = Color(0xFFF5E6CC),
                        onSurface = Color(0xFFF5E6CC),
                        onSurfaceVariant = Color(0xFFC2A878),
                        outline = Color(0xFF7A5C38),
                    ),
            )

        val DULCE =
            CuteNovnaTheme(
                id = "dulce",
                name = "Dulce",
                seedHex = "FF33182A",
                isDark = true,
                particle = ParticleKind.HEARTS,
                palette =
                    ThemePalette(
                        background = Color(0xFF1E0F16),
                        surface = Color(0xFF33182A),
                        primary = Color(0xFFFF9EBB),
                        onPrimary = Color(0xFF33182A),
                        secondary = Color(0xFFFFD6E0),
                        onSecondary = Color(0xFF33182A),
                        tertiary = Color(0xFFFFF1A8),
                        onTertiary = Color(0xFF33182A),
                        onBackground = Color(0xFFFFF0F3),
                        onSurface = Color(0xFFFFF0F3),
                        onSurfaceVariant = Color(0xFFE0A8B8),
                        outline = Color(0xFF8A5A6E),
                    ),
            )

        val LLUVIA =
            CuteNovnaTheme(
                id = "lluvia",
                name = "Lluvia",
                seedHex = "FF16222B",
                isDark = true,
                particle = ParticleKind.RAIN,
                palette =
                    ThemePalette(
                        background = Color(0xFF0B1116),
                        surface = Color(0xFF16222B),
                        primary = Color(0xFF7FA8C9),
                        onPrimary = Color(0xFF16222B),
                        secondary = Color(0xFFA8C6D8),
                        onSecondary = Color(0xFF16222B),
                        tertiary = Color(0xFF5B7A99),
                        onTertiary = Color(0xFFE8F1F7),
                        onBackground = Color(0xFFE8F1F7),
                        onSurface = Color(0xFFE8F1F7),
                        onSurfaceVariant = Color(0xFF9AB0C0),
                        outline = Color(0xFF5E7386),
                    ),
            )

        val CLARO =
            CuteNovnaTheme(
                id = "claro",
                name = "Claro",
                seedHex = "FFB4552D",
                isDark = false,
                particle = ParticleKind.LIGHT_DUST,
                palette =
                    ThemePalette(
                        background = Color(0xFFFAF6EE),
                        surface = Color(0xFFFFFDF7),
                        primary = Color(0xFFB4552D),
                        onPrimary = Color(0xFFFFFDF7),
                        secondary = Color(0xFF5F7A5A),
                        onSecondary = Color(0xFFFFFDF7),
                        tertiary = Color(0xFFC9A227),
                        onTertiary = Color(0xFF241708),
                        onBackground = Color(0xFF241F1A),
                        onSurface = Color(0xFF241F1A),
                        onSurfaceVariant = Color(0xFF6B6257),
                        outline = Color(0xFFA89E8F),
                    ),
                // El reproductor y las pantallas inmersivas siempre van en oscuro.
                darkPalette =
                    ThemePalette(
                        background = Color(0xFF1C1712),
                        surface = Color(0xFF2A231B),
                        primary = Color(0xFFE08A5A),
                        onPrimary = Color(0xFF2A231B),
                        secondary = Color(0xFF9DB894),
                        onSecondary = Color(0xFF2A231B),
                        tertiary = Color(0xFFD9B45B),
                        onTertiary = Color(0xFF2A231B),
                        onBackground = Color(0xFFF5EFE2),
                        onSurface = Color(0xFFF5EFE2),
                        onSurfaceVariant = Color(0xFFB8AD9C),
                        outline = Color(0xFF7A6F60),
                    ),
            )

        val ALL =
            listOf(
                NAVIDAD,
                INVIERNO,
                SAKURA,
                FUEGO,
                OCEANO,
                GALAXIA,
                BOSQUE,
                ATARDECER,
                NEON,
                OTONO,
                DULCE,
                LLUVIA,
                CLARO,
            )

        fun fromId(id: String?): CuteNovnaTheme = ALL.firstOrNull { it.id == id } ?: NAVIDAD
    }
}

/** El tema activo, para que Inicio, el reproductor y el selector lo lean. */
val LocalCuteNovnaTheme = staticCompositionLocalOf { CuteNovnaTheme.NAVIDAD }

/**
 * Aplica una paleta CuteNovna a un ColorScheme generado: fondo, superficies,
 * acento, texto y secundarios cambian a la vez; los contenedores se derivan
 * de fondo/superficie para que cada tema quede coherente. Los slots de error
 * y scrim se dejan como los genera el esquema (no dependen del tema).
 */
fun ColorScheme.withCuteNovnaPalette(
    p: ThemePalette,
    isDark: Boolean,
): ColorScheme =
    copy(
        primary = p.primary,
        onPrimary = p.onPrimary,
        primaryContainer = lerp(p.primary, p.background, 0.6f),
        onPrimaryContainer = lerp(p.primary, p.onBackground, 0.45f),
        secondary = p.secondary,
        onSecondary = p.onSecondary,
        secondaryContainer = lerp(p.secondary, p.background, 0.6f),
        onSecondaryContainer = lerp(p.secondary, p.onBackground, 0.45f),
        tertiary = p.tertiary,
        onTertiary = p.onTertiary,
        tertiaryContainer = lerp(p.tertiary, p.background, 0.6f),
        onTertiaryContainer = lerp(p.tertiary, p.onBackground, 0.45f),
        background = p.background,
        onBackground = p.onBackground,
        surface = p.surface,
        onSurface = p.onSurface,
        surfaceVariant = lerp(p.surface, p.onSurface, 0.08f),
        onSurfaceVariant = p.onSurfaceVariant,
        surfaceTint = p.primary,
        surfaceBright = if (isDark) lerp(p.surface, Color.White, 0.12f) else Color.White,
        surfaceDim = lerp(p.background, p.surface, 0.5f),
        surfaceContainerLowest = p.background,
        surfaceContainerLow = lerp(p.background, p.surface, 0.25f),
        surfaceContainer = p.surface,
        surfaceContainerHigh = lerp(p.surface, p.onSurface, 0.08f),
        surfaceContainerHighest = lerp(p.surface, p.onSurface, 0.16f),
        outline = p.outline,
        outlineVariant = lerp(p.outline, p.background, 0.5f),
        inverseSurface = p.onBackground,
        inverseOnSurface = p.background,
    )
