package xyz.savvamirzoyan.nous.feature_gallery

import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.shared_app.ui_state.TextState

data class NoDataUi(
    val description: TextState,
    val isTryAgainButtonEnabled: Boolean = true
) : Model.Ui {
    val isLoading: Boolean
        get() = !isTryAgainButtonEnabled
}
