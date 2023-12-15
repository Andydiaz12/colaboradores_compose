package com.upaep.upaeppersonal.view.features.menu

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.BuildConfig
import com.upaep.upaeppersonal.viewmodel.features.menu.PrivacyPolicyViewModel

@Preview(showSystemUi = true)
@Composable
fun PrivacyScreen(
    navigation: NavHostController? = null,
    privacyPolicyViewModel: PrivacyPolicyViewModel = hiltViewModel()
) {
    Dialog(onDismissRequest = {  }) {
        Text(text = "Hola!")
    }
}