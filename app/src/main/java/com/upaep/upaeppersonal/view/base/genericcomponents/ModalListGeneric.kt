package com.upaep.upaeppersonal.view.base.genericcomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import kotlin.reflect.full.memberProperties

@Composable
fun ModalListGeneric(
    onElementClick: (Any) -> Unit,
    list: List<Any>,
    obj: Any? = null,
    searchParam: String? = null,
    selected: Any? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    Column {
        Spacer(modifier = Modifier.size(10.dp))
        list.forEachIndexed { index, element ->
            Text(
                text = if (obj != null) {
                    element::class.memberProperties.find { it.name == searchParam }?.getter?.call(
                        element
                    ).toString()
                } else {
                    element.toString()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .let {
                        if (selected == element) it
                        else {
                            it
                                .alpha(0.5f)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                    onClick = { onElementClick(element) })
                        }
                    },
                textAlign = TextAlign.Center,
                fontFamily = roboto_bold
            )
            if (index + 1 < list.size) {
                Divider(modifier = Modifier.padding(10.dp))
            } else {
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }
}