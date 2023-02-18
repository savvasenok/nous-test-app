package xyz.savvamirzoyan.nous.shared_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import xyz.savvamirzoyan.nous.core.Model

interface CoreViewHolderFingerprint<V : ViewBinding, I : Model.Ui> {

    fun isRelativeItem(item: Model.Ui): Boolean

    @LayoutRes
    fun getLayoutRes(): Int

    fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): CoreViewHolder<V, I>
}