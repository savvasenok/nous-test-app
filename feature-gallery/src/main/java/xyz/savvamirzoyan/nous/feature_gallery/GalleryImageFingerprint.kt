package xyz.savvamirzoyan.nous.feature_gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.feature_gallery.databinding.LayoutImageBinding
import xyz.savvamirzoyan.nous.shared_app.CoreViewHolder
import xyz.savvamirzoyan.nous.shared_app.CoreViewHolderFingerprint

class GalleryImageFingerprint(
    private val onClick: (index: Int) -> Unit
) : CoreViewHolderFingerprint<LayoutImageBinding, GalleryImageUi> {

    override fun isRelativeItem(item: Model.Ui): Boolean = item is GalleryImageUi

    override fun getLayoutRes(): Int = R.layout.layout_image

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): CoreViewHolder<LayoutImageBinding, GalleryImageUi> {
        val binding = LayoutImageBinding.inflate(layoutInflater, parent, false)
        return GalleryImageViewHolder(binding, onClick)
    }
}