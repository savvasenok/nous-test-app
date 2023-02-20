package xyz.savvamirzoyan.nous.feature_gallery.search

import xyz.savvamirzoyan.nous.core.ID
import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.core.PictureUrl
import xyz.savvamirzoyan.nous.shared_app.ui_state.TextState

data class SearchResultImageUi(
    val id: ID,
    val pictureUrl: PictureUrl,
    val title: TextState,
    val description: TextState,
    val titleSearchResultIndexStart: Int,
    val titleSearchResultIndexEnd: Int,
    val descriptionSearchResultIndexStart: Int,
    val descriptionSearchResultIndexEnd: Int,
) : Model.Ui