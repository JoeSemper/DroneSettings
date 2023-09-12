package com.joesemper.dronesettings.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsSet
import com.joesemper.dronesettings.ui.TIMELINE_ROUTE
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = getViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.uiState.value

    LaunchedEffect(key1 = context) {
        viewModel.uiEvents.collect { settingsSetId ->
            navController.navigate("$TIMELINE_ROUTE/${settingsSetId}")
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name))
            }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                AddNewPresetItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.onNewSettingsPresetClick() }
                )
            }
            items(state.size) {
                PresetItem(
                    state = state[it],
                    onClick = { }
                )
            }
        }
    }

}

@Composable
fun AddNewPresetItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier.height(64.dp),
        onClick = onClick
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
        Text(text = stringResource(R.string.add_new_settings_preset))
    }
}

@Composable
fun PresetItem(
    modifier: Modifier = Modifier,
    state: SettingsSet,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = state.setId.toString())
        Column(horizontalAlignment = Alignment.Start)
        {
            Text(text = state.name)
            Text(text = state.description)
        }
        Text(text = state.date.toString())
    }
}