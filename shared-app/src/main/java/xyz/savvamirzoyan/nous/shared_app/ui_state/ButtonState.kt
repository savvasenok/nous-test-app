package xyz.savvamirzoyan.nous.shared_app.ui_state

data class ButtonState(
    val text: TextState,
    val isEnabled: Boolean = true,
    val isVisible: Boolean = true
)