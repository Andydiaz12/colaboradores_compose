package com.upaep.upaeppersonal.view.features.credential

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.view.base.genericcomponents.Header
import com.upaep.upaeppersonal.view.base.navigation.Routes
import com.upaep.upaeppersonal.view.base.theme.roboto_regular

@Composable
fun CredentialScreen(navigation: NavHostController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, credential) = createRefs()
        Header(modifier = Modifier.constrainAs(header) {
            top.linkTo(parent.top)
        }, screenName = "CREDENCIAL DIGITAL")
        CredentialContainer(modifier = Modifier.constrainAs(credential) {
            top.linkTo(header.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, navigation = navigation)
    }
}

@Composable
fun CredentialContainer(modifier: Modifier, navigation: NavHostController) {
    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.size(40.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .paint(
                    painterResource(id = R.drawable.imagen_credencial),
                    contentScale = ContentScale.FillWidth
                )
                .padding(30.dp)
        ) {
            CredentialHeader(modifier = Modifier.align(Alignment.TopCenter))
            CredentialBody(modifier = Modifier.align(Alignment.Center))
            CredentialFooter(
                modifier = Modifier.align(Alignment.BottomCenter),
                navigation = navigation
            )
        }
    }
}

@Composable
fun CredentialHeader(modifier: Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = "CREDENCIAL DIGITAL\nOFICIAL DE LA UPAEP",
            fontSize = 12.sp,
            color = Color.White,
            fontFamily = roboto_regular
        )
        Image(
            painter = painterResource(id = R.drawable.icono_credencial_no_imagen),
            contentDescription = "",
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .size(80.dp)
        )
    }
}

@Composable
fun CredentialBody(modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "GERMÁN SOTO PONCE",
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = roboto_regular
        )
        Text(
            text = "INNOVACIÓN Y DESARROLLO DIGITAL",
            fontSize = 18.sp,
            color = Color.White,
            fontFamily = roboto_regular
        )
        Text(
            text = "PUESTO: JEFE DEPARTAMENTO INNOVACIÓN Y DESARROLLO DIGITAL",
            fontSize = 18.sp,
            color = Color.White,
            fontFamily = roboto_regular
        )
        Text(text = "94747", fontSize = 18.sp, color = Color.White, fontFamily = roboto_regular)
    }
}

@Composable
fun CredentialFooter(modifier: Modifier, navigation: NavHostController) {
    Image(
        painter = painterResource(id = R.drawable.imagen_credencial_btn_acceso),
        contentDescription = "mostrar qr",
        modifier = modifier.clickable { navigation.navigate(Routes.QrScreen.routes) {
            launchSingleTop = true
        } }
    )
}
