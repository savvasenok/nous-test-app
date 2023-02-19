package xyz.savvamirzoyan.nous.feature_gallery

import android.graphics.drawable.AnimatedVectorDrawable
import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.feature_gallery.databinding.LayoutNoDataBinding
import xyz.savvamirzoyan.nous.shared_app.CoreViewHolder

class NoDataViewHolder(
    binding: LayoutNoDataBinding,
    tryAgainCallback: () -> Unit
) : CoreViewHolder<LayoutNoDataBinding, NoDataUi>(binding) {

    private val animDrawable = binding.tryAgainButton.icon as AnimatedVectorDrawable

    init {
        binding.tryAgainButton.setOnClickListener { tryAgainCallback() }
    }

    override fun bind(item: NoDataUi) {
        binding.tryAgainButton.isEnabled = item.isTryAgainButtonEnabled
        setProgressStatus(item.isLoading)
    }

    override fun <R : Model.Ui> bindPayload(item: NoDataUi, payload: List<R>) {
        payload.forEach {
            when (it) {
                is NoDataProgressPayload -> setProgressStatus(it.isLoading)
            }
        }
    }

    private fun setProgressStatus(isLoading: Boolean) =
        if (isLoading) animDrawable.start()
        else animDrawable.stop()
}