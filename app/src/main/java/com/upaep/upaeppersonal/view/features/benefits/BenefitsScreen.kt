package com.upaep.upaeppersonal.view.features.benefits

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.features.benefits.Benefit
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.navigation.Routes
import com.upaep.upaeppersonal.view.base.theme.ThemeSchema
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.viewmodel.features.benefits.BenefitsViewModel

@Preview(showSystemUi = true)
@Composable
fun BenefitsScreen(
    benefitsViewModel: BenefitsViewModel = hiltViewModel(),
    navigation: NavHostController? = null
) {
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val activeTheme = userPreferences.activeTheme.collectAsState(initial = defaultTheme).value
    val benefits = benefitsViewModel.benefits
    val loadingScreen by benefitsViewModel.loadingScreen.observeAsState()
    val error by benefitsViewModel.error.observeAsState()
    BaseView(
        onRefresh = {},
        loading = false,
        loadingScreen = loadingScreen!!,
        error = error,
        onCloseDialog = { benefitsViewModel.closeDialog() },
        navigation = navigation
    ) {
        benefits.forEachIndexed { index, benefit ->
            Spacer(Modifier.size(20.dp))
            IndividualBenefit(
                benefit = benefit,
                textColor = activeTheme!!.BASE_TEXT_COLOR,
                activeTheme = activeTheme,
                onClick = { categoryId ->
                    benefitsViewModel.selectCategory(
                        categoryId = categoryId,
                        navigation = navigation
                    )
                }
            )
            Spacer(modifier = Modifier.size(5.dp))
            if (index + 1 < benefits.size) Divider()
        }
    }
}

@Composable
fun IndividualBenefit(
    benefit: Benefit,
    textColor: Color,
    activeTheme: ActiveTheme,
    onClick: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { onClick(benefit.categoryId ?: 0) })
    ) {
        Text(
            text = benefit.name ?: "",
            modifier = Modifier.weight(1f),
            color = textColor,
            fontFamily = roboto_bold,
            fontSize = 14.sp
        )
        AsyncImage(
            model = if (ThemeSchema.getActiveTheme(activeTheme) == ThemeSchema.DARK) benefit.urlIconDark else benefit.urlIcon,
            contentDescription = benefit.name
        )
    }
}