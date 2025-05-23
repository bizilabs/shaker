package org.bizilabs.apps.shaker.screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.Url
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import org.bizilabs.apps.shaker.theme.ShakerTheme

class MainScreen(
    val close: () -> Unit,
    val hide: () -> Unit,
) : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<MainScreenModel> { MainScreenModel() }
        val state by screenModel.state.collectAsState()
        MainScreenContent(
            state = state,
            onAction = screenModel::onAction,
            closeApplication = close,
            hideApplication = hide,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreenContent(
    state: MainScreenState,
    onAction: (MainScreenAction) -> Unit,
    closeApplication: () -> Unit,
    hideApplication: () -> Unit,
) {
    val animFromUrl by rememberLottieComposition {
        LottieCompositionSpec.Url("https://lottie.host/eee894d5-34a7-411e-9575-10d0b1bbdc95/C4BlHWYTPq.lottie")
    }

    var scrollOffset by remember { mutableStateOf(0f) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = closeApplication) {
                    Icon(Icons.Filled.Close, "close")
                }
                Column {
                    Text(text = "Shaker", style = MaterialTheme.typography.titleSmall)
                }
                IconButton(onClick = hideApplication) {
                    Icon(Icons.Filled.Minimize, "minimise")
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
        ) {
            Column(
                modifier =
                    Modifier.fillMaxWidth()
                        .weight(1f),
            ) {
                Card(
                    modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 16.dp).padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(),
                    shape = RoundedCornerShape(10),
                ) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .pointerInput(Unit) {
                                    awaitPointerEventScope {
                                        while (true) {
                                            val event = this.awaitPointerEvent()
                                            if (event.type == PointerEventType.Scroll) {
                                                val deltaY = event.changes.first().scrollDelta.y
                                                scrollOffset += deltaY
                                            }
                                        }
                                    }
                                },
                    ) {
                        Text(text = "", modifier = Modifier.align(Alignment.Center))
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    TextField(
                        enabled = state.active.not(),
                        modifier = Modifier.height(48.dp).weight(1f),
                        value = state.count,
                        onValueChange = { count ->
                            if (count == "" || count.toIntOrNull() != null) onAction(MainScreenAction.UpdateCount(count))
                        },
                        label = {
                            Text(
                                text =
                                    when (state.option) {
                                        DelayOption.Second -> "Second/s"
                                        DelayOption.Minute -> "Minute/s"
                                    },
                            )
                        },
                        trailingIcon = {
                            Card(
                                enabled = state.active.not(),
                                onClick = {
                                    val option =
                                        when (state.option) {
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
                                        text =
                                            when (state.option) {
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
            Button(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                onClick = { onAction(MainScreenAction.ToggleActive) },
                shape = MaterialTheme.shapes.medium,
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = if (!state.active) ShakerTheme.colorScheme.info else ShakerTheme.colorScheme.error,
                        contentColor = if (!state.active) ShakerTheme.colorScheme.onInfo else ShakerTheme.colorScheme.onError,
                    ),
            ) {
                Text(text = if (state.active) "Stop" else "Start")
            }
        }
    }
}
