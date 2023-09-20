package com.upaep.upaeppersonal.view.features.credential

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lightspark.composeqr.QrCodeView
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.view.base.genericcomponents.Header
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import com.upaep.upaeppersonal.viewmodel.features.credential.QrScreenViewModel
import androidx.compose.ui.platform.LocalContext

@Preview(showSystemUi = true)
@Composable
fun QrScreen(
    qrScreenViewModel: QrScreenViewModel = hiltViewModel(),
    navigation: NavHostController? = null
) {

    val context = LocalContext.current
    qrScreenViewModel.setNavigation(navigation = navigation!!, context = context)
    val timer by qrScreenViewModel.timer.observeAsState()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, qrContent) = createRefs()
        Header(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
            },
            screenName = "CREDENCIAL DIGITAL",
            navigation = navigation,
            backBtnExtraAction = true,
            backBtnExtraActionBtn = {
                qrScreenViewModel.resetBrightness()
//                qrScreenViewModel.setBrightness(context = context)
//                navigation?.popBackStack()
            })
        QrCard(modifier = Modifier.constrainAs(qrContent) {
            top.linkTo(header.bottom)
        }, timer = timer!!, onIconClick = {
            qrScreenViewModel.resetBrightness()
//            qrScreenViewModel.setBrightness(context)
            //navigation?.popBackStack()
        })
    }
}

@Composable
fun QrCard(modifier: Modifier, timer: String, onIconClick: () -> Unit) {
    Column(modifier = modifier) {
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
            CardHeader(
                timer = timer,
                modifier = Modifier.align(Alignment.TopCenter),
                onIconClick = onIconClick
            )
            CardQR(modifier = Modifier.align(Alignment.Center))
            CardFooter(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Composable
fun CardHeader(
    timer: String,
    modifier: Modifier,
    onIconClick: () -> Unit
) {
    Column(modifier = modifier) {
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Surface(
                border = BorderStroke(2.dp, Color.White),
                color = Color.Transparent,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(3.dp)
                        .size(25.dp)
                        .clickable { onIconClick() },
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            text = "EL CÓDIGO CADUCA EN:",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontFamily = roboto_regular,
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            text = timer,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontFamily = roboto_bold,
            fontSize = 28.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.size(15.dp))
    }
}

@Composable
fun CardQR(modifier: Modifier) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.size(100.dp))
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp)
        ) {
            QrCodeView(
                data = "https://upaep.mx",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(250.dp)
            )
        }
    }
}

@Composable
fun CardFooter(modifier: Modifier) {
    Text(
        text = "CÓDIGO DE ACCESO",
        fontFamily = roboto_regular,
        color = Color.White,
        modifier = modifier,
        fontSize = 20.sp
    )
}