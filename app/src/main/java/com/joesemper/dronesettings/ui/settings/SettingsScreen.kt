package com.joesemper.dronesettings.ui.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.settings.mapping.SignalMappingSettingsScreen
import com.joesemper.dronesettings.ui.settings.sensors.SensorsSettingsScreen
import com.joesemper.dronesettings.ui.settings.signal.SignalSettingsScreen
import com.joesemper.dronesettings.ui.settings.timeline.TimeLineSettingsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController
) {

    val pages = rememberSaveable() {
        listOf(
            Page(
                titleRes = R.string.time_line,
                content = { TimeLineSettingsScreen() }
            ),
            Page(
                titleRes = R.string.sensors,
                content = { SensorsSettingsScreen() }
            ),
            Page(
                titleRes = R.string.signal_mapping,
                content = { SignalMappingSettingsScreen() }
            ),
            Page(
                titleRes = R.string.signal,
                content = { SignalSettingsScreen() }
            )
        )
    }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            ScrollableTabRow(
                modifier = Modifier,
                selectedTabIndex = pagerState.currentPage
            ) {
                pages.forEachIndexed { index, page ->
                    Tab(text = { Text(text = stringResource(page.titleRes)) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                        }
                    )
                }
            }
            HorizontalPager(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                pageCount = pages.size,
                verticalAlignment = Alignment.CenterVertically,
                state = pagerState
            ) { page ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(pages[page].titleRes))
                }

            }
        }

    }
}

data class Page(
    @StringRes
    val titleRes: Int,
    val content: @Composable () -> Unit
)