package xyz.savvamirzoyan.nous.shared_app

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import xyz.savvamirzoyan.nous.core.Model

abstract class CoreViewHolder<out V : ViewBinding, I : Model.Ui>(
    protected val binding: V
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: I)
}
