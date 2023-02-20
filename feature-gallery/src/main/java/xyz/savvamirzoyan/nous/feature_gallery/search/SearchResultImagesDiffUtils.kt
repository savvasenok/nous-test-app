package xyz.savvamirzoyan.nous.feature_gallery.search

import androidx.recyclerview.widget.DiffUtil
import xyz.savvamirzoyan.nous.shared_app.CoreDiffUtilsGetter

class SearchResultImagesDiffUtils : CoreDiffUtilsGetter<SearchResultImageUi> {
    override fun get(old: List<SearchResultImageUi>, new: List<SearchResultImageUi>): DiffUtil.Callback =
        object : DiffUtil.Callback() {
            override fun getOldListSize() = old.size
            override fun getNewListSize() = new.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition].id == new[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition] == new[newItemPosition]

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                val oldModel = old[oldItemPosition]
                val newModel = new[newItemPosition]

                return if (oldModel.titleSearchResultIndexStart != newModel.titleSearchResultIndexStart
                    || oldModel.titleSearchResultIndexEnd != newModel.titleSearchResultIndexEnd
                    || oldModel.descriptionSearchResultIndexStart != newModel.descriptionSearchResultIndexStart
                    || oldModel.descriptionSearchResultIndexEnd != newModel.descriptionSearchResultIndexEnd
                ) SearchResultImagePayload() else null
            }
        }
}