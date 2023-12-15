/*
 * Created by Elnur Hajiyev on on 8/6/22, 2:32 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.exchange.setting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import loan.exchange.core.base.BaseFragment
import loan.exchange.setting.databinding.FragmentSettingPageBinding
import loan.exchange.setting.effect.SettingPageEffect
import loan.exchange.setting.state.SettingPageState
import loan.exchange.setting.viewmodel.SettingPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import loan.exchange.core.tools.NavigationCommand
import loan.exchange.domain.entity.enums.UNIT_TYPE
import loan.exchange.domain.entity.home.CurrencyModel
import loan.exchange.domain.entity.unit.UnitModel
import loan.exchange.setting.R
import loan.exchange.setting.adapter.UnitAdapter

@AndroidEntryPoint
class SettingPageFragment : BaseFragment<SettingPageState, SettingPageEffect, SettingPageViewModel, FragmentSettingPageBinding>() {

    lateinit var unitAdapter: UnitAdapter
    private lateinit var itemList: List<UnitModel>

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingPageBinding
        get() = FragmentSettingPageBinding::inflate

    override fun getViewModelClass() = SettingPageViewModel::class.java
    override fun getViewModelScope() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //startAppAd = StartAppAd(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unitAdapter = UnitAdapter(getItemList(), UnitAdapter.UnitItemClick {
            viewmodel.navigate(
                NavigationCommand.To(
                    SettingPageFragmentDirections.actionSettingPageFragmentToConvertUnitDetailPageFragment(unitType = it.type)
                )
            )
        })
        binding.recyclerViewUnit.adapter = unitAdapter
    }

    private fun getItemList(): List<UnitModel>{
        var arrayList = arrayListOf<UnitModel>()
        arrayList.add(UnitModel("ic_weight",requireContext().getString(R.string.weight), UNIT_TYPE.WEIGHT))
        arrayList.add(UnitModel("ic_volume",requireContext().getString(R.string.volume),UNIT_TYPE.VOLUME))
        arrayList.add(UnitModel("ic_temperature",requireContext().getString(R.string.temperature),UNIT_TYPE.TEMPERATURE))
        arrayList.add(UnitModel("ic_length",requireContext().getString(R.string.length),UNIT_TYPE.LENGTH))
        arrayList.add(UnitModel("ic_speed",requireContext().getString(R.string.speed),UNIT_TYPE.SPEED))
        arrayList.add(UnitModel("ic_area",requireContext().getString(R.string.area),UNIT_TYPE.AREA))
        arrayList.add(UnitModel("ic_time",requireContext().getString(R.string.time),UNIT_TYPE.TIME))
        arrayList.add(UnitModel("ic_pressure",requireContext().getString(R.string.pressure),UNIT_TYPE.PRESSURE))
        arrayList.add(UnitModel("ic_storage",requireContext().getString(R.string.storage),UNIT_TYPE.STORAGE))
        return arrayList.toList()
    }

}