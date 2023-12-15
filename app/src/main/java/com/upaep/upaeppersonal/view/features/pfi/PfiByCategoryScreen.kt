package com.upaep.upaeppersonal.view.features.pfi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.model.entities.features.pfi.PfiCategoryResponse
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseViewWithModal
import com.upaep.upaeppersonal.view.base.theme.Upaep_yellow
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import com.upaep.upaeppersonal.viewmodel.features.pfi.PfiByCategoryViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PfiByCategoryScreen(pfiByCategoryViewModel: PfiByCategoryViewModel = hiltViewModel()) {
    val categories = pfiByCategoryViewModel.categories
    val categoryName by pfiByCategoryViewModel.screenName.observeAsState()
    val loadingScreen by pfiByCategoryViewModel.loadingScreen.observeAsState()
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var selectedCategory by remember { mutableStateOf<PfiCategoryResponse?>(null) }
    val scope = rememberCoroutineScope()
    BaseViewWithModal(
        state = state,
        content = {
            categories.forEachIndexed { index, pfiCategoryResponse ->
                SingleCategory(content = pfiCategoryResponse.title ?: "", onClick = {
                    scope.launch {
                        selectedCategory = it
                        state.show()
                    }
                }, category = pfiCategoryResponse)
                if (index != categories.lastIndex) {
                    Divider(modifier = Modifier.padding(vertical = 10.dp))
                }
            }
        },
        modalContent = { ModalContent(selectedCategory) },
        loading = false,
        loadingScreen = loadingScreen!!,
        noData = categories.isEmpty(),
        upperCardContent = {
            Text(text = categoryName!!)
        }
    )
}

@Composable
fun SingleCategory(
    category: PfiCategoryResponse,
    content: String,
    onClick: (PfiCategoryResponse) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { onClick(category) }
            )
    ) {
        Text(
            text = content,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            fontFamily = roboto_bold
        )
        Image(
            painter = painterResource(id = R.drawable.icono_flecha_roja),
            contentDescription = "",
            modifier = Modifier.size(13.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ModalContent(category: PfiCategoryResponse? = null) {
    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = category?.title ?: "",
            fontFamily = roboto_bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        category?.purpose?.let { SingleCategory(title = "PROPÓSITO:", description = it) }
        category?.audience?.let { SingleCategory(title = "DIRIGIDO A:", description = it) }
        category?.type?.let { SingleCategory(title = "TIPO:", description = it) }
        category?.modality?.let { SingleCategory(title = "MODALIDAD:", description = it) }
        category?.dates?.let { SingleCategory(title = "FECHA:", description = it) }
        category?.schedules?.let { SingleCategory(title = "HORARIOS:", description = it) }
        category?.instructor?.let { SingleCategory(title = "CONFERENCIANTE:", description = it) }
        category?.contact?.let { SingleCategory(title = "CONTACTO:", description = it) }
        Divider(color = Upaep_yellow, modifier = Modifier.padding(vertical = 5.dp))
        Button(
            onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                backgroundColor = Upaep_yellow
            ), shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = "REGISTRATE AQUÍ", color = Color.White)
        }
    }
}

@Composable
fun SingleCategory(title: String, description: String) {
    Row(modifier = Modifier.padding(vertical = 5.dp)) {
        Surface(
            modifier = Modifier
                .padding(end = 15.dp)
                .width(15.dp)
                .height(15.dp), color = Upaep_yellow
        ) {}
        Text(
            text = title,
            modifier = Modifier.weight(4f),
            fontFamily = roboto_regular,
            fontSize = 14.sp
        )
        Text(
            text = description,
            fontFamily = roboto_regular,
            fontSize = 14.sp,
            modifier = Modifier.weight(6f)
        )
    }
}