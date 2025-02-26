package com.logotet.bookapp.core.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import bookapp.composeapp.generated.resources.Res
import bookapp.composeapp.generated.resources.action_cancel
import bookapp.composeapp.generated.resources.action_retry
import com.logotet.bookapp.core.presentation.theme.Dimensions
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppSnackbar(
    snackbarHostState: SnackbarHostState,
    withTwoButtons: Boolean = false
) {
    val scope = rememberCoroutineScope()

    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            CustomSnackbar(
                snackbarData = data,
                withTwoButtons = withTwoButtons,
                onConfirm = {
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.performAction()
                    }
                },
                onCancel = {
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                    }
                }
            )
        }
    )
}

@Composable
private fun CustomSnackbar(
    snackbarData: SnackbarData,
    cancelButtonTextId: StringResource = Res.string.action_cancel,
    actionButtonTextId: StringResource = Res.string.action_retry,
    withTwoButtons: Boolean = false,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .padding(Dimensions.Spacing.large),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.medium)
        ) {
            Text(
                text = snackbarData.visuals.message,
                style = MaterialTheme.typography.bodyLarge
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onCancel) {
                    val cancelButtonUpperCaseString =
                        stringResource(cancelButtonTextId).uppercase()

                    Text(cancelButtonUpperCaseString)
                }

                if (withTwoButtons) {
                    Spacer(
                        modifier = Modifier
                            .width(Dimensions.Spacing.medium)
                    )

                    TextButton(onClick = onConfirm) {
                        val confirmButtonText =
                            snackbarData.visuals.actionLabel
                                ?: stringResource(actionButtonTextId)

                        Text(confirmButtonText)
                    }
                }
            }
        }
    }
}