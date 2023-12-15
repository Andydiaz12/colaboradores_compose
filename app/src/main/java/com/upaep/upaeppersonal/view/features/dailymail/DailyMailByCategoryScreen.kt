package com.upaep.upaeppersonal.view.features.dailymail

import androidx.compose.foundation.layout.Row
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseViewWithModal
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import com.upaep.upaeppersonal.viewmodel.features.dailymail.DailyMailByCategoryViewModel

@OptIn(ExperimentalMaterialApi::class)
@Preview(showSystemUi = true)
@Composable
fun DailyMailByCategoryScreen(
    navigation: NavHostController? = null,
    dailyMailByCategoryViewModel: DailyMailByCategoryViewModel = hiltViewModel()
) {
    val error by dailyMailByCategoryViewModel.error.observeAsState()
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    BaseViewWithModal(
        state = state,
        content = { /*TODO*/ },
        modalContent = { /*TODO*/ },
        loading = false,
        error = error,
        onCloseDialog = { dailyMailByCategoryViewModel.closeDialog() }
    )
}

@Preview(showSystemUi = true)
@Composable
fun SingleElement() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "UPAEP anuncia la creación de la Dirección de Evaluación y Gestión de la Calidad",
            modifier = Modifier.weight(1f),
            fontFamily = roboto_regular,
            fontSize = 14.sp
        )
        Icon(imageVector = Icons.Default.ArrowRight, contentDescription = null)
    }
}