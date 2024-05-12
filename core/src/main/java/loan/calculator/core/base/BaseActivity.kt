package loan.calculator.core.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment is BaseFragment<*, *, *, *>) {
                fragment.onNewIntent(intent)
            }

            fragment.childFragmentManager.fragments.forEach { childFragment ->
                if (childFragment is BaseFragment<*, *, *, *>) {
                    childFragment.onNewIntent(intent)
                }
            }
        }
    }
}