package xyz.savvamirzoyan.nous.shared_app

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDeepLinkRequest
import kotlinx.coroutines.flow.*
import xyz.savvamirzoyan.nous.core.ErrorEntity
import xyz.savvamirzoyan.nous.core.ResultWrap
import xyz.savvamirzoyan.nous.shared_app.ui_state.AlertDialogData
import xyz.savvamirzoyan.nous.shared_app.ui_state.TextState

abstract class CoreViewModel : ViewModel() {

    private val _loadingFlow = MutableStateFlow<Boolean?>(null)
    val loadingFlow: Flow<Boolean> = _loadingFlow.filterNotNull()

    private val _navigationIntentFlow = MutableSharedFlow<Intent>(replay = 0)
    val navigationIntentFlow: Flow<Intent> = _navigationIntentFlow

    private val _navigationDeeplinkFlow = MutableSharedFlow<String>(replay = 0)
    val navigationFromDeepLinkFlow: Flow<NavDeepLinkRequest> = _navigationDeeplinkFlow
        .map { Uri.parse(it) }
        .map { NavDeepLinkRequest.Builder.fromUri(it).build() }

    private val _alertDataFlow = MutableSharedFlow<AlertDialogData>(replay = 0)
    val alertDataFlow: Flow<AlertDialogData> = _alertDataFlow

    private val _closeFragmentFlow = MutableSharedFlow<Unit>(replay = 0)
    val closeFragmentFlow: Flow<Unit> = _closeFragmentFlow

    protected suspend fun <T> ResultWrap<T>.handleError(onPositiveClick: (() -> Unit)? = null): ResultWrap<T> = this
        .also { result ->
            when ((result as? ResultWrap.Failure)?.error) {
                ErrorEntity.NoConnection -> showNoConnectionAlert(onPositiveClick)
                ErrorEntity.NoData -> showNoDataAlert(onPositiveClick)
                ErrorEntity.ServerError -> showServerErrorAlert(onPositiveClick)
                ErrorEntity.Unknown -> showUnknownErrorAlert(onPositiveClick)
                else -> {}
            }
        }

    protected suspend fun whileLoading(function: suspend () -> Unit) {
        _loadingFlow.emit(true)
        function()
        _loadingFlow.emit(false)
    }

    protected suspend fun navigate(deeplink: String) = _navigationDeeplinkFlow.emit(deeplink)
    protected suspend fun navigate(intent: Intent) = _navigationIntentFlow.emit(intent)

    protected suspend fun showAlert(title: TextState, description: TextState, onPositiveClick: (() -> Unit)? = null) =
        _alertDataFlow.emit(AlertDialogData(title, description, onPositiveClick))

    protected suspend fun showNoConnectionAlert(onPositiveClick: (() -> Unit)? = null) = showAlert(
        TextState(R.string.alert_title_no_connection), TextState(R.string.alert_message_no_connection), onPositiveClick
    )

    protected suspend fun showNoDataAlert(onPositiveClick: (() -> Unit)? = null) = showAlert(
        TextState(R.string.alert_title_no_data), TextState(R.string.alert_message_no_data), onPositiveClick
    )

    protected suspend fun showServerErrorAlert(onPositiveClick: (() -> Unit)? = null) = showAlert(
        TextState(R.string.alert_title_server_error), TextState(R.string.alert_message_server_error), onPositiveClick
    )

    protected suspend fun showUnknownErrorAlert(onPositiveClick: (() -> Unit)? = null) = showAlert(
        TextState(R.string.alert_title_unknown_error), TextState(R.string.alert_message_unknown_error), onPositiveClick
    )

    protected suspend fun closeFragment() = _closeFragmentFlow.emit(Unit)
}