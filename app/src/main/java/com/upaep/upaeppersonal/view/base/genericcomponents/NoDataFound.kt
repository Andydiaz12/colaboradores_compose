package com.upaep.upaeppersonal.view.base.genericcomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.view.base.theme.Text_base_color
import com.upaep.upaeppersonal.view.base.theme.Upaep_red
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular

@Composable
fun NoDataFound(
    messageTitle: String = "",
    messageDescription: String = "",
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 20.dp)
    ) {
        val (image, content) = createRefs()
        val middleLine = createGuidelineFromTop(fraction = 0.5f)
        Image(
            painter = painterResource(id = R.drawable.icono_brumildo_sin_datos),
            contentDescription = "",
            modifier = Modifier
                .size(250.dp)
                .constrainAs(image) {
                    bottom.linkTo(middleLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        ContentSection(modifier = Modifier.constrainAs(content) {
            top.linkTo(middleLine)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
        }, messageTitle = messageTitle, messageDescription = messageDescription)
    }
}

@Composable
fun ContentSection(modifier: Modifier, messageTitle: String, messageDescription: String) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = messageTitle,
            fontFamily = roboto_bold,
            color = Upaep_red,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = messageDescription,
            textAlign = TextAlign.Center,
            color = Text_base_color,
            fontFamily = roboto_regular,
            fontSize = 18.sp
        )
    }
}