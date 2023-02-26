package com.example.passwordgenerator.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.passwordgenerator.R
import com.example.passwordgenerator.compose.add_pass.AddPasswordScreen
import com.example.passwordgenerator.compose.passwords_screen.SavedPasswordsScreen
import kotlinx.coroutines.launch

enum class MainScreenPage(
    @StringRes val titleResId: Int,
    @DrawableRes val drawableResId: Int
) {
    SAVED_PASSWORDS(R.string.my_passwords_paige_title, R.drawable.ic_lock),
    ADD_PASSWORD(R.string.add_password_paige_title, R.drawable.ic_plus)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onPageChange: (MainScreenPage) -> Unit = {},
    pages: Array<MainScreenPage> = MainScreenPage.values()
) {
    val pagerState = rememberPagerState()

    LaunchedEffect(pagerState.currentPage) {
        onPageChange(pages[pagerState.currentPage])
    }

    Column(modifier.nestedScroll(rememberNestedScrollInteropConnection())) {
        val coroutineScope = rememberCoroutineScope()

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = colorResource(R.color.main),
            contentColor = colorResource(R.color.background)
        ) {
            pages.forEachIndexed { index, page ->
                val title = stringResource(id = page.titleResId)
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(text = title) },
                    icon = {
                        Icon(
                            painter = painterResource(id = page.drawableResId),
                            contentDescription = title
                        )
                    },
                    unselectedContentColor = colorResource(R.color.dark_main),
                    selectedContentColor = colorResource(R.color.background),
                )
            }
        }

        HorizontalPager(
            pageCount = pages.size,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { index ->
            val backgroundColor = colorResource(R.color.background)
            when (pages[index]) {
                MainScreenPage.SAVED_PASSWORDS -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = backgroundColor),
                        contentAlignment = Alignment.TopCenter,
                    ) {
                        SavedPasswordsScreen()
                    }
                }
                MainScreenPage.ADD_PASSWORD -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = backgroundColor),
                        contentAlignment = Alignment.TopCenter,
                    ) {
                        AddPasswordScreen()
                    }
                }
            }
        }
    }
}
