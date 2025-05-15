package org.bizilabs.apps.shaker.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.bizilabs.apps.shaker.screen.MainScreenAction
import org.bizilabs.apps.shaker.screen.MainScreenState
import org.bizilabs.apps.shaker.theme.ShakerTheme

@Composable
fun ActionControl(state: MainScreenState, onAction: (MainScreenAction) -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { onAction(MainScreenAction.ToggleActive) },
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (!state.active) ShakerTheme.colorScheme.info else ShakerTheme.colorScheme.error,
            contentColor = if (!state.active) ShakerTheme.colorScheme.onInfo else ShakerTheme.colorScheme.onError,
        ),
    ) {
        Text(text = if (state.active) "Stop" else "Start")
    }
}