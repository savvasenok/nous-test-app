package xyz.savvamirzoyan.nous.feature_gallery

import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.core.PictureUrl
import xyz.savvamirzoyan.nous.shared_app.ui_state.TextState

class SearchResultImageUi(
    val pictureUrl: PictureUrl,
    val title: TextState,
    val description: TextState,
    // TODO: spannable for selection
) : Model.Ui