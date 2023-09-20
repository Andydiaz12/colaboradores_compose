package com.upaep.upaeppersonal.view.base.genericcomponents

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BaseViewWithModal(
    modalContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
    cardHeader: @Composable (() -> Unit)? = null,
    state: ModalBottomSheetState,
    loading: Boolean
) {
    ModalBottomSheetLayout(
        sheetState = state,
        scrimColor = Color.Black.copy(alpha = 0.6f),
        sheetContent = { modalContent() }
    ) {
        BaseView(onRefresh = { }, loading = loading, cardHeader = cardHeader) {
            content()
        }
    }
}