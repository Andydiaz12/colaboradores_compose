package com.upaep.upaeppersonal.view.features.benefits

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.model.entities.features.benefits.BenefitDetail
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseViewWithModal
import com.upaep.upaeppersonal.view.base.theme.Gray_title
import com.upaep.upaeppersonal.view.base.theme.Upaep_yellow
import com.upaep.upaeppersonal.viewmodel.features.benefits.BenefitsByCategoryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview(showSystemUi = true)
@Composable
fun BenefitsByCategoryScreen(
    benefitsByCategoryViewModel: BenefitsByCategoryViewModel = hiltViewModel(),
    navigation: NavHostController? = null
) {
    val benefits = benefitsByCategoryViewModel.benefit
    val error by benefitsByCategoryViewModel.error.observeAsState(initial = null)
    var benefitRate by remember { mutableIntStateOf(-1) }
    val loadingScreen by benefitsByCategoryViewModel.loadingScreen.observeAsState()
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var selectedBenefit by remember { mutableStateOf<BenefitDetail?>(null) }
    var maxCardSize by remember { mutableIntStateOf(-1) }
    BaseViewWithModal(
        loadingScreen = loadingScreen!!,
        noData = benefits.isEmpty(),
        state = state,
        content = {
            for (rowIndex in benefits.indices step 3) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    for (colIndex in rowIndex until rowIndex + 3) {
                        if (colIndex < benefits.size) {
                            IndividualBenefit(
                                benefit = benefits[colIndex],
                                modifier = Modifier.weight(1f),
                                onBenefitClick = { benefit ->
                                    scope.launch {
                                        selectedBenefit = benefit
                                        state.show()
                                    }
                                },
                                getSize = { size ->
                                    if (size in (maxCardSize + 1)..299) {
                                        maxCardSize = size
                                    }
                                },
                                cardSize = maxCardSize
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        },
        modalContent = {
            ModalContent(benefit = selectedBenefit, onGradeBenefit = {
                benefitRate = it
            }, selected = benefitRate)
        },
        loading = false,
        error = error,
        navigation = navigation,
        onCloseDialog = {
            benefitsByCategoryViewModel.hideDialog()
        })
}

@Composable
fun ModalContent(benefit: BenefitDetail?, selected: Int, onGradeBenefit: (Int) -> Unit) {
    benefit?.let {
        Column {
            Text(
                text = "BELLEZA Y TIENDAS DE MODA",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            benefit.name?.let { Text(text = it) }
            benefit.managerName?.let { BenefitDescRow(description = it) }
            benefit.addres?.let { BenefitDescRow(description = it) }
            benefit.phone?.let { BenefitDescRow(description = it) }
            benefit.phoneAlternative?.let { BenefitDescRow(description = it) }
            benefit.mail?.let { BenefitDescRow(description = it) }
            Divider(color = Upaep_yellow)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "CALIFICA ESTA EMPRESA")
                repeat(5) { index ->
                    val resourse =
                        if (index <= selected) R.drawable.icono_beneficio_calificado else R.drawable.icono_beneficio_no_calificado
                    Icon(
                        painter = painterResource(id = resourse),
                        contentDescription = null,
                        modifier = Modifier.clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            onClick = { onGradeBenefit(index) }),
                        tint = Color.Black
                    )
                }
            }
            Text(text = "Danos tus comentarios")
            Button(onClick = { /*TODO*/ }) {
                Text(text = "ENVIAR")
            }
        }
    }
}

@Composable
fun BenefitDescRow(description: String) {
    if (description.isNullOrBlank()) {
        Row() {
            Icon(imageVector = Icons.Default.AccessAlarm, contentDescription = null)
            Text(text = description)
        }
    }
}

@Composable
fun IndividualBenefit(
    benefit: BenefitDetail,
    modifier: Modifier,
    onBenefitClick: (BenefitDetail) -> Unit,
    getSize: (Int) -> Unit,
    cardSize: Int
) {
    val cardSizeInDp = with(LocalDensity.current) { cardSize.toDp() }
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { onBenefitClick(benefit) }
            )
            .padding(horizontal = 5.dp)
            .let {
                if (cardSizeInDp > 0.dp) it.height(cardSizeInDp)
                else it
            }
            .onGloballyPositioned { coordinates ->
                getSize(coordinates.size.height)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            AsyncImage(
                model = benefit.icon,
                contentDescription = benefit.name,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = benefit.name ?: "",
                fontSize = 10.sp,
                color = Gray_title,
                lineHeight = 10.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}