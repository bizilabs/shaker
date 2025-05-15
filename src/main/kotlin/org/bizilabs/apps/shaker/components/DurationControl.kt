package org.bizilabs.apps.shaker.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.bizilabs.apps.shaker.screen.*

@Composable
fun DurationControl(state: MainScreenState, onAction: (MainScreenAction) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TextField(
            enabled = state.active.not(),
            modifier = Modifier
                .height(48.dp)
                .weight(1f),
            value = state.count,
            onValueChange = { count ->
                if (count == "" || count.toIntOrNull() != null) {
                    onAction(MainScreenAction.UpdateCount(count))
                }
            },
            label = {
                Text(
                    text = when (state.option) {
                        DelayOption.Second -> "Second/s"
                        DelayOption.Minute -> "Minute/s"
                    },
                )
            },
            trailingIcon = {
                Card(
                    enabled = state.active.not(),
                    onClick = {
                        val option = when (state.option) {
                            DelayOption.Second -> DelayOption.Minute
                            else -> DelayOption.Second
                        }
                        onAction(MainScreenAction.UpdateOption(option))
                    },
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = when (state.option) {
                                DelayOption.Second -> "Sec"
                                DelayOption.Minute -> "Min"
                            },
                        )
                        Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Min")
                    }
                }
            },
        )
    }
}
