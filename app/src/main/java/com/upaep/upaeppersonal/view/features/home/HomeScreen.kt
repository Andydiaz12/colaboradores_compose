package com.upaep.upaeppersonal.view.features.home

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.features.home.Feature
import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.Header
import com.upaep.upaeppersonal.view.base.genericcomponents.LoadingSpinner
import com.upaep.upaeppersonal.view.base.navigation.Routes
import com.upaep.upaeppersonal.view.base.theme.Red_darker
import com.upaep.upaeppersonal.view.base.theme.Upaep_yellow
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import com.upaep.upaeppersonal.viewmodel.features.home.HomeViewModel

@Preview(showSystemUi = true)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigation: NavHostController? = null
) {
//    val context = LocalContext.current
//    val userPreferences = UserPreferences(context)
//    val isDark = userPreferences.getTheme.collectAsState(initial = false).value
//
//
//    val lifecycle = LocalLifecycleOwner.current.lifecycle
//    val uiState by produceState<ScreenStates>(
//        initialValue = ScreenStates.Loading,
//        key1 = lifecycle,
//        key2 = homeViewModel
//    ) {
//        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
//            homeViewModel.uiState.collect { value = it }
//        }
//    }
//
//
//    when (uiState) {
//        is ScreenStates.Error -> {}
//        ScreenStates.Loading -> {
//            Text("Loading...")
//        }
//        is ScreenStates.Success<*> -> {
//            val data = (uiState as ScreenStates.Success<List<Test>>).data
//            LazyColumn() {
//                item {
//                    Button(onClick = { homeViewModel.agregarRandom() }) {
//                        Text("Agregar random")
//                    }
//                    Button(onClick = { homeViewModel.deleteData() }) {
//                        Text("Eliminar todo")
//                    }
//                }
//                items(data) { test ->
//                    Text("id = ${test.id} / name = ${test.name} / extra = ${test.extra}")
//                }
//            }
//        }
//
//        is ScreenStates.EmptyData -> {
//            Column {
//                Button(onClick = { homeViewModel.agregarRandom() }) {
//                    Text("Agregar random")
//                }
//            }
//        }
//    }

    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val activeTheme = userPreferences.activeTheme.collectAsState(initial = defaultTheme).value
    val loadingScreen by homeViewModel.loadingScreen.observeAsState()
    val features = homeViewModel.features
    val news = homeViewModel.news

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, content) = createRefs()
        Header(modifier = Modifier.constrainAs(header) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, notificationIcon = true, navigation = navigation, visibleLogo = true)
        ContentSection(
            modifier = Modifier.constrainAs(content) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            },
            textColor = activeTheme!!.BASE_TEXT_COLOR,
            cardColor = activeTheme.BASE_BACKGROUND_COLOR,
            navigation = navigation,
            pageContent = news,
            onClick = { upressItem ->
                homeViewModel.navigateToNews(
                    navigation = navigation,
                    clickedNew = upressItem
                )
            },
            features = features,
            loadingScreen = loadingScreen!!
        )
    }
}

@Composable
fun ContentSection(
    modifier: Modifier,
    textColor: Color,
    cardColor: Color,
    navigation: NavHostController?,
    pageContent: List<UpressItem>,
    onClick: (UpressItem) -> Unit,
    features: List<Feature>,
    loadingScreen: Boolean
) {
    if (loadingScreen) {
        LoadingSpinner()
    } else {
        LazyColumn(
            modifier = modifier.padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.size(20.dp))
                if (pageContent.isNotEmpty()) {
                    UpressContainer(
                        textColor = textColor,
                        cardColor = cardColor,
                        pageContent = pageContent,
                        onClick = onClick
                    )
                }
                FeaturesContainer(
                    features = features,
                    textColor = textColor,
                    navigation = navigation
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UpressContainer(
    textColor: Color,
    cardColor: Color,
    pageContent: List<UpressItem>,
    onClick: (UpressItem) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { pageContent.size }
    )
    Card(
        shape = RoundedCornerShape(10.dp), colors = CardDefaults.cardColors(
            containerColor = cardColor
        ), elevation = CardDefaults.cardElevation(1.dp), modifier = Modifier.height(100.dp)
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.padding(15.dp)) { index ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IndividualUpress(pageContent[index], textColor = textColor, onClick = onClick)
            }
        }
    }
    Spacer(modifier = Modifier.size(10.dp))
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        repeat(pagerState.pageCount) { iteration ->
            Dot(pagerState.currentPage == iteration)
        }
    }
}

