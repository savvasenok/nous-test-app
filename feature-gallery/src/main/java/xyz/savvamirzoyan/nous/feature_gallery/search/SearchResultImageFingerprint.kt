package xyz.savvamirzoyan.nous.feature_gallery.search

import android.view.LayoutInflater
import android.view.ViewGroup
import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.feature_gallery.R
import xyz.savvamirzoyan.nous.feature_gallery.databinding.LayoutSearchResultImageBinding
import xyz.savvamirzoyan.nous.shared_app.CoreViewHolder
import xyz.savvamirzoyan.nous.shared_app.CoreViewHolderFingerprint

class SearchResultImageFingerprint(
    private val onClick: (index: Int) -> Unit
) : CoreViewHolderFingerprint<LayoutSearchResultImageBinding, SearchResultImageUi> {

    override fun isRelativeItem(item: Model.Ui): Boolean = item is SearchResultImageUi

    override fun getLayoutRes(): Int = R.layout.layout_search_result_image

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): CoreViewHolder<LayoutSearchResultImageBinding, SearchResultImageUi> {
        val binding = LayoutSearchResultImageBinding.inflate(layoutInflater, parent, false)
        return SearchResultImageViewHolder(binding, onClick)
    }
}