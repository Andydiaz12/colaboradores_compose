package com.upaep.upaeppersonal.view.features.pfi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.viewmodel.features.pfi.PfiViewModel

@Preview
@Composable
fun PfiScreen(
    pfiViewModel: PfiViewModel = hiltViewModel(),
    navigation: NavHostController? = null
) {
    val categories = pfiViewModel.categories

    BaseView(
        transparentBackground = true
    ) {
        categories.forEach {
            AsyncImage(
                model = it.urlicon,
                contentDescription = it.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = {
                            pfiViewModel.navigateToPfiCategories(
                                category = it,
                                navigation = navigation,
                            )
                        }
                    )
            )
        }
    }
}