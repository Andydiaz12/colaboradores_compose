package com.upaep.upaeppersonal.view.features.token

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.theme.Gray_title
import com.upaep.upaeppersonal.view.base.theme.Red_Upaep_Splash
import com.upaep.upaeppersonal.view.base.theme.Upaep_yellow
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import com.upaep.upaeppersonal.viewmodel.features.token.TokenViewModel

@Preview
@Composable
fun TokenScreen(tokenViewModel: TokenViewModel = hiltViewModel()) {
    val userPreferences = UserPreferences(LocalContext.current)
    val activeTheme by userPreferences.activeTheme.collectAsState(initial = defaultTheme)
    val context = LocalContext.current
    var imeiAccess by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        Log.i("imei", isGranted.toString())
        if(isGranted) {
            tokenViewModel.getImei(context = context)
        }
        imeiAccess = isGranted
    }
    BaseView(onRefresh = { }, loading = false, screenName = "TOKEN") {
        CardContent(activeTheme = activeTheme!!)
    }

    if (
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED
    ) {

        tokenViewModel.getImei(context = context)

//        tokenViewModel.getImei(context = context)
    } else {
        AlertDialog(
            onDismissRequest = { launcher.launch(Manifest.permission.READ_PHONE_STATE) },
            confirmButton = {
                TextButton(onClick = { launcher.launch(Manifest.permission.READ_PHONE_STATE) }) {
                    Text(text = "Aceptar")
                }
            },
            dismissButton = {

            },
            text = {
                Text(
                    text = "Se necesitan permisos para ocupar esta sección",
                    fontFamily = roboto_regular
                )
            })
    }
}

@Composable
fun CardContent(activeTheme: ActiveTheme) {
    var textValue by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
    ) {
        BasicTextField(
            value = textValue,
            onValueChange = { newText -> if (newText.length < 5) textValue = newText },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = activeTheme.BASE_TEXT_COLOR,
                textAlign = if (textValue.isEmpty()) TextAlign.Start else TextAlign.Center
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .border(width = 2.dp, color = Gray_title, shape = RoundedCornerShape(50.dp))
                        .padding(vertical = 5.dp, horizontal = 15.dp)
                ) {
                    if (textValue.isEmpty()) {
                        Text(
                            text = "INSERTA TU PIN",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            color = Gray_title,
                            fontFamily = roboto_regular
                        )
                    }
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        innerTextField()
                    }
                }
            },
            cursorBrush = SolidColor(Upaep_yellow),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            visualTransformation = PasswordVisualTransformation()
        )
//        OutlinedTextField(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clip(RoundedCornerShape(50.dp))
//                .border(2.dp, Color.Cyan),
//            value = textValue,
//            onValueChange = {
//                if (it.length < 5) textValue = it
//            },
//            placeholder = {
//                Text(
//                    text = "INSERTA TU PIN",
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.fillMaxWidth(),
//                    color = Gray_title,
//                    fontFamily = roboto_regular
//                )
//            },
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                containerColor = Color.Yellow,
//                focusedBorderColor = Color.Unspecified,
//                unfocusedBorderColor = Color.Unspecified
//            ),
//            textStyle = TextStyle(textAlign = TextAlign.Center),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
//            visualTransformation = PasswordVisualTransformation()
//        )
        Text(
            text = "*PIN de 4 dígitos",
            fontSize = 14.sp,
            fontFamily = roboto_regular,
            color = activeTheme.BASE_TEXT_COLOR
        )
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            onClick = { }, colors = ButtonDefaults.buttonColors(
                containerColor = Red_Upaep_Splash
            )
        ) {
            Text(text = "ENVIAR", fontFamily = roboto_regular, fontSize = 14.sp)
        }
        Text(
            text = "¿Olvidaste tu pin?",
            color = Red_Upaep_Splash,
            fontSize = 14.sp,
            fontFamily = roboto_regular
        )
    }
}