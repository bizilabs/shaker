package org.bizilabs.apps.shaker.screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.Url
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import org.bizilabs.apps.shaker.components.ActionControl
import org.bizilabs.apps.shaker.components.CursorField
import org.bizilabs.apps.shaker.components.DurationControl

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
                CursorField(
                    modifier = Modifier.weight(1f),
                    onScroll = { deltaY -> scrollOffset += deltaY }
                )
                DurationControl(state, onAction)
            }
            ActionControl(state, onAction)
        }
    }
}

