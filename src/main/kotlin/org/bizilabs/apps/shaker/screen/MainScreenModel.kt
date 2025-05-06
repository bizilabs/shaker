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

    data class UpdateOffset(val offset: Int) : MainScreenAction

    data class UpdateCount(val count: String) : MainScreenAction

    data class UpdateOption(val option: DelayOption) : MainScreenAction
}

enum class DelayOption {
    Second,
    Minute,
}

data class MainScreenState(
    val active: Boolean = false,
    val offset: Int = 0,
    val count: String = "",
    val option: DelayOption = DelayOption.Second,
    val options: List<DelayOption> = DelayOption.entries,
) {
    val delay: Long
        get() =
            when (option) {
                DelayOption.Second -> secondsToMillis(count.toLongOrNull() ?: 30L)
                DelayOption.Minute -> minutesToMillis(count.toLongOrNull() ?: 3)
            }
}

class MainScreenModel(
    private val executor: MouseCommandExecutor = MouseCommandExecutor(),
) : StateScreenModel<MainScreenState>(MainScreenState()) {
    fun onAction(action: MainScreenAction) {
        when (action) {
            is MainScreenAction.ToggleActive -> toggleActive()
            is MainScreenAction.UpdateOffset -> updateOffset(action.offset)
            is MainScreenAction.UpdateCount -> updateCount(count = action.count)
            is MainScreenAction.UpdateOption -> updateOption(option = action.option)
        }
    }

    private fun updateCount(count: String) {
        mutableState.update { it.copy(count = count) }
    }

    private fun updateOption(option: DelayOption) {
        mutableState.update { it.copy(option = option) }
    }

    private fun updateOffset(offset: Int) {
        mutableState.update { it.copy(offset = offset) }
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

private fun secondsToMillis(seconds: Long): Long {
    return seconds * 1000
}

private fun minutesToMillis(minutes: Long): Long {
    return minutes * 60 * 1000
}
