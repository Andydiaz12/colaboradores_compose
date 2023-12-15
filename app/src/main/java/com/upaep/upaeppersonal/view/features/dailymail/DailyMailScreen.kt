package com.upaep.upaeppersonal.view.features.dailymail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.viewmodel.features.dailymail.DailyMailViewModel

@Preview(showSystemUi = true)
@Composable
fun DailyMailScreen(
    navigation: NavHostController? = null,
    dailyMailViewModel: DailyMailViewModel = hiltViewModel()
) {
    val categories = dailyMailViewModel.categories
    BaseView(navigation = navigation, screenName = "CORREO DEL DÍA", transparentBackground = true) {
        categories.forEach { category ->
            AsyncImage(
                model = category.urlicon,
                contentDescription = category.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = {
                            dailyMailViewModel.navigationToCategory(
                                categoryId = category.id!!,
                                navigation = navigation
                            )
                        })
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}
