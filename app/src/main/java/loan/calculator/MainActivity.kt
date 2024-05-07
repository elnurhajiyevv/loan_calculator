package loan.calculator

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.startapp.sdk.adsbase.StartAppAd
import com.startapp.sdk.adsbase.StartAppSDK
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.common.extensions.gone
import loan.calculator.common.extensions.show
import loan.calculator.core.base.BaseActivity
import loan.calculator.core.extension.setWindowFlag
import loan.calculator.data.repository.SettingPreferences
import loan.calculator.data.util.RuntimeLocaleChanger
import loan.calculator.databinding.ActivityMainBinding
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject lateinit var settingPreferences: SettingPreferences

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var navHost: NavHostFragment
    lateinit var mainActivityMainBinding: ActivityMainBinding
    private lateinit var navController: NavController

    private val hiddenBottomNavigationViews by lazy {
        setOf(
            R.id.amortizationFragment,
            R.id.savePdfFragment
        )
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(RuntimeLocaleChanger.wrapContext(newBase))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        RuntimeLocaleChanger.overrideLocale(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setThemeByColor(settingPreferences.colorValue)

        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            this.setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            this.setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

        StartAppSDK.enableReturnAds(false)
        StartAppAd.disableSplash()

        //window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        // NOTE always use test ads during development and testing

        //MobileAds.initialize(this) {}

        /*StartAppAd.disableSplash()
        // NOTE always use test ads during development and testing
        */
        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG)

        mainActivityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this)).also {
            setContentView(it.root)
        }

        AppCompatDelegate.setDefaultNightMode(if(mainViewModel.getLightTheme()) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES)

        setStartGraph(savedInstanceState = savedInstanceState)

        /*val intent = intent
        val uriPdf = intent.data
        if(uriPdf.isNotNull()){
            mainViewModel.navigate(NavigationCommand.Deeplink(DeeplinkNavigationTypes.SAVE_PDF,null,false))
        }*/

    }

    private fun setThemeByColor(colorValue: Int) {
        when(colorValue){
            0->{
                setTheme(R.style.Theme_One)
            }
            1->{
                setTheme(R.style.Theme_Two)
            }
            2->{
                setTheme(R.style.Theme_Three)
            }
            3->{
                setTheme(R.style.Theme_Four)
            }
            4->{
                setTheme(R.style.Theme_Five)
            }
            5->{
                setTheme(R.style.Theme_Six)
            }
            6->{
                setTheme(R.style.Theme_Seven)
            }
            7->{
                setTheme(R.style.Theme_Eight)
            }
            8->{
                setTheme(R.style.Theme_Nine)
            }
            9->{
                setTheme(R.style.Theme_Ten)
            } else ->{
                // set default
            }
        }
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
                graphId = R.id.loan_page_nav_graph,
                args = null,
            )
    }
}