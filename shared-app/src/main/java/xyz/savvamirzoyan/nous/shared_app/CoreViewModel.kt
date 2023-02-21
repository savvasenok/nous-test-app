package xyz.savvamirzoyan.nous.shared_app

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDeepLinkRequest
import kotlinx.coroutines.flow.*

abstract class CoreViewModel : ViewModel() {

    private val _loadingFlow = MutableStateFlow<Boolean?>(null)
    val loadingFlow: Flow<Boolean> = _loadingFlow.filterNotNull()

    private val _navigationIntentFlow = MutableSharedFlow<Intent>(replay = 0)
    val navigationIntentFlow: Flow<Intent> = _navigationIntentFlow

    private val _navigationDeeplinkFlow = MutableSharedFlow<String>(replay = 0)
    val navigationFromDeepLinkFlow: Flow<NavDeepLinkRequest> = _navigationDeeplinkFlow
        .map { Uri.parse(it) }
        .map { NavDeepLinkRequest.Builder.fromUri(it).build() }

    protected suspend fun whileLoading(function: suspend () -> Unit) {
        _loadingFlow.emit(true)
        function()
        _loadingFlow.emit(false)
    }

    protected suspend fun navigate(deeplink: String) = _navigationDeeplinkFlow.emit(deeplink)
    protected suspend fun navigate(intent: Intent) = _navigationIntentFlow.emit(intent)
}