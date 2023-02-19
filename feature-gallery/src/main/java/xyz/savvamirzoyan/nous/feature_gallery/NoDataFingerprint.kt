package xyz.savvamirzoyan.nous.feature_gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.feature_gallery.databinding.LayoutNoDataBinding
import xyz.savvamirzoyan.nous.shared_app.CoreViewHolder
import xyz.savvamirzoyan.nous.shared_app.CoreViewHolderFingerprint

class NoDataFingerprint(
    private val tryAgainCallback: () -> Unit
) : CoreViewHolderFingerprint<LayoutNoDataBinding, NoDataUi> {

    override fun isRelativeItem(item: Model.Ui): Boolean = item is NoDataUi

    override fun getLayoutRes(): Int = R.layout.layout_no_data
    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): CoreViewHolder<LayoutNoDataBinding, NoDataUi> {
        val binding = LayoutNoDataBinding.inflate(layoutInflater, parent, false)
        return NoDataViewHolder(binding, tryAgainCallback)
    }
}