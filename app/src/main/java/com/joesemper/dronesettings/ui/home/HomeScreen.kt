package com.joesemper.dronesettings.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.PRESET_ROUTE
import com.joesemper.dronesettings.ui.TIMELINE_ROUTE
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = getViewModel()
) {
    val context = LocalContext.current
    val state = remember(viewModel.uiState.value) {
        viewModel.uiState.value
    }

    LaunchedEffect(key1 = context) {
        viewModel.uiActions.collect { action ->
            when (action) {
                is HomeUiAction.NewSettingsSet -> {
                    navController.navigate("$TIMELINE_ROUTE/${action.setId}")
                }

                is HomeUiAction.OpenPreset -> {
                    navController.navigate("$PRESET_ROUTE/${action.setId}")
                }
            }

        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                },
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "New preset") },
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                onClick = { viewModel.onNewSettingsPresetClick() })
        }
    ) { padding ->

        when (state) {
            HomeUiState.Loading -> {

            }

            is HomeUiState.Loaded -> {
                val uiData = remember(state.settingsSetList) {
                    state.settingsSetList
                }

                if (uiData.isEmpty()) {
                    EmptyListView()
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        uiData.forEach { (title, items) ->
                            stickyHeader {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Surface(
                                        modifier = Modifier.alpha(0.7f),
                                        shape = MaterialTheme.shapes.medium,
                                        color = MaterialTheme.colorScheme.tertiaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(8.dp),
                                            text = title
                                        )
                                    }

                                }
                            }

                            items(
                                items.size,
                                key = { items[it].setId }
                            ) {
                                PresetItem(
                                    state = items[it],
                                    onClick = { viewModel.onPresetClick(items[it].setId) }
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun PresetItem(
    modifier: Modifier = Modifier,
    state: SettingsSetUiState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = state.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = state.description,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    softWrap = true
                )
            }
            Text(
                text = state.time,
                style = MaterialTheme.typography.titleSmall
            )
        }

        Divider(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun EmptyListView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = null,
                tint = Color.Gray
            )

            Text(
                text = stringResource(R.string.no_saved_presets),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
        }


    }
}