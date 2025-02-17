package dev.davidgaspar.getthis.ui.components.utils

sealed class ButtonState {
    object Clicked : ButtonState()
    object Loading : ButtonState()
    object Completed : ButtonState()
}