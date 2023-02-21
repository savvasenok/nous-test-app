package xyz.savvamirzoyan.nous.feature_details

import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.core.PictureUrl
import xyz.savvamirzoyan.nous.shared_app.ui_state.TextState

data class NewsItemDetailsUi(
    val pictureUrl: PictureUrl,
    val title: TextState,
    val description: TextState
) : Model.Ui