package xyz.savvamirzoyan.nous.feature_gallery.search

import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import com.google.android.material.color.MaterialColors
import xyz.savvamirzoyan.nous.feature_gallery.databinding.LayoutSearchResultImageBinding
import xyz.savvamirzoyan.nous.shared_app.CoreViewHolder
import xyz.savvamirzoyan.nous.shared_app.load


class SearchResultImageViewHolder(
    binding: LayoutSearchResultImageBinding,
    onClick: (Int) -> Unit
) : CoreViewHolder<LayoutSearchResultImageBinding, SearchResultImageUi>(binding) {

    private val color by lazy { MaterialColors.getColor(binding.root, android.R.attr.textColorTertiaryInverse) }

    init {
        binding.root.setOnClickListener { onClick(adapterPosition) }
    }

    override fun bind(item: SearchResultImageUi) {
        binding.ivPicture.load(item.pictureUrl)

        SpannableString(item.title.getString(binding.root.context))
            .also {
                it.setSpan(
                    BackgroundColorSpan(color),
                    item.titleSearchResultIndexStart,
                    item.titleSearchResultIndexEnd,
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE
                )
            }
            .also { binding.tvTitle.text = it }

        SpannableString(item.description.getString(binding.root.context))
            .also {
                it.setSpan(
                    BackgroundColorSpan(color),
                    item.descriptionSearchResultIndexStart,
                    item.descriptionSearchResultIndexEnd,
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE
                )
            }.also { binding.tvDescription.text = it }
    }
}