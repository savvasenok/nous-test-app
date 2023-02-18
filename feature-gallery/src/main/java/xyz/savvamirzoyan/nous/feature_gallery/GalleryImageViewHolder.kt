package xyz.savvamirzoyan.nous.feature_gallery

import xyz.savvamirzoyan.nous.feature_gallery.databinding.LayoutImageBinding
import xyz.savvamirzoyan.nous.shared_app.CoreViewHolder
import xyz.savvamirzoyan.nous.shared_app.load

class GalleryImageViewHolder(
    binding: LayoutImageBinding,
    onClick: (index: Int) -> Unit
) : CoreViewHolder<LayoutImageBinding, GalleryImageUi>(binding) {

    init {
        binding.root.setOnClickListener {
            onClick(adapterPosition)
        }
    }

    override fun bind(item: GalleryImageUi) {
        binding.ivPicture.load(item.pictureUrl)
    }
}