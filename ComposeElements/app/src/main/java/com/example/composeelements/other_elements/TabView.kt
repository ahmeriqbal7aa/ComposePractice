package com.example.composeelements.other_elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabViewLayout() {

    val pagerState = rememberPagerState() // keep the state of tab
    val currentPage = pagerState.currentPage
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {

        TabRow(
            selectedTabIndex = currentPage,
            backgroundColor = Color.Yellow,
            //divider = { TabRowDefaults.Divider(color = Color.Red) },
            //indicator = { TabRowDefaults.Indicator(color = Color.Green) }
        ) {
            tabList.forEachIndexed { index, tabData ->
                Tab(
                    modifier = Modifier.padding(20.dp),
                    selected = currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } }
                ) {
                    Text(text = tabList[index].tab)
                }
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            count = tabList.size,
            state = pagerState
        ) { index ->
            Text(text = tabList[index].des)
        }
    }
}

data class TabData(
    val tab: String,
    val des: String
)

val tabList = listOf(
    TabData("Home", "This is Home Page"),
    TabData("Images", "This is Images Page"),
    TabData("Videos", "This is Videos Page")
)