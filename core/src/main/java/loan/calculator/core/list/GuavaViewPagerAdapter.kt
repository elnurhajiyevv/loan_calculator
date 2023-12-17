package loan.calculator.core.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class vpnViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val tabLayoutItems: Array<Pair<Fragment, String>>) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return tabLayoutItems.size
    }


    override fun createFragment(position: Int): Fragment {
         return tabLayoutItems[position].first
    }
}