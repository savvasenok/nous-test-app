package xyz.savvamirzoyan.nous.shared_app

import androidx.recyclerview.widget.DiffUtil
import xyz.savvamirzoyan.nous.core.Model

interface CoreDiffUtilsGetter<T : Model.Ui> {
    fun get(old: List<T>, new: List<T>): DiffUtil.Callback
}