package com.maxrave.simpmusic.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * CuteNovna v1.0.3: el pie de página ya no muestra el crédito ("@año CuteNovna
 * versión / maxrave-dev"). Solo queda el espacio, para no mover el layout de
 * las pantallas que lo usan. El parámetro se conserva para no tocar todos los
 * sitios que lo llaman.
 */
@Composable
fun EndOfPage(withoutCredit: Boolean = false) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(280.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        // Sin texto: ningún crédito visible en la app.
    }
}
