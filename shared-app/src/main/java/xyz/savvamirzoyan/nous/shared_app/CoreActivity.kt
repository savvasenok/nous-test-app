package xyz.savvamirzoyan.nous.shared_app

import androidx.appcompat.app.AppCompatActivity

/**
 * In bigger projects some logic may be available to fragments, because all Activities
 * have to derive from this abstract class. Reserved for future use
 */
abstract class CoreActivity : AppCompatActivity() {

    // counts how many time loader was requested
    private var loadersCounter: Int = 0
        set(value) {

            field = if (value < 0) 0 else value

            if (field == 0) loading(false)
            else loading(true)
        }

    protected abstract fun loading(state: Boolean)

    fun startLoading() {
        loadersCounter += 1
    }

    fun stopLoading() {
        loadersCounter -= 1
    }
}