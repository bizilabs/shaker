package dev.mambo.play.shaker.screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import dev.mambo.play.shaker.theme.ShakerTheme
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.Url
import io.github.alexzhirkevich.compottie.rememberLottieComposition

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
    hideApplication: () -> Unit
) {
    val animFromUrl by rememberLottieComposition {
        LottieCompositionSpec.Url("https://lottie.host/eee894d5-34a7-411e-9575-10d0b1bbdc95/C4BlHWYTPq.lottie")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = closeApplication) {
                        Icon(Icons.Filled.Close, "close")
                    }
                },
                actions = {
                    IconButton(onClick = hideApplication) {
                        Icon(Icons.Filled.Minimize, "minimise")
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().weight(1f),
            ) {
                Column(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = "Shaker")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Card(
                        onClick = { onAction(MainScreenAction.DecreaseDelay) },
                        enabled = !(state.active) && (state.delay > 500),
                    ) {
                        Box(
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = "-",
                            )
                        }
                    }
                    Card(modifier = Modifier.weight(1f)) {
                        Box(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                            Text(
                                text = state.delay.toString(),
                                modifier = Modifier.align(Alignment.Center),
                            )
                        }
                    }
                    Card(
                        onClick = { onAction(MainScreenAction.IncreaseDelay) },
                        enabled = !(state.active),
                    ) {
                        Box(
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = "+",
                            )
                        }
                    }
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
