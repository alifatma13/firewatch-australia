package com.alifatma.firewatch.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alifatma.firewatch.R
import com.alifatma.firewatch.ui.RfsUiState
import com.alifatma.firewatch.ui.components.EmptyIncidentListComponent
import com.alifatma.firewatch.ui.components.ErrorMessageComponent
import com.alifatma.firewatch.ui.components.FireLoadingIndicator
import com.alifatma.firewatch.ui.components.HeaderComponent
import com.alifatma.firewatch.ui.components.IncidentItemComponent
import com.alifatma.firewatch.ui.util.TestTags
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun IncidentListScreen(
    uiState: RfsUiState,
    onIncidentClick: (String) -> Unit,
    modifier: Modifier,
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val showHeader by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
                    && listState.firstVisibleItemScrollOffset < 10
        }
    }
    val showScrollToTopFab by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex >= 1
        }
    }
    val expandedById = remember {
        mutableStateMapOf<String, Boolean>()
    }
    Box(modifier = modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 0.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,

            ) {
            AnimatedVisibility(
                visible = showHeader,
                enter = fadeIn() + expandVertically(),
                exit = shrinkVertically() + fadeOut()
            ) {
                HeaderComponent(modifier = Modifier.padding(bottom = 8.dp))
            }

            when (uiState) {
                is RfsUiState.Loading -> FireLoadingIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                is RfsUiState.Error -> ErrorMessageComponent(
                    message = uiState.message,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                is RfsUiState.Success -> {
                    if (uiState.incidents.isEmpty()) {
                        EmptyIncidentListComponent(
                            message = "No active incidents right now",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                                .testTag(TestTags.INCIDENT_LIST),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.Start,
                            state = listState
                        ) {
                            items(uiState.incidents) { incident ->
                                IncidentItemComponent(
                                    incident = incident,
                                    onViewMap = { incident.id?.let { onIncidentClick(it) } },
                                    onToggleExpand = {
                                        val id = incident.id ?: return@IncidentItemComponent
                                        expandedById[id] = !(expandedById[id] ?: false)
                                    },
                                    expanded = expandedById[incident.id] == true
                                )

                            }
                        }

                    }

                }

            }
        }

        AnimatedVisibility(
            visible = showScrollToTopFab,
            modifier = modifier
                .padding(end = 16.dp, bottom = 64.dp)
                .align(AbsoluteAlignment.BottomRight),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            FloatingActionButton(
                modifier = Modifier.testTag(TestTags.SCROLL_TO_TOP_FAB),
                onClick = { scrollToTop(scope, listState) }) {
                Icon(
                    imageVector = Icons.Filled.ArrowUpward, contentDescription = stringResource(R.string.scroll_to_top_content_description)
                )
            }
        }
    }

}

fun scrollToTop(scope: CoroutineScope, listState: LazyListState) {
    scope.launch {
        listState.animateScrollToItem(0)
    }
}