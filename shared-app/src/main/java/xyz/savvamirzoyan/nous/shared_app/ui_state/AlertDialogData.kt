package xyz.savvamirzoyan.nous.shared_app.ui_state

import xyz.savvamirzoyan.nous.core.Model

class AlertDialogData(
    val title: TextState,
    val message: TextState,
    val onPositiveClickCallback: (() -> Unit)? = null
) : Model.Ui