package com.joesemper.dronesettings.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.joesemper.dronesettings.R
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    navigateToPreset: (Int) -> Unit,
    navigateToNewPreset: (Int) -> Unit,
    viewModel: HomeViewModel = getViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.uiState

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = context) {
        viewModel.uiActions.collect { action ->
            when (action) {
                is HomeUiAction.NewSettingsSet -> {
                    navigateToNewPreset(action.dataId)
                }

                is HomeUiAction.OpenPreset -> {
//                    navController.navigate("$PRESET_ROUTE/${action.dataId}")
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = { HomeScreenFab(onClick = { viewModel.onNewSettingsPresetClick() }) }
    ) { padding ->
        if (!state.isLoading) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                HomeSearchView(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    state = state.searchQuery.value,
                    enabled = state.enableSearch,
                    onClear = { viewModel.updateSearchQuery("") },
                    onQueryChange = { viewModel.updateSearchQuery(it) },
                    onSearch = { keyboardController?.hide() }
                )

                if (state.presets.isEmpty()) {

                    EmptyListView()

                } else {

                    HomeScreenContentView(
                        presets = state.filteredPresets,
                        onPresetItemClick = { navigateToPreset(it) }
                    )

                }
            }


        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContentView(
    modifier: Modifier = Modifier,
    presets: List<PresetDataUiState>,
    onPresetItemClick: (Int) -> Unit
) {
    if (presets.isEmpty()) {
        EmptySearchView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        )
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            presets.forEach { item ->
                item(
                    key = item.dataId
                ) {
                    PresetItem(
                        state = item,
                        onClick = { onPresetItemClick(item.dataId) }
                    )
                }
            }
        }
    }
}

@Composable
fun PresetItem(
    modifier: Modifier = Modifier,
    state: PresetDataUiState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable { onClick() }
            .padding(top = 8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(32.dp),
                painter = painterResource(id = R.drawable.baseline_list_alt_24),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = state.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = state.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = state.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Text(
                    text = state.time,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }

        }
        Divider(
            modifier = Modifier
                .padding(start = 40.dp)
                .fillMaxWidth()
        )
    }

}

@Composable
fun EmptySearchView(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = Icons.Default.Search, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.no_results),
            style = MaterialTheme.typography.bodyLarge
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar() {

    TopAppBar(
        navigationIcon = {
            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
        },
        title = {
            Text(text = stringResource(id = R.string.app_name))
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSearchView(
    modifier: Modifier,
    state: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClear: () -> Unit,
    enabled: Boolean = false
) {
    Box(
        modifier = modifier
    ) {

        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        SearchBar(
            modifier = Modifier.focusRequester(focusRequester),
            query = state,
            onQueryChange = onQueryChange,
            onSearch = {
                focusManager.clearFocus()
                onSearch(it)
            },
            enabled = enabled,
            active = false,
            onActiveChange = {},
            placeholder = {
                Text(text = stringResource(R.string.search_in_presets))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = enabled && state.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        onClear()
                    }
                    ) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                    }
                }
            },
        ) {

        }
    }
}

@Composable
fun HomeScreenFab(
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        text = { Text(text = stringResource(R.string.new_preset)) },
        icon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
        onClick = onClick
    )
}