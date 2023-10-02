package com.upaep.upaeppersonal.view.base.genericcomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upaep.upaeppersonal.view.base.theme.Yellow_Schedule
import com.upaep.upaeppersonal.view.base.theme.roboto_regular

@Composable
fun CardPicker(onCardClick: () -> Unit, textContent: String, multipleElements: Boolean, cardPickerColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(cardPickerColor)
            .clickable { onCardClick() }
            .padding(12.dp)
    ) {
        Text(text = textContent, color = Color.White, fontSize = 12.sp, fontFamily = roboto_regular)
        Spacer(modifier = Modifier.weight(1f))
        if(multipleElements) {
            Image(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}