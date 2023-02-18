package xyz.savvamirzoyan.nous.feature_gallery

import xyz.savvamirzoyan.nous.feature_gallery.databinding.LayoutSearchResultImageBinding
import xyz.savvamirzoyan.nous.shared_app.CoreViewHolder
import xyz.savvamirzoyan.nous.shared_app.load
import xyz.savvamirzoyan.nous.shared_app.setText

class SearchResultImageViewHolder(
    binding: LayoutSearchResultImageBinding,
    onClick: (Int) -> Unit
) : CoreViewHolder<LayoutSearchResultImageBinding, SearchResultImageUi>(binding) {

    init {
        binding.root.setOnClickListener { onClick(adapterPosition) }
    }

    override fun bind(item: SearchResultImageUi) {
        binding.ivPicture.load(item.pictureUrl)
        binding.tvTitle.setText(item.title)
        binding.tvDescription.setText(item.description)
    }
}