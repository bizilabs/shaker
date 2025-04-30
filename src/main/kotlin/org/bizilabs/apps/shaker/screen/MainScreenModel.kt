package org.bizilabs.apps.shaker.screen

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bizilabs.apps.shaker.utils.MouseCommand
import org.bizilabs.apps.shaker.utils.MouseCommandExecutor
import kotlin.random.Random

sealed interface MainScreenAction {
    object ToggleActive : MainScreenAction

    object IncreaseDelay : MainScreenAction

    object DecreaseDelay : MainScreenAction

    data class UpdateOffset(val offset: Int) : MainScreenAction
}

data class MainScreenState(
    val active: Boolean = false,
    val delay: Long = 500,
    val offset: Int = 0,
)

class MainScreenModel(
    private val executor: MouseCommandExecutor = MouseCommandExecutor(),
) : StateScreenModel<MainScreenState>(MainScreenState()) {
    fun onAction(action: MainScreenAction) {
        when (action) {
            is MainScreenAction.ToggleActive -> toggleActive()
            MainScreenAction.DecreaseDelay -> updateDelay(shouldDecreaseDelay = true)
            MainScreenAction.IncreaseDelay -> updateDelay(shouldDecreaseDelay = false)
            is MainScreenAction.UpdateOffset -> updateOffset(action.offset)
        }
    }

    private fun updateOffset(offset: Int) {
        mutableState.update { it.copy(offset = offset) }
    }

    private fun updateDelay(shouldDecreaseDelay: Boolean) {
        when (shouldDecreaseDelay) {
            true -> mutableState.update { it.copy(delay = it.delay - 500) }
            false -> mutableState.update { it.copy(delay = it.delay + 500) }
        }
    }

    private fun toggleActive() {
        mutableState.update { it.copy(active = it.active.not()) }
        if (state.value.active) startMovingMouseWork()
    }

    private var moveJob: Job? = null

    private fun startMovingMouseWork() {
        if (state.value.active.not()) {
            moveJob?.cancel()
            return
        }
        moveJob =
            screenModelScope.launch {
                val x = Random.nextInt(-100, 100).toFloat()
                val y = Random.nextInt(-100, 100).toFloat()
                executor.invoke(MouseCommand.MoveByDelta(x, y))
                delay(state.value.delay)
                startMovingMouseWork()
            }
    }
}
