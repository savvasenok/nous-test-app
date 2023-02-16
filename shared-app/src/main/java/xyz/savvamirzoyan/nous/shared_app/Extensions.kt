package xyz.savvamirzoyan.nous.shared_app

import android.widget.ImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import xyz.savvamirzoyan.nous.core.PictureUrl
import xyz.savvamirzoyan.nous.shared_app.ui_state.ButtonState
import xyz.savvamirzoyan.nous.shared_app.ui_state.TextState

fun ImageView.load(pictureUrl: PictureUrl?) = Glide.with(this.context)
    .load(pictureUrl)
    .transition(DrawableTransitionOptions.withCrossFade())
    .dontAnimate()
    .placeholder(R.drawable.ic_image)
    .error(R.drawable.ic_broken_image)
    .diskCacheStrategy(DiskCacheStrategy.DATA)
    .into(this)

fun MaterialTextView.setText(textValue: TextState?) {
    if (textValue != null) {
        val value = textValue.getString(this.context)
        isVisible = value.isNotBlank()
        text = value
    } else isVisible = false
}

fun MaterialButton.setState(state: ButtonState) {
    text = state.text.getString(context)
    isEnabled = state.isEnabled
    isVisible = state.isVisible
}

fun CollapsingToolbarLayout.setTitle(textValue: TextState) {
    title = textValue.getString(this.context)
}