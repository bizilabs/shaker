package dev.mambo.play.shaker.utils

import java.awt.MouseInfo
import java.awt.Robot
import java.awt.Toolkit
import java.awt.event.InputEvent

sealed interface MouseCommand {
    data class MoveByDelta(val deltaX: Float, val deltaY: Float) : MouseCommand

    data class MoveByYawPitch(val yaw: Float, val pitch: Float) : MouseCommand

    data object LeftClick : MouseCommand

    data object RightClick : MouseCommand
}

class MouseCommandExecutor(private val robot: Robot = Robot()) {
    operator fun invoke(command: MouseCommand) {
        when (command) {
            MouseCommand.LeftClick -> {
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
            }

            is MouseCommand.MoveByDelta -> {
                moveMouseByDelta(command.deltaX, command.deltaY)
            }

            is MouseCommand.MoveByYawPitch -> {
                moveMouseByYawPitch(command.yaw, command.pitch)
            }

            MouseCommand.RightClick -> {
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK)
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK)
            }
        }
    }

    private fun moveMouseByYawPitch(
        normalizedYaw: Float,
        normalizedPitch: Float,
    ) {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        // Map normalized yaw and pitch to screen coordinates
        val targetX = ((normalizedYaw + 1) / 2 * screenSize.width).toInt()
        val targetY = ((normalizedPitch + 1) / 2 * screenSize.height).toInt()

        val constrainedX = targetX.coerceIn(0, screenSize.width - 1)
        val constrainedY = targetY.coerceIn(0, screenSize.height - 1)

        robot.mouseMove(constrainedX, constrainedY)
    }

    private fun moveMouseByDelta(
        deltaX: Float,
        deltaY: Float,
    ) {
        val mousePointer = MouseInfo.getPointerInfo().location

        // Calculate new position
        val newX = mousePointer.x + deltaX.toInt()
        val newY = mousePointer.y + deltaY.toInt()

        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val constrainedX = newX.coerceIn(0, screenSize.width - 1)
        val constrainedY = newY.coerceIn(0, screenSize.height - 1)
        robot.mouseMove(constrainedX, constrainedY)
    }
}
