package com.upaep.upaeppersonal.view.features.benefits

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.upaep.upaeppersonal.view.base.genericcomponents.Header
import com.upaep.upaeppersonal.view.base.theme.Gray_title

@Preview(showSystemUi = true)
@Composable
fun BenefitsByCategoryScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, content) = createRefs()
        Header(modifier = Modifier.constrainAs(header) {

        }, screenName = "BENEFICIOS")
        BenefitsContainer()
    }
}

@Composable
fun IndividualBenefit() {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column {
            Image(imageVector = Icons.Default.BrokenImage, contentDescription = "")
            Text(text = "", fontSize = 10.sp, color = Gray_title)
        }
    }
}

@Composable
fun BenefitsContainer() {
    Card(colors = CardDefaults.cardColors(
        containerColor = Color.Black
    )) {
        
    }
}