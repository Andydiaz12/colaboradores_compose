package com.upaep.upaeppersonal.view.features.login

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.theme.Placeholders
import com.upaep.upaeppersonal.view.base.theme.Upaep_red
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular

@Preview(showSystemUi = true)
@Composable
fun LoginScreen() {
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val webLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {})
    val activeTheme = userPreferences.activeTheme.collectAsState(initial = defaultTheme).value
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (logo, input, footer) = createRefs()
        LogoSection(modifier = Modifier.constrainAs(logo) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(input.top)
        })
//        InputsContainer(
//            modifier = Modifier.constrainAs(input) {
//                top.linkTo(parent.top)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//                bottom.linkTo(parent.bottom)
//            }, visiblePassword = true,
//            changePasswordVisibility = {},
//            mailInput = "",
//            passwordInput = "",
//            updateData = { _, _ -> }
//        )
        LoginContainer(modifier = Modifier.constrainAs(input) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }, launcher = webLauncher, textColor = activeTheme!!.BASE_TEXT_COLOR)
        Footer(modifier = Modifier.constrainAs(footer) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
    }
}

@Composable
fun LoginContainer(modifier: Modifier, textColor: Color, launcher: ActivityResultLauncher<Intent>) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputsContainer(
            visiblePassword = true,
            changePasswordVisibility = {},
            mailInput = "",
            passwordInput = "",
            updateData = { _, _ -> }
        )
        ForgotPassword(textColor = textColor, launcher = launcher)
    }
}

@Composable
fun LogoSection(modifier: Modifier) {
    Image(
        modifier = modifier.size(225.dp),
        painter = painterResource(id = R.drawable.logo_upaep),
        contentDescription = ""
    )
}

@Composable
fun Footer(modifier: Modifier) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        onClick = { },
        colors = ButtonDefaults.buttonColors(containerColor = Upaep_red)
    ) {
        Text(text = "ENTRAR")
    }
}

@Composable
fun ForgotPassword(launcher: ActivityResultLauncher<Intent>, textColor: Color) {
    Spacer(modifier = Modifier.size(20.dp))
    Text(
        "¿Olvidaste tu contraseña?",
        fontFamily = roboto_regular,
        fontSize = 12.sp,
        color = textColor,
        modifier = Modifier.clickable {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://oauth.upaep.mx/passwd-reset/?login_id=13")
            )
            launcher.launch(intent)
        }
    )
}

@Composable
fun InputsContainer(
    visiblePassword: Boolean,
    changePasswordVisibility: () -> Unit,
    mailInput: String,
    passwordInput: String,
    updateData: (String, String) -> Unit
) {
    Column(modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 40.dp)) {
        Input(
            placeHolder = "INGRESA TU CORREO ID",
            leadingIcon = painterResource(id = R.drawable.icono_usuario_login),
            contentDescritpion = "Usuario",
            value = mailInput,
            onValueChange = { updateData(it, "mail") }
        )
        Spacer(modifier = Modifier.size(26.dp))
        Input(
            placeHolder = "CONTRASEÑA",
            leadingIcon = painterResource(id = R.drawable.icono_candado),
            contentDescritpion = "Contraseña",
            value = passwordInput,
            onValueChange = { updateData(it, "password") },
            trailingIcon = {
                ShowPasswordIcon(passwordVisibility = visiblePassword) {
                    changePasswordVisibility()
                }
            },
            visualTransformation = if (visiblePassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Input(
    placeHolder: String,
    leadingIcon: Painter,
    contentDescritpion: String,
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        shape = RoundedCornerShape(5.dp),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        value = value,
        onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            placeholderColor = Color.Gray,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color.Black
        ),
        placeholder = {
            Text(
                text = placeHolder,
                fontFamily = roboto_bold,
                fontSize = 13.sp,
                color = Placeholders
            )
        },
        leadingIcon = {
            Image(
                painter = leadingIcon,
                contentDescription = contentDescritpion,
                modifier = Modifier.size(26.dp)
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation
    )
}

@Composable
fun ShowPasswordIcon(
    passwordVisibility: Boolean,
    changePasswordVisibility: () -> Unit
) {
    val imagen = if (passwordVisibility) {
        R.drawable.icono_ocultar_pass
    } else {
        R.drawable.icono_ver_pass
    }
    IconButton(onClick = { changePasswordVisibility() }) {
        Image(
            painter = painterResource(id = imagen),
            contentDescription = "Mostrar contraseña",
            modifier = Modifier.size(25.dp)
        )
    }
}