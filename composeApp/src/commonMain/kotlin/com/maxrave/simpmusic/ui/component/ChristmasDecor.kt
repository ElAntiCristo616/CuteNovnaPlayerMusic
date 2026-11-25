package com.maxrave.simpmusic.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.maxrave.simpmusic.ui.theme.cutenovnaGold
import com.maxrave.simpmusic.ui.theme.cutenovnaRed
import com.maxrave.simpmusic.ui.theme.cutenovnaSage
import com.maxrave.simpmusic.ui.theme.cutenovnaSnow
import kotlin.math.sin
import kotlin.random.Random

/**
 * CuteNovna: nieve animada discreta sobre la pantalla.
 *
 * Unos ~40 copos caen en bucle con velocidades, tamaños y derivas distintas.
 * No consume toques: es solo un [Canvas] sin entrada de puntero, así que la
 * pantalla de abajo sigue recibiendo todos los gestos.
 */
@Composable
fun SnowfallOverlay(modifier: Modifier = Modifier) {
    val flakes =
        remember {
            List(42) {
                Snowflake(
                    x = Random.nextFloat(),
                    speed = 0.45f + Random.nextFloat() * 0.9f,
                    radius = 1.2f + Random.nextFloat() * 2.6f,
                    drift = 0.01f + Random.nextFloat() * 0.035f,
                    phase = Random.nextFloat(),
                    alpha = 0.25f + Random.nextFloat() * 0.45f,
                )
            }
        }
    val transition = rememberInfiniteTransition(label = "cutenovna-snowfall")
    // Un bucle de 90 s: cada copo avanza (tiempo * velocidad + fase) % 1 de alto.
    val time by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(90_000, easing = LinearEasing)),
        label = "cutenovna-snow-time",
    )
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        for (flake in flakes) {
            val progress = (time * flake.speed + flake.phase) % 1f
            val y = progress * height
            val sway = sin((progress * 6.2832f) + flake.phase * 6.2832f) * flake.drift * width
            val x = (flake.x * width + sway).coerceIn(0f, width)
            drawCircle(
                color = cutenovnaSnow,
                radius = flake.radius,
                center = Offset(x, y),
                alpha = flake.alpha,
            )
        }
    }
}

private data class Snowflake(
    val x: Float,
    val speed: Float,
    val radius: Float,
    val drift: Float,
    val phase: Float,
    val alpha: Float,
)

/**
 * CuteNovna: guirnalda de luces navideñas para la parte superior de la pantalla.
 *
 * Un cable que cuelga en ondas suaves con focos rojo, dorado y verde salvia.
 * Solo dibuja; no intercepta toques.
 */
@Composable
fun ChristmasLightsStrip(modifier: Modifier = Modifier) {
    val bulbColors =
        remember {
            listOf(cutenovnaRed, cutenovnaGold, cutenovnaSage, cutenovnaSnow)
        }
    Canvas(
        modifier =
            modifier
                .fillMaxWidth()
                .height(26.dp),
    ) {
        val width = size.width
        val height = size.height
        val bulbCount = 14
        val spacing = width / bulbCount
        // Cable: una onda suave de lado a lado.
        val wirePath =
            Path().apply {
                moveTo(0f, height * 0.25f)
                var x = 0f
                var up = false
                while (x < width) {
                    val nextX = (x + spacing).coerceAtMost(width)
                    val midX = (x + nextX) / 2f
                    val midY = if (up) height * 0.15f else height * 0.55f
                    quadraticTo(midX, midY, nextX, height * 0.25f)
                    x = nextX
                    up = !up
                }
            }
        drawPath(
            path = wirePath,
            color = Color(0xFF1E3A2C),
            style = Stroke(width = 1.5f),
        )
        // Focos colgando del cable, alternando colores navideños.
        for (i in 0 until bulbCount) {
            val cx = spacing * (i + 0.5f)
            val wave = sin((i.toFloat() / bulbCount) * 3.14159f * 2f) * 0.5f + 0.5f
            val cy = height * (0.30f + 0.30f * wave)
            val color = bulbColors[i % bulbColors.size]
            // Halo suave y foco.
            drawCircle(color = color, radius = 7f, center = Offset(cx, cy), alpha = 0.25f)
            drawCircle(color = color, radius = 3.5f, center = Offset(cx, cy))
            drawCircle(color = cutenovnaSnow, radius = 1.2f, center = Offset(cx - 1f, cy - 1f), alpha = 0.8f)
        }
    }
}