@Composable
fun IndividualUpress(upress: UpressItem, textColor: Color, onClick: (UpressItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = { onClick(upress) })
    ) {
        Image(
            painter = painterResource(id = R.drawable.icono_upress_home),
            contentDescription = "",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = upress.title ?: "",
            fontFamily = roboto_bold,
            overflow = TextOverflow.Ellipsis,
            color = textColor,
            modifier = Modifier.fillMaxHeight(),
            fontSize = 14.sp
        )
    }
}

@Composable
fun FeaturesContainer(features: List<Feature>, textColor: Color, navigation: NavHostController?) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { _: Boolean ->

    }
    Spacer(modifier = Modifier.size(15.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        for (rowIndex in features.indices step 3) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                for (colIndex in rowIndex until rowIndex + 3) {
                    if (colIndex < features.size) {
                        IndividualFeature(
                            feature = features[colIndex],
                            modifier = Modifier
                                .weight(1f)
                                .let {
                                    val routes =
                                        Routes.getById(features[colIndex].featureId)?.routes
                                    if (routes.isNullOrEmpty()) {
                                        it
                                    } else {
                                        it.clickable {
                                            navigation?.navigate(routes) { launchSingleTop = true }
                                        }
                                    }
                                },
                            textColor = textColor
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            if (rowIndex + 3 < features.size) {
                Spacer(modifier = Modifier.size(5.dp))
            }
        }
    }
}

@Composable
fun IndividualFeature(feature: Feature, modifier: Modifier, textColor: Color) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = feature.featureIcon,
            contentDescription = feature.featureName,
            modifier = Modifier.size(45.dp)
        )
        Text(
            text = feature.featureName,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = textColor,
            fontFamily = roboto_regular,
            fontSize = 14.sp,
            lineHeight = 14.sp
        )
    }
}

@Composable
fun Dot(activeDot: Boolean) {
    Surface(
        modifier = Modifier.size(9.dp),
        shape = RoundedCornerShape(50.dp),
        color = if (activeDot) Upaep_yellow else Red_darker,
        border = BorderStroke(2.dp, if (activeDot) Upaep_yellow else Red_darker)
    ) {

    }
}

fun getFeatures(): List<Feature> {
    return listOf(
        Feature(featureId = "1", featureName = "Credencial digital", order = 4, featureIcon = ""),
        Feature(
            featureId = "2",
            featureName = "Plan de formación integral",
            order = 5,
            featureIcon = ""
        ),
        Feature(featureId = "3", featureName = "Directorio", order = 6, featureIcon = ""),
        Feature(featureId = "4", featureName = "Correo del día", order = 7, featureIcon = ""),
        Feature(featureId = "5", featureName = "UPRESS", order = 8, featureIcon = ""),
        Feature(featureId = "7", featureName = "Calendarios", order = 10, featureIcon = ""),
        Feature(featureId = "8", featureName = "Buzón", order = 11, featureIcon = ""),
        Feature(featureId = "9", featureName = "Biblioteca", order = 12, featureIcon = ""),
        Feature(
            featureId = "10",
            featureName = "Beneficios y convenios",
            order = 13,
            featureIcon = ""
        ),
        Feature(
            featureId = "11",
            featureName = "Registro de eventos",
            order = 14,
            featureIcon = ""
        ),
        Feature(featureId = "12", featureName = "Park U", order = 15, featureIcon = ""),
        Feature(featureId = "13", featureName = "Pase de lista", order = 1, featureIcon = ""),
        Feature(featureId = "14", featureName = "Token", order = 2, featureIcon = ""),
        Feature(featureId = "15", featureName = "Horarios", order = 3, featureIcon = "")
    )
}