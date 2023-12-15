/*
 * Created by Elnur Hajiyev on on 8/6/22, 2:32 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.exchange.setting.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import loan.exchange.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import loan.exchange.domain.entity.enums.UNIT_TYPE
import loan.exchange.setting.R
import loan.exchange.setting.bottomsheet.UnitMenuBottomSheet
import loan.exchange.setting.bottomsheet.unitMenuBottomSheet
import loan.exchange.setting.databinding.FragmentConvertUnitDetailPageBinding
import loan.exchange.setting.effect.ConvertUnitDetailEffect
import loan.exchange.setting.state.ConvertUnitDetailState
import loan.exchange.setting.util.UnitConverter
import loan.exchange.setting.viewmodel.ConvertUnitDetailViewModel

@AndroidEntryPoint
class ConvertUnitDetailPageFragment : BaseFragment<ConvertUnitDetailState, ConvertUnitDetailEffect, ConvertUnitDetailViewModel, FragmentConvertUnitDetailPageBinding>() {


    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentConvertUnitDetailPageBinding
        get() = FragmentConvertUnitDetailPageBinding::inflate

    override fun getViewModelClass() = ConvertUnitDetailViewModel::class.java
    override fun getViewModelScope() = this

    private val arguments by navArgs<ConvertUnitDetailPageFragmentArgs>()

    override val bindViews: FragmentConvertUnitDetailPageBinding.() -> Unit = {
        viewmodel.updateValues(arguments.unitType)
        headerTitle.text = arguments.unitType.type

        fromEdittext.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    val valor: Double = if (s.isNotEmpty()) s.toString().
                    replace(""," ").
                    replace(" ","").toDouble() else 0.0
                    toEdittext.setText(UnitConverter.convert(valor, viewmodel.selectedFromConverter,viewmodel.selectedToConverter).toString())
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.fromAmountContainer.setOnClickListener {
            openUnitModule(SELECT_TYPE.FROM.name,arguments.unitType)
        }
        binding.toAmountContainer.setOnClickListener {
            openUnitModule(SELECT_TYPE.TO.name, arguments.unitType)
        }
    }

    private fun openUnitModule(selectType: String, type: UNIT_TYPE){
        unitMenuBottomSheet {
            itemList = viewmodel.fromConverter
            onItemsSelected = {
                updateSelectedAmount(selectType,type,it)
            }
            onDismiss = {
            }
        }?.show(childFragmentManager, UnitMenuBottomSheet::class.java.canonicalName)
    }

    private fun updateSelectedAmount(selectType: String,type: UNIT_TYPE, it: UnitConverter.Unit) {
        if(selectType == SELECT_TYPE.FROM.name){
            binding.fromAmountText.text = it.names
            binding.fromEdittext.hint = "${getString(R.string.from)} $it"
            viewmodel.selectedFromConverter = it
        } else  if(selectType == SELECT_TYPE.TO.name){
            binding.amountText.text = it.names
            binding.toEdittext.hint = "${getString(R.string.to)} $it"
            viewmodel.selectedToConverter = it
        }
    }

    enum class SELECT_TYPE(type: String){
        FROM("from"),TO("to")
    }

}
