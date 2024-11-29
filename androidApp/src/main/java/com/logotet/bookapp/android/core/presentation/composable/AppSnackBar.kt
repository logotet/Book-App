package com.logotet.bookapp.android.core.presentation.composable

import androidx.annotation.StringRes
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
import androidx.compose.ui.res.stringResource
import com.logotet.bookapp.android.R
import com.logotet.bookapp.android.core.presentation.theme.Dimensions
import kotlinx.coroutines.launch

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
    @StringRes cancelButtonTextId: Int = R.string.action_cancel,
    @StringRes actionButtonTextId: Int = R.string.action_retry,
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
                        stringResource(id = cancelButtonTextId).uppercase()

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
                                ?: stringResource(id = actionButtonTextId)

                        Text(confirmButtonText)
                    }
                }
            }
        }
    }
}