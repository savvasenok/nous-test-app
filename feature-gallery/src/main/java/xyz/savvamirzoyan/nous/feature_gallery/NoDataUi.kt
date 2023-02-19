package xyz.savvamirzoyan.nous.feature_gallery

import xyz.savvamirzoyan.nous.core.Model

data class NoDataUi(
    val isTryAgainButtonEnabled: Boolean
) : Model.Ui {
    val isLoading: Boolean
        get() = !isTryAgainButtonEnabled
}
