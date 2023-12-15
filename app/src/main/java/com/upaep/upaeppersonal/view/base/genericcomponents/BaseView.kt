package com.upaep.upaeppersonal.view.base.genericcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atMostWrapContent
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.base.ErrorInfo
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.theme.Yellow_Schedule
import com.upaep.upaeppersonal.view.base.theme.roboto_bold

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BaseView(
    text: String = "",
    lazyPadding: Dp = 15.dp,
    screenName: String = "",
    loading: Boolean = false,
    rightIcon: Boolean = true,
    onRefresh: () -> Unit = {},
    upperCardText: String? = null,
    onUpperCardClick: () -> Unit = {},
    multipleElements: Boolean = false,
    navigation: NavHostController? = null,
    transparentBackground: Boolean = false,
    cardPickerColor: Color = Yellow_Schedule,
    error: ErrorInfo? = null,
    upperCardContent: (@Composable () -> Unit)? = null,
    onCloseDialog: () -> Unit = {},
    noData: Boolean = false,
    loadingScreen: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val activeTheme = userPreferences.activeTheme.collectAsState(initial = defaultTheme).value
    val refreshState = rememberPullRefreshState(refreshing = loading, onRefresh = { onRefresh() })
    if (loadingScreen) {
        LoadingSpinner()
    } else {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (header, cardContent) = createRefs()
            Header(modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
            }, navigation = navigation, screenName = screenName, rightIcon = rightIcon)
            if (noData) {
                Column(
                    modifier = Modifier
                        .constrainAs(cardContent) {
                            top.linkTo(header.bottom)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            height = Dimension.fillToConstraints.atMostWrapContent
                        }
                ) {
                    Box {
                        NoDataFound(modifier = Modifier.pullRefresh(refreshState))
                        PullRefreshIndicator(
                            refreshing = loading,
                            state = refreshState,
                            modifier = Modifier.align(Alignment.TopCenter),
                            backgroundColor = if (loading) Color.Yellow else Color.Magenta,
                        )
                    }
                }
            } else {
                CardContent(
                    refreshState = refreshState,
                    text = text,
                    activeTheme = activeTheme!!,
                    loading = loading,
                    content = content,
                    modifier = Modifier.constrainAs(cardContent) {
                        top.linkTo(header.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints.atMostWrapContent
                    },
                    transparentBackground = transparentBackground,
                    lazyPadding = lazyPadding,
                    upperCardText = upperCardText,
                    onUpperCardClick = onUpperCardClick,
                    multipleElements = multipleElements,
                    cardPickerColor = cardPickerColor,
                    upperCardContent = upperCardContent
                )
            }
        }
    }
    if (error?.visible == true) {
        ErrorDialog(message = error.message ?: "", onCloseDialog = { onCloseDialog() })
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardContent(
    text: String,
    lazyPadding: Dp,
    loading: Boolean,
    modifier: Modifier,
    upperCardText: String?,
    cardPickerColor: Color,
    activeTheme: ActiveTheme,
    multipleElements: Boolean,
    onUpperCardClick: () -> Unit,
    refreshState: PullRefreshState,
    transparentBackground: Boolean,
    upperCardContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        if (text.isNotEmpty()) {
            Text(
                text = text,
                color = activeTheme.BASE_TEXT_COLOR,
                fontFamily = roboto_bold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.size(15.dp))
        }
        if (upperCardContent != null) {
            upperCardContent()
        }
        Card(
            Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                containerColor = if (transparentBackground) Color.Transparent else activeTheme.BASE_BACKGROUND_COLOR.copy(
                    alpha = 0.5f
                )
            )
        ) {
            Box {
                Column {
                    if (!upperCardText.isNullOrBlank()) {
                        CardPicker(
                            onCardClick = { onUpperCardClick() },
                            textContent = upperCardText,
                            multipleElements = multipleElements,
                            cardPickerColor = cardPickerColor
                        )
                    }
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = lazyPadding)
                            .pullRefresh(refreshState)
                    ) {
                        item {
                            Spacer(modifier = Modifier.size(10.dp))
                            content()
                            Spacer(modifier = Modifier.size(10.dp))
                        }
                    }
                }
                PullRefreshIndicator(
                    refreshing = loading,
                    state = refreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    backgroundColor = if (loading) Color.Yellow else Color.Magenta,
                )
            }
        }
    }
}