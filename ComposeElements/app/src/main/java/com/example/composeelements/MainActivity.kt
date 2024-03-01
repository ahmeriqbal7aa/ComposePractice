package com.example.composeelements

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.composeelements.quote_app_json_in_assets.QuoteDataManager
import com.example.composeelements.tweetsy_app.nav_host.TweetsyAppNavHost
import com.example.composeelements.ui.theme.ComposeElementsTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalPagerApi::class,
    ExperimentalMaterialApi::class
)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // For Testing
    /*@Inject
    lateinit var apiService: TweetAPIService*/

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // For Testing
        /*GlobalScope.launch {
            Log.e("Tweetsy", "Response : ${apiService.getCategories().body()}")
            Log.e("Tweetsy", "ResponseDistinct : ${apiService.getCategories().body()?.distinct()}")
        }*/

        //fetchQuotes() // QuoteApp

        setContent {
            ComposeElementsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    // ======================================================== //

//                    GetEnvironmentVariable()

                    // ======================================================== //

//                    RecyclerViewScreen(users = dummyData())
//                    OnBoardingScreen() // ViewPager
//                    Navigator(screen = FirstScreen) // Screen Navigation
//                    CheckBoxUi()
//                    SnackBar()
//                    Drawer()
//                    RadioButtonUi()
//                    TabViewLayout()
//                    CustomTitleBar()
//                    SearchView()
//                    ScreenNavigationWithSharedViewModel()
//                    CheckKeyboardVisibility()
//                    ProduceState_CirculatingLoaderExample()
//                    DerivedState()
//                    ProduceDerivedStates()

                    // ======================================================== //

//                    PizzaAppHomeScreen()
//                    ChatAppMainNavigation()
                    TweetsyAppNavHost()

                    // ======================================================== //

                    // ............. QuoteApp .............. //
                    /*if (QuoteDataManager.isDataLoaded.value) {
                        if (QuoteDataManager.currentPage.value == Pages.LISTING) {
                            QuoteListScreen(QuoteDataManager.data, onClick = { quote ->
                                QuoteDataManager.switchPage(quote)
                            })
                        } else {
                            QuoteDataManager.currentQuote?.let { QuoteDetail(quote = it) }
                        }
                    } else {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize(1f)
                        ) {
                            Text(text = "Loading...", style = MaterialTheme.typography.h6)
                        }
                    }*/

                    // ======================================================== //

                    /*DialogScreen()
                    AlertDialogScreen()*/

                    /*PickImageFromGallery()
                    PickImageFromCamera()*/

                    /*ConstraintLayoutArrangeHorizontally()
                    ConstraintLayoutExampleDemo()
                    LoginPageWithConstraintSet()*/

                    /*GuidLineExample()
                    BarrierExample()
                    ChainExample()*/

                    /*ParentContent()
                    Stateful()
                    Stateless()*/

                    /*SuggestionChipLayout()
                    FilterChipLayout()*/

                    /*CustomToggleButtonWithImage()
                    CustomToggleButtonPink()*/

                    // ======================================================== //

                    /*BottomSheetNavigator {
                        Navigator(screen = BottomSheetScreen)
                    }*/

                    // ======================================================== //

                    /*TabNavigator(tab = HomeTab) {
                        Scaffold(
                            bottomBar = {
                                BottomNavigation {
                                    TabNavigationItems(tab = HomeTab) // RowScope's extension func
                                    TabNavigationItems(tab = ProfileTab) // RowScope's extension func
                                }
                            }
                        ) { paddingValues ->
                            Box(modifier = Modifier.padding(paddingValues)) {
                                CurrentTab()
                            }
                        }
                    }*/
                }
            }
        }
    }

    private fun fetchQuotes() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(10000)
            QuoteDataManager.loadAssetsFromFile(applicationContext)
        }
    }
}

// Bottom Navigation
/*@Composable
fun RowScope.TabNavigationItems(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    BottomNavigationItem(selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(tab.options.icon!!, contentDescription = tab.options.title) }
    )
}*/
