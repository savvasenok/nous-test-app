package xyz.savvamirzoyan.nous.feature_gallery.gallery

import androidx.recyclerview.widget.DiffUtil
import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.shared_app.CoreDiffUtilsGetter

class GalleryImagesDiffUtils : CoreDiffUtilsGetter<Model.Ui> {
    override fun get(old: List<Model.Ui>, new: List<Model.Ui>): DiffUtil.Callback = object : DiffUtil.Callback() {
        override fun getOldListSize() = old.size
        override fun getNewListSize() = new.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

            val oldModel = old[oldItemPosition]
            val newModel = new[newItemPosition]

            return when {
                oldModel is GalleryImageUi && newModel is GalleryImageUi -> oldModel.pictureUrl == newModel.pictureUrl
                else -> false
            }
        }

        // currently contents dont change
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true
    }
}