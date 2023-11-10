package com.upaep.upaeppersonal.view.features.mailbox

import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxFormResponse
import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxSurveyType
import com.upaep.upaeppersonal.model.entities.features.mailbox.SurveyResponses
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseViewWithModal
import com.upaep.upaeppersonal.view.base.genericcomponents.InputDownLine
import com.upaep.upaeppersonal.view.base.genericcomponents.ModalListGeneric
import com.upaep.upaeppersonal.view.base.theme.Upaep_yellow
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import com.upaep.upaeppersonal.viewmodel.features.mailbox.MailboxViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun MailboxScreen(mailboxViewModel: MailboxViewModel = hiltViewModel()) {
    val activeTheme by UserPreferences(LocalContext.current).activeTheme.collectAsState(initial = defaultTheme)
    val mailboxOptions by mailboxViewModel.mailboxOptions.observeAsState(emptyList())
    val selectedOption by mailboxViewModel.selectedOption.observeAsState()
    val answers = mailboxViewModel.answers
    val surveyOptions = mailboxViewModel.surveyOptions
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    BaseViewWithModal(
        modalContent = {
            ModalListGeneric(
                onElementClick = { selected ->
                    scope.launch {
                        val selection = selected as MailboxSurveyType
                        mailboxViewModel.getMailBoxSurvey(surveyType = selection)
                        mailboxViewModel.changeSelectedOption(selection)
                        state.hide()
                    }
                },
                list = mailboxOptions,
                searchParam = "name",
                obj = MailboxSurveyType::class.java,
                selected = selectedOption
            )
        },
        content = {
            MailboxSurvey(
                surveyOptions = surveyOptions,
                mailboxViewModel = mailboxViewModel,
                answers = answers
            )
        },
        state = state,
        loading = false,
        upperCardText = selectedOption?.name ?: "",
        onUpperCardClick = { scope.launch { state.show() } },
        multipleElements = mailboxOptions.size > 1
    )
}

@Composable
fun MailboxSurvey(
    surveyOptions: List<MailboxFormResponse>,
    mailboxViewModel: MailboxViewModel,
    answers: List<SurveyResponses>
) {
    Column(modifier = Modifier.padding(top = 10.dp)) {
        surveyOptions.forEach { element ->
            if (!element.indication.isNullOrEmpty() && element.componentType != 17) {
                Text(text = element.indication!!)
            }
            when (element.componentType) {
                1 -> { //Input con línea abajo
                    InputDownLine(
                        value = answers.find { it.itemId == element.itemId }?.responseValue ?: "",
                        onValueChange = { inputValue ->
                            mailboxViewModel.inputChange(element = element, value = inputValue)
                        },
                        label = element.title
                    )
                }

                2 -> { //Contenedor tipo de queja radio
                    RadioContainerType(radioGroup = element, onSelectionChange = { radioValue, componentType ->
                        mailboxViewModel.inputChange(element = element, value = radioValue, componentType = componentType ?: -1)
                    })
                }

                4 -> { //Aviso de privacidad
                    RowAcceptPrivacy(
                        checked = answers.find { it.itemId == element.itemId }?.responseValue.toBoolean(),
                        onCheckedChange = { inputValue ->
                            mailboxViewModel.inputChange(
                                element = element,
                                value = inputValue.toString()
                            )
                        },
                        text = element.title ?: ""
                    )
                }

                8, 12 -> { //12 para submit, 8 para botón de permisos
                    val enabledBtn by mailboxViewModel.enabledBtn.observeAsState(initial = false)
                    CenteredButton(
                        onClick = {
                            if (element.componentType == 12) {
                                mailboxViewModel.submitAnswers()
                            } else {

                            }
                        },
                        text = element.title ?: "",
                        fullWidth = (element.componentType == 8),
                        enabled = if (element.componentType == 12) enabledBtn else true
                    )
                }
            }
            Spacer(modifier = Modifier.size(5.dp))
        }
    }
}

@Composable
fun RowAcceptPrivacy(checked: Boolean, onCheckedChange: (Boolean) -> Unit, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = { onCheckedChange(it) })
        Text(text = text)
    }
}

@Composable
fun CenteredButton(
    onClick: () -> Unit,
    text: String,
    fullWidth: Boolean = false,
    enabled: Boolean = true
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { onClick() },
            modifier = Modifier
                .align(Alignment.Center)
                .let { if (fullWidth) it.fillMaxWidth() else it }
                .padding(vertical = 13.dp),
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Upaep_yellow),
            enabled = enabled
        ) {
            Text(text = text, color = Color.White, fontFamily = roboto_regular, fontSize = 14.sp)
        }
    }
}

@Composable
fun RadioContainerType(
    radioGroup: MailboxFormResponse,
    onSelectionChange: (String, Int?) -> Unit
) {
    var selection by remember { mutableIntStateOf(0) }
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(
            text = radioGroup.title ?: "",
            fontFamily = roboto_regular
        )
        Spacer(modifier = Modifier.size(5.dp))
        radioGroup.childs.forEach { child ->
            SimpleRadioButtonRow(radioButton = child, selection = selection, onSelectOption = {
                selection = it
                onSelectionChange(selection.toString(), child.componentType)
            })
            AnimatedVisibility(visible = (child.componentType == 16 && selection == child.itemId)) {
                child.childs.forEach { element ->
                    var value by remember { mutableStateOf("") }
                    InputDownLine(
                        value = value,
                        onValueChange = {
                            value = it
                            onSelectionChange(value, element.componentType)
                        },
                        label = element.title
                    )
                }
            }
        }
    }
}

@Composable
fun SimpleRadioButtonRow(
    radioButton: MailboxFormResponse,
    selection: Int,
    onSelectOption: (Int) -> Unit
) {
    Row(modifier = Modifier.padding(bottom = 7.dp)) {
        CustomRadioButton(
            selected = (radioButton.itemId == selection),
            onClick = { onSelectOption(radioButton.itemId ?: -1) })
        Spacer(modifier = Modifier.size(5.dp))
        Text(
            text = radioButton.title ?: "",
            fontFamily = roboto_regular,
            fontSize = 14.sp,
            modifier = Modifier.clickable(
                indication = null,
                onClick = { onSelectOption(radioButton.itemId ?: -1) },
                interactionSource = remember { MutableInteractionSource() })
        )
    }
}

@Composable
fun CustomRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val dotRadius = animateDpAsState(
        targetValue = if (selected) 12.dp / 2 else 0.dp,
        animationSpec = tween(durationMillis = 100), label = ""
    )
    val selectableModifier =
        if (onClick != null) {
            Modifier.selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.RadioButton,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = 40.0.dp / 2
                )
            )
        } else {
            Modifier
        }
    Canvas(
        modifier
            .then(selectableModifier)
            .wrapContentSize(Alignment.Center)
            .padding(2.dp)
            .requiredSize(20.0.dp)
    ) {
        val strokeWidth = 2.dp.toPx()
        drawCircle(
            Color.Black,
            radius = (20.0.dp / 2).toPx() - strokeWidth / 2,
            style = Stroke(strokeWidth)
        )
        if (dotRadius.value > 0.dp) {
            drawCircle(Color.Black, dotRadius.value.toPx() - strokeWidth / 2, style = Fill)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview(showSystemUi = true)
@Composable
fun TestDialog() {
    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { _ -> }
    var image by remember { mutableStateOf<Uri?>(null) }
    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            image = it
        }
    AlertDialog(
        onDismissRequest = { },
        buttons = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(text = "CANCELAR", modifier = Modifier.align(Alignment.CenterEnd))
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Elige una opción para adjuntar la evidencia")
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }) {
                    Icon(imageVector = Icons.Default.CameraAlt, contentDescription = null)
                    Text(text = "CÁMARA", modifier = Modifier.align(Alignment.Center))
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable { galleryLauncher.launch("image/*") }) {
                    Icon(imageVector = Icons.Default.Image, contentDescription = null)
                    Text(text = "GALERÍA", modifier = Modifier.align(Alignment.Center))
                }
                Image(painter = rememberImagePainter(image), contentDescription = null)
            }
        }
    )
}