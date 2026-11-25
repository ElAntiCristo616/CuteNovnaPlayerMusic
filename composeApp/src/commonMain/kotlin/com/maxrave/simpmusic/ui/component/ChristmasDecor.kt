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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import com.maxrave.simpmusic.ui.theme.CuteNovnaTheme
import com.maxrave.simpmusic.ui.theme.ParticleKind
import com.maxrave.simpmusic.ui.theme.cutenovnaGold
import com.maxrave.simpmusic.ui.theme.cutenovnaRed
import com.maxrave.simpmusic.ui.theme.cutenovnaSage
import com.maxrave.simpmusic.ui.theme.cutenovnaSnow
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * CuteNovna v1.0.3: partículas animadas del tema activo, sobre toda la app.
 *
 * Unos ~40 elementos en un [Canvas] sin entrada de puntero: la pantalla de
 * abajo sigue recibiendo todos los gestos. Cada tema tiene su movimiento y su
 * forma (nieve que cae, pétalos, brasas que suben, burbujas, estrellas que
 * titilan, hojas, polvo dorado, chispas neón, corazones, lluvia, polvo de luz).
 * Un solo bucle de 90 s mueve a todas; el coste es el mismo que tenía la nieve.
 */
@Composable
fun ThemeParticlesOverlay(
    theme: CuteNovnaTheme,
    modifier: Modifier = Modifier,
) {
    val spec = particleSpecFor(theme.particle)
    val particles =
        remember(theme.particle) {
            List(spec.count) {
                Particle(
                    x = Random.nextFloat(),
                    y = Random.nextFloat(),
                    speed = spec.minSpeed + Random.nextFloat() * (spec.maxSpeed - spec.minSpeed),
                    radius = spec.minRadius + Random.nextFloat() * (spec.maxRadius - spec.minRadius),
                    phase = Random.nextFloat(),
                    alpha = 0.25f + Random.nextFloat() * 0.5f,
                    color = spec.colors.random(),
                    sway = Random.nextFloat() * spec.sway,
                    rotation = Random.nextFloat() * 360f,
                    spin = (Random.nextFloat() - 0.5f) * 120f,
                    shape = spec.shapes.random(),
                    outline = spec.outline && Random.nextFloat() > 0.5f,
                )
            }
        }
    val transition = rememberInfiniteTransition(label = "cutenovna-particles")
    // Un bucle de 90 s: cada partícula avanza (tiempo * velocidad + fase) % 1.
    val time by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(90_000, easing = LinearEasing)),
        label = "cutenovna-particles-time",
    )
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val tau = 6.2832f
        for (p in particles) {
            val progress = (time * p.speed + p.phase) % 1f
            val angle = (progress * tau) + p.phase * tau
            val (x, y) =
                when (spec.motion) {
                    ParticleMotion.FALL -> {
                        val y = progress * height
                        val x = (p.x * width + sin(angle) * p.sway * width).coerceIn(0f, width)
                        x to y
                    }
                    ParticleMotion.RISE -> {
                        val y = (1f - progress) * height
                        val x = (p.x * width + sin(angle) * p.sway * width).coerceIn(0f, width)
                        x to y
                    }
                    ParticleMotion.FLOAT -> {
                        val y = (p.y * height + sin(angle) * height * 0.04f).coerceIn(0f, height)
                        val x = (p.x * width + cos(angle) * width * 0.03f).coerceIn(0f, width)
                        x to y
                    }
                    ParticleMotion.TWINKLE -> p.x * width to p.y * height
                }
            // Titileo: las estrellas, el polvo y las chispas respiran; las brasas parpadean.
            val alpha =
                when (spec.motion) {
                    ParticleMotion.TWINKLE -> p.alpha * (0.3f + 0.7f * (0.5f + 0.5f * sin(angle * 2f)))
                    ParticleMotion.FLOAT -> p.alpha * (0.5f + 0.5f * (0.5f + 0.5f * sin(angle)))
                    else -> p.alpha
                } * if (spec.flicker) (0.6f + 0.4f * sin(angle * 3f)) else 1f
            val center = Offset(x, y)
            when (p.shape) {
                ParticleShape.DOT -> {
                    if (p.outline) {
                        drawCircle(
                            color = p.color,
                            radius = p.radius,
                            center = center,
                            alpha = alpha,
                            style = Stroke(width = 1.2f),
                        )
                    } else {
                        drawCircle(color = p.color, radius = p.radius, center = center, alpha = alpha)
                    }
                }
                ParticleShape.OVAL -> {
                    rotate(p.rotation + time * p.spin, center) {
                        drawOval(
                            color = p.color,
                            topLeft = Offset(center.x - p.radius * 1.6f, center.y - p.radius),
                            size = Size(p.radius * 3.2f, p.radius * 2f),
                            alpha = alpha,
                        )
                    }
                }
                ParticleShape.LINE -> {
                    // Lluvia: trazo corto y vertical, cayendo rápido.
                    drawLine(
                        color = p.color,
                        start = center,
                        end = Offset(center.x - p.radius * 0.4f, center.y + p.radius * 5f),
                        strokeWidth = 1.5f,
                        alpha = alpha,
                    )
                }
                ParticleShape.HEART -> {
                    translate(center.x, center.y) {
                        scale(p.radius / 10f, p.radius / 10f, pivot = Offset.Zero) {
                            drawPath(path = heartPath, color = p.color, alpha = alpha)
                        }
                    }
                }
                ParticleShape.STAR -> {
                    translate(center.x, center.y) {
                        scale(p.radius / 5f, p.radius / 5f, pivot = Offset.Zero) {
                            drawPath(path = starPath, color = p.color, alpha = alpha)
                        }
                    }
                }
            }
        }
    }
}

private enum class ParticleMotion {
    FALL,
    RISE,
    FLOAT,
    TWINKLE,
}

private enum class ParticleShape {
    DOT,
    OVAL,
    LINE,
    HEART,
    STAR,
}

private data class ParticleSpec(
    val motion: ParticleMotion,
    val shapes: List<ParticleShape>,
    val colors: List<Color>,
    val minRadius: Float,
    val maxRadius: Float,
    val minSpeed: Float,
    val maxSpeed: Float,
    val sway: Float,
    val count: Int = 40,
    /** Las brasas parpadean además de subir. */
    val flicker: Boolean = false,
    /** Algunas burbujas se dibujan solo con el contorno. */
    val outline: Boolean = false,
)

private data class Particle(
    val x: Float,
    val y: Float,
    val speed: Float,
    val radius: Float,
    val phase: Float,
    val alpha: Float,
    val color: Color,
    val sway: Float,
    val rotation: Float,
    val spin: Float,
    val shape: ParticleShape,
    val outline: Boolean,
)

/** Corazón centrado en (0,0), en una caja de -0.5..0.5. */
private val heartPath =
    Path().apply {
        moveTo(0f, 0.32f)
        cubicTo(-0.52f, -0.08f, -0.28f, -0.48f, 0f, -0.16f)
        cubicTo(0.28f, -0.48f, 0.52f, -0.08f, 0f, 0.32f)
        close()
    }

/** Estrella de 4 puntas (destello), centrada en (0,0), caja de -0.5..0.5. */
private val starPath =
    Path().apply {
        moveTo(0f, -0.5f)
        quadraticTo(0.08f, -0.08f, 0.5f, 0f)
        quadraticTo(0.08f, 0.08f, 0f, 0.5f)
        quadraticTo(-0.08f, 0.08f, -0.5f, 0f)
        quadraticTo(-0.08f, -0.08f, 0f, -0.5f)
        close()
    }

private fun particleSpecFor(kind: ParticleKind): ParticleSpec =
    when (kind) {
        ParticleKind.SNOW ->
            ParticleSpec(
                motion = ParticleMotion.FALL,
                shapes = listOf(ParticleShape.DOT),
                colors = listOf(Color(0xFFF5F8F5)),
                minRadius = 1.2f, maxRadius = 3.8f,
                minSpeed = 0.45f, maxSpeed = 1.35f,
                sway = 0.045f,
            )
        ParticleKind.SPARKLE ->
            ParticleSpec(
                motion = ParticleMotion.FALL,
                shapes = listOf(ParticleShape.DOT, ParticleShape.STAR),
                colors = listOf(Color(0xFFFFFFFF), Color(0xFFBFE9FF), Color(0xFF7FD4FF)),
                minRadius = 1f, maxRadius = 3f,
                minSpeed = 0.35f, maxSpeed = 1f,
                sway = 0.02f,
            )
        ParticleKind.PETALS ->
            ParticleSpec(
                motion = ParticleMotion.FALL,
                shapes = listOf(ParticleShape.OVAL),
                colors = listOf(Color(0xFFFFB3C8), Color(0xFFFF7FA8), Color(0xFFFFD6E4)),
                minRadius = 2.5f, maxRadius = 5f,
                minSpeed = 0.3f, maxSpeed = 0.8f,
                sway = 0.06f,
            )
        ParticleKind.EMBERS ->
            ParticleSpec(
                motion = ParticleMotion.RISE,
                shapes = listOf(ParticleShape.DOT),
                colors = listOf(Color(0xFFFF6B35), Color(0xFFFFB347), Color(0xFFFF3B30), Color(0xFFFFD27F)),
                minRadius = 1f, maxRadius = 3f,
                minSpeed = 0.5f, maxSpeed = 1.4f,
                sway = 0.03f,
                flicker = true,
            )
        ParticleKind.BUBBLES ->
            ParticleSpec(
                motion = ParticleMotion.RISE,
                shapes = listOf(ParticleShape.DOT),
                colors = listOf(Color(0xFF7FE3D8), Color(0xFF4CC9F0), Color(0xFFE6F7F5)),
                minRadius = 1.5f, maxRadius = 4.5f,
                minSpeed = 0.3f, maxSpeed = 0.9f,
                sway = 0.05f,
                outline = true,
            )
        ParticleKind.STARS ->
            ParticleSpec(
                motion = ParticleMotion.TWINKLE,
                shapes = listOf(ParticleShape.STAR, ParticleShape.DOT),
                colors = listOf(Color(0xFFFFFFFF), Color(0xFFB388FF), Color(0xFFE1BEE7)),
                minRadius = 2f, maxRadius = 5f,
                minSpeed = 0.15f, maxSpeed = 0.5f,
                sway = 0f,
            )
        ParticleKind.LEAVES ->
            ParticleSpec(
                motion = ParticleMotion.FALL,
                shapes = listOf(ParticleShape.OVAL),
                colors = listOf(Color(0xFF8BC34A), Color(0xFFC5E1A5), Color(0xFF558B2F)),
                minRadius = 2.5f, maxRadius = 5f,
                minSpeed = 0.25f, maxSpeed = 0.7f,
                sway = 0.06f,
            )
        ParticleKind.GOLD_DUST ->
            ParticleSpec(
                motion = ParticleMotion.FLOAT,
                shapes = listOf(ParticleShape.DOT, ParticleShape.STAR),
                colors = listOf(Color(0xFFFFD27F), Color(0xFFFFB74D), Color(0xFFFFE9B8)),
                minRadius = 1f, maxRadius = 2.5f,
                minSpeed = 0.2f, maxSpeed = 0.6f,
                sway = 0f,
            )
        ParticleKind.NEON_SPARKS ->
            ParticleSpec(
                motion = ParticleMotion.FALL,
                shapes = listOf(ParticleShape.DOT, ParticleShape.STAR),
                colors = listOf(Color(0xFFFF2E97), Color(0xFF00E5FF), Color(0xFFB967FF)),
                minRadius = 1f, maxRadius = 2.5f,
                minSpeed = 0.5f, maxSpeed = 1.3f,
                sway = 0.02f,
            )
        ParticleKind.AUTUMN_LEAVES ->
            ParticleSpec(
                motion = ParticleMotion.FALL,
                shapes = listOf(ParticleShape.OVAL),
                colors = listOf(Color(0xFFE67E22), Color(0xFFD9A441), Color(0xFFA0522D), Color(0xFFC98A3D)),
                minRadius = 2.5f, maxRadius = 5.5f,
                minSpeed = 0.3f, maxSpeed = 0.85f,
                sway = 0.06f,
            )
        ParticleKind.HEARTS ->
            ParticleSpec(
                motion = ParticleMotion.FALL,
                shapes = listOf(ParticleShape.HEART, ParticleShape.STAR),
                colors = listOf(Color(0xFFFF9EBB), Color(0xFFFF6B9D), Color(0xFFFFF1A8), Color(0xFFFFD6E0)),
                minRadius = 2.5f, maxRadius = 4.5f,
                minSpeed = 0.3f, maxSpeed = 0.8f,
                sway = 0.05f,
            )
        ParticleKind.RAIN ->
            ParticleSpec(
                motion = ParticleMotion.FALL,
                shapes = listOf(ParticleShape.LINE),
                colors = listOf(Color(0xFF7FA8C9), Color(0xFFA8C6D8)),
                minRadius = 1.5f, maxRadius = 3f,
                minSpeed = 2.5f, maxSpeed = 4f,
                sway = 0f,
            )
        ParticleKind.LIGHT_DUST ->
            ParticleSpec(
                motion = ParticleMotion.FLOAT,
                shapes = listOf(ParticleShape.DOT),
                colors = listOf(Color(0xFFD9C9A8), Color(0xFFFFFFFF), Color(0xFFC9A227)),
                minRadius = 1f, maxRadius = 2f,
                minSpeed = 0.15f, maxSpeed = 0.45f,
                sway = 0f,
            )
    }

/**
 * CuteNovna: guirnalda de luces navideñas para la parte superior de la pantalla.
 *
 * Un cable que cuelga en ondas suaves con focos rojo, dorado y verde salvia.
 * Solo dibuja; no intercepta toques. Solo se muestra con el tema Navidad.
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
