package com.cektrend.cekinhome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.cektrend.cekinhome.databinding.FragmentDashboardBinding
import com.google.android.material.tabs.TabLayout
import com.cektrend.cekinhome.utils.showToast
import java.util.*

class DashboardFragment : Fragment(), TabLayout.OnTabSelectedListener {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        setupViewPager(binding.tabRoomViewpager)
        binding.tabRoom.setupWithViewPager(binding.tabRoomViewpager)
        binding.tabRoom.addOnTabSelectedListener(this)
        val view = binding.root
        return view
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFrag(AquariumFragment(), "Aquarium")
        adapter.addFrag(BedroomFragment(), "Bedroom")
        viewPager.adapter = adapter
    }

    private class ViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFrag(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        binding.tabRoomViewpager.setCurrentItem(tab!!.position)

        when (tab.position) {
            0 -> context?.showToast("One")
            1 -> context?.showToast("Two")
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {}
}