@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.joesemper.dronesettings.ui.settings

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.settings.mapping.SignalMappingSettingsScreen
import com.joesemper.dronesettings.ui.settings.sensors.SensorsSettingsScreen
import com.joesemper.dronesettings.ui.settings.signal.SignalSettingsScreen
import com.joesemper.dronesettings.ui.settings.timeline.TimeLineSettingsScreen
import com.joesemper.dronesettings.ui.settings.timeline.rememberTimeLineSettingsScreenState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = getViewModel()
) {

    val pages = remember {
        listOf(
            Page(
                titleRes = R.string.time_line,
                content = {
                    TimeLineSettingsScreen(
                        state = rememberTimeLineSettingsScreenState(
                            timelineState = viewModel.uiState.timelineState,
                            onInputDelayMinutes = { viewModel.onDelayTimeMinutesChange(it) },
                            onInputDelaySeconds = { viewModel.onDelayTimeSecondsChange(it) },
                            onInputCockingMinutes = { viewModel.onCockingTimeMinutesChange(it) },
                            onInputCockingSeconds = { viewModel.onCockingTimeSecondsChange(it) },
                            onActivateCockingTimeChange = { viewModel.onActivateCockingTimeChange(it) },
                            onInputSelfDestructionTimeMinutes = {
                                viewModel.onSelfDestructionTimeMinutesChange(it)
                            },
                            onInputSelfDestructionTimeSeconds = {
                                viewModel.onSelfDestructionTimeSecondsChange(it)
                            }
                        )
                    )
                }
            ),
            Page(
                titleRes = R.string.sensors,
                content = {
                    SensorsSettingsScreen(
                        state = viewModel.uiState.sensorsState,
                        onTargetDistanceChange = { viewModel.onTargetDistanceChange(it) },
                        onMinVoltageChange = { viewModel.onMinVoltageChange(it) },
                        onOverloadActivationChange = { viewModel.onOverloadActivationChange(it) },
                        onDeadTimeActivationChange = { viewModel.onDeadTimeActivationChange(it) },
                        onAccelerationChange = { viewModel.onAverageAccelerationChange(it) },
                        onDeviationChange = { viewModel.onAverageDeviationChange(it) },
                        onDeviationCoefficientChange = { viewModel.onDeviationCoefficientChange(it) },
                        onDeadTimeChange = { viewModel.onDeadTimeChange(it) }
                    )
                }
            ),
            Page(
                titleRes = R.string.signal_mapping,
                content = { SignalMappingSettingsScreen() }
            ),
            Page(
                titleRes = R.string.signal,
                content = {
                    SignalSettingsScreen(
                        state = viewModel.uiState.signalState,
                        onCockingPulseWidthHiChange = { viewModel.onCockingPulseWidthHiChange(it) },
                        onCockingPulseWidthLoChange = { viewModel.onCockingPulseWidthLoChange(it) },
                        onCockingPulseAmountChange = { viewModel.onCockingPulseAmountChange(it) },
                        onInfiniteCockingPulseChange = { viewModel.onInfiniteCockingPulseRepeatChange(it) },
                        onActivationPulseWidthHiChange = { viewModel.onActivationPulseWidthHiChange(it) },
                        onActivationPulseWidthLoChange = { viewModel.onActivationPulseWidthLoChange(it) },
                        onActivationPulseAmountChange = { viewModel.onActivationPulseAmountChange(it) },
                        onInfiniteActivationPulseChange = { viewModel.onInfiniteActivationPulseRepeatChange(it) },
                    )
                }
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                }
            )
        },
        floatingActionButton = {
            val context = LocalContext.current
            FloatingActionButton(onClick = {
                Toast.makeText(context, "Settings saved", Toast.LENGTH_SHORT).show()
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
            }
        }
    ) { paddingValues ->
        SettingsScreenContent(
            modifier = Modifier.padding(paddingValues),
            state = viewModel.uiState,
            pages = pages
        )
    }

}

@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    state: SettingsUiState,
    pages: List<Page>
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ScrollableTabRow(
            modifier = Modifier,
            selectedTabIndex = pagerState.currentPage
        ) {
            pages.forEachIndexed { index, page ->
                Tab(text = { Text(text = stringResource(page.titleRes)) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
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
            state = pagerState
        ) { page ->
            pages[page].content()
        }
    }
}

data class Page(
    @StringRes
    val titleRes: Int,
    val content: @Composable () -> Unit
)