package com.upaep.upaeppersonal.view.base.genericcomponents

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.model.entities.base.ErrorInfo
import com.upaep.upaeppersonal.view.base.theme.Yellow_Schedule

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BaseViewWithModal(
    screenName: String = "",
    state: ModalBottomSheetState,
    upperCardText: String? = null,
    content: @Composable () -> Unit,
    multipleElements: Boolean = false,
    modalContent: @Composable () -> Unit,
    transparentBackground: Boolean = false,
    cardPickerColor: Color = Yellow_Schedule,
    onUpperCardClick: () -> Unit = {},
    upperCardContent: (@Composable () -> Unit)? = null,
    error: ErrorInfo? = null,
    onCloseDialog: () -> Unit = {},
    noData: Boolean = false,
    loadingScreen: Boolean = false,
    navigation: NavHostController? = null,
    loading: Boolean
) {
    ModalBottomSheetLayout(
        sheetState = state,
        scrimColor = Color.Black.copy(alpha = 0.6f),
        sheetContent = { modalContent() }
    ) {
        BaseView(
            onRefresh = { },
            loading = loading,
            transparentBackground = transparentBackground,
            screenName = screenName,
            upperCardText = upperCardText,
            onUpperCardClick = onUpperCardClick,
            multipleElements = multipleElements,
            cardPickerColor = cardPickerColor,
            upperCardContent = upperCardContent,
            error = error,
            navigation = navigation,
            noData = noData,
            loadingScreen = loadingScreen,
            onCloseDialog = onCloseDialog
        ) { content() }
    }
}