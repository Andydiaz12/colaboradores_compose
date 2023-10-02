package com.upaep.upaeppersonal.view.base.genericcomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.theme.Yellow_Schedule
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular

@Composable
fun SelectorCard(
    activeTheme: ActiveTheme,
    onClick: () -> Unit,
    textContent: String,
    multipleElements: Boolean
) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 20.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = { onClick() }),
        colors = CardDefaults.cardColors(containerColor = activeTheme.BASE_BACKGROUND_COLOR),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = textContent,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 15.dp),
                fontFamily = roboto_bold
            )
            if(multipleElements) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "",
                    modifier = Modifier.align(
                        Alignment.CenterEnd
                    )
                )
            }
        }
    }
}