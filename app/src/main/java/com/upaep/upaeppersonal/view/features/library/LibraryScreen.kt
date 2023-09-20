package com.upaep.upaeppersonal.view.features.library

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.Header
import com.upaep.upaeppersonal.view.base.theme.Upaep_yellow
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular

@Preview(showSystemUi = true)
@Composable
fun LibraryScreen(navigation: NavHostController? = null) {
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val activeTheme = userPreferences.activeTheme.collectAsState(initial = defaultTheme).value
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, content) = createRefs()
        Header(modifier = Modifier.constrainAs(header) {
            top.linkTo(parent.top)
        }, navigation = navigation, screenName = "BIBLIOTECA")
        ContentScreen(modifier = Modifier.constrainAs(content) {
            top.linkTo(header.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
        }, theme = activeTheme!!)
    }
}

@Composable
fun ContentScreen(modifier: Modifier, theme: ActiveTheme) {
    LazyColumn(
        modifier = modifier.padding(end = 10.dp, start = 10.dp, top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = "Aplica únicamente para la biblioteca del campus central",
                fontFamily = roboto_regular,
                fontSize = 14.sp,
                color = theme.BASE_TEXT_COLOR,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.size(40.dp))
            SingleCircle(
                text = "BIBLIOTECA VIRTUAL",
                border = Upaep_yellow,
                image = theme.LIBRARY_ICON,
                backgroundColor = theme.BASE_BACKGROUND_COLOR,
                textColor = theme.BASE_TEXT_COLOR
            )
            SingleCircle(
                text = "RENOVACIÓN DE LIBROS",
                border = Upaep_yellow,
                image = R.drawable.icono_renovacion_libros,
                backgroundColor = Upaep_yellow,
                textColor = Color.White
            )
        }
    }
}

@Composable
fun SingleCircle(
    text: String,
    border: Color,
    image: Int,
    backgroundColor: Color,
    textColor: Color
) {
    Surface(
        color = backgroundColor,
        shape = CircleShape,
        border = BorderStroke(2.dp, border),
        modifier = Modifier
            .padding(vertical = 20.dp)
            .size(150.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Image(
                painterResource(id = image),
                contentDescription = "",
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = text,
                fontSize = 14.sp,
                fontFamily = roboto_bold,
                textAlign = TextAlign.Center,
                color = textColor,
                lineHeight = 14.sp
            )
        }
    }
}
