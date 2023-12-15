package loan.exchange.currency

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import loan.exchange.currency.databinding.ActivityMainBinding
import loan.exchange.core.base.BaseActivity
import com.google.android.gms.ads.MobileAds
import com.startapp.sdk.adsbase.StartAppAd
import com.startapp.sdk.adsbase.StartAppSDK
import dagger.hilt.android.AndroidEntryPoint
import loan.exchange.common.extensions.gone
import loan.exchange.common.extensions.show

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var navHost: NavHostFragment
    lateinit var mainActivityMainBinding: ActivityMainBinding
    private lateinit var navController: NavController

    private val hiddenBottomNavigationViews by lazy {
        setOf(
            R.id.convertUnitDetailPageFragment,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        StartAppSDK.enableReturnAds(false)
        StartAppAd.disableSplash()
        // NOTE always use test ads during development and testing

        //MobileAds.initialize(this) {}

        /*StartAppAd.disableSplash()
        // NOTE always use test ads during development and testing
        */
        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG)

        mainActivityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this)).also {
            setContentView(it.root)
        }
        setStartGraph(savedInstanceState = savedInstanceState)

    }
    private fun setStartGraph(savedInstanceState: Bundle?) {
        navHost = supportFragmentManager.findFragmentById(R.id.main_nav_fragment) as NavHostFragment? ?: return
        navController = navHost.navController
        if (savedInstanceState == null) { //save the state after activity recreates
            val graph = navHost.navController.navInflater.inflate(R.navigation.main_graph)
            val (startGraphId, startGraphArgs) = findStartGraph()
            graph.setStartDestination(startGraphId)
            navController.setGraph(
                graph = graph,
                startDestinationArgs = startGraphArgs,
            )
        }
        navController.addOnDestinationChangedListener { _, destination, arguments ->
            if (destination.id !in hiddenBottomNavigationViews)
                mainActivityMainBinding.bottomNavigationMenu.show()
            else
                mainActivityMainBinding.bottomNavigationMenu.gone()
        }
        setupWithNavController(mainActivityMainBinding.bottomNavigationMenu, navController)
    }

    data class FindStartGraphResult(
        val graphId: Int,
        val args: Bundle? = null,
    )

    private fun findStartGraph(): FindStartGraphResult {
        return FindStartGraphResult(
                graphId = R.id.home_page_nav_graph,
                args = null,
            )
    }
}