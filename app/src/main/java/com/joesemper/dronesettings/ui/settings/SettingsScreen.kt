@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.joesemper.dronesettings.ui.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = getViewModel()
) {

}

//    val pages = remember {
//        listOf(
//            Page(
//                id = 0,
//                titleRes = R.string.time_line,
//                content = {
//                    TimeLineSettingsScreen(
//                        state = viewModel.uiState.timelineState,
//                        onUiEvent = { viewModel.onTimelineUiEvent(it) }
//                    )
//                }
//            ),
//            Page(
//                id = 1,
//                titleRes = R.string.sensors,
//                content = {
//                    SensorsSettingsScreen(
//                        state = viewModel.uiState.sensorsState,
//                        onUiEvent = { viewModel.onSensorsUiEvent(it) }
//                    )
//                }
//            ),
//            Page(
//                id = 2,
//                titleRes = R.string.signal_mapping,
//                content = { SignalMappingSettingsScreen(
//                    state = viewModel.uiState.mappingUiState,
//                    onUiEvent = { viewModel.onMappingUiEvent(it) }
//                ) }
//            ),
//            Page(
//                id = 3,
//                titleRes = R.string.signal,
//                content = {
//                    SignalSettingsScreen(
//                        state = viewModel.uiState.signalState,
//                        onSignalUiEvent = { viewModel.onSignalUiEvent(it) }
//                    )
//                }
//            )
//        )
//    }

//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = stringResource(id = R.string.settings)) },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
//                    }
//                }
//            )
//        },
//        floatingActionButton = {
//            val context = LocalContext.current
//            FloatingActionButton(onClick = {
//                Toast.makeText(context, "Settings saved", Toast.LENGTH_SHORT).show()
//            }) {
//                Icon(imageVector = Icons.Default.Check, contentDescription = null)
//            }
//        }
//    ) { paddingValues ->
//        SettingsScreenContent(
//            modifier = Modifier.padding(paddingValues),
//            state = viewModel.uiState,
//            pages = pages
//        )
//    }
//
//}
//
//@Composable
//fun SettingsScreenContent(
//    modifier: Modifier = Modifier,
//    state: SettingsUiState,
//    pages: List<Page>
//) {
//    val pagerState = rememberPagerState()
//    val coroutineScope = rememberCoroutineScope()
//
//    Column(
//        modifier = modifier.fillMaxSize()
//    ) {
//        ScrollableTabRow(
//            modifier = Modifier,
//            selectedTabIndex = pagerState.currentPage
//        ) {
//            pages.forEachIndexed { index, page ->
//                Tab(text = { Text(text = stringResource(page.titleRes)) },
//                    selected = pagerState.currentPage == index,
//                    onClick = {
//                        coroutineScope.launch {
//                            pagerState.scrollToPage(index)
//                        }
//                    }
//                )
//            }
//        }
//
//        HorizontalPager(
//            modifier = Modifier
//                .weight(1f)
//                .fillMaxSize(),
//            pageCount = pages.size,
//            state = pagerState,
//            key = { pages[it].id }
//        ) { page ->
//            pages[page].content()
//        }
//    }
//}
//
//data class Page(
//    val id: Int,
//    @StringRes
//    val titleRes: Int,
//    val content: @Composable () -> Unit
//)