package xyz.savvamirzoyan.nous.feature_gallery.search

import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import com.google.android.material.color.MaterialColors
import xyz.savvamirzoyan.nous.core.Model
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

        // TODO: shift contents of textview, so highlighted text is somewhere in the center

        buildSpannableString(
            item.title.getString(binding.root.context),
            item.titleSearchResultIndexStart,
            item.titleSearchResultIndexEnd
        ).also { binding.tvTitle.text = it }

        buildSpannableString(
            item.description.getString(binding.root.context),
            item.descriptionSearchResultIndexStart,
            item.descriptionSearchResultIndexEnd
        ).also { binding.tvDescription.text = it }
    }

    override fun <R : Model.Ui> bindPayload(item: SearchResultImageUi, payload: List<R>) {
        payload.forEach { p ->
            if (p is SearchResultImagePayload) {
                buildSpannableString(
                    item.title.getString(binding.root.context),
                    item.titleSearchResultIndexStart,
                    item.titleSearchResultIndexEnd
                ).also { binding.tvTitle.text = it }

                buildSpannableString(
                    item.description.getString(binding.root.context),
                    item.descriptionSearchResultIndexStart,
                    item.descriptionSearchResultIndexEnd
                ).also { binding.tvDescription.text = it }
            }
        }
    }

    private fun buildSpannableString(string: String, start: Int, end: Int) = SpannableString(string).apply {
        setSpan(BackgroundColorSpan(color), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    }
}