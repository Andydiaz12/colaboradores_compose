package com.upaep.upaeppersonal.view.base.genericcomponents

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upaep.upaeppersonal.view.base.theme.Dark_grey
import com.upaep.upaeppersonal.view.base.theme.Upaep_yellow

@Composable
fun InputDownLine(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String? = null,
    label: String? = null,
    labelColor: Color = Color.Black,
    bottomLineColor: Color = Upaep_yellow,
    fontSize: TextUnit = 20.sp,
    labelFontSize: TextUnit = 12.sp,
    labelOffset: Dp = (-15).dp
) {
    var offsetState by remember { mutableStateOf(0.dp) }
    var fontSizeState by remember { mutableFloatStateOf(fontSize.value) }
    val fontScale by animateFloatAsState(
        targetValue = fontSizeState,
        animationSpec = tween(durationMillis = 500),
        label = ""
    )

    val animatedOffset by animateDpAsState(
        targetValue = offsetState,
        animationSpec = tween(durationMillis = 500),
        label = ""
    )
    BasicTextField(
        modifier = Modifier
            .padding(top = 20.dp)
            .onFocusChanged { focusState ->
                offsetState = if (focusState.isFocused || value.isNotEmpty()) labelOffset else 0.dp
                fontSizeState =
                    if (focusState.isFocused || value.isNotEmpty()) labelFontSize.value else fontSize.value
            },
        value = value,
        onValueChange = { onValueChange(it) },
        textStyle = TextStyle(fontSize = fontSize)
    ) { innerTextField ->
        Box(modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = bottomLineColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }) {
            if (!label.isNullOrEmpty()) {
                Text(
                    text = label,
                    fontSize = fontScale.sp,
                    modifier = Modifier.offset(y = animatedOffset),
                    color = labelColor
                )
            }
            if(!placeHolder.isNullOrBlank()) {
                Text(text = placeHolder)
            }
            innerTextField()
        }
    }
}