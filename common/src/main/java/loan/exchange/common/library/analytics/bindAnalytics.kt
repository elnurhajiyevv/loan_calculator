package loan.exchange.common.library.analytics

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import loan.exchange.domain.entity.analytics.CommonAnalyticsEvent
import loan.exchange.domain.repository.AnalyticsManager
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

fun Fragment.bindAnalytics(
    getScreenName: () -> String?,
    getScreenClass: () -> String = { javaClass.simpleName.ifBlank { javaClass.name } },
) {
    lifecycle.addObserver(object : DefaultLifecycleObserver {

        private val entryPoint by lazy {
            EntryPoints.get(requireContext().applicationContext, BindAnalyticsEntryPoint::class.java)
        }

        override fun onResume(owner: LifecycleOwner) {
            entryPoint.analyticsManager.trackEvent(
                CommonAnalyticsEvent.ScreenView(
                    screenName = getScreenName(),
                    screenClass = getScreenClass(),
                )
            )
        }
    })
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface BindAnalyticsEntryPoint {
    val analyticsManager: AnalyticsManager
}