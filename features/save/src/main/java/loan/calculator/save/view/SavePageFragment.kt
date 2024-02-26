/*
 * Created by Elnur Hajiyev on on 8/6/22, 2:32 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.save.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import loan.calculator.core.base.BaseFragment
import loan.calculator.save.effect.SavePageEffect
import loan.calculator.save.state.SavePageState
import loan.calculator.save.viewmodel.SavePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.common.extensions.gone
import loan.calculator.common.extensions.show
import loan.calculator.domain.entity.home.LanguageModel
import loan.calculator.domain.entity.home.SavedModel
import loan.calculator.domain.entity.saved.ExportTypeModel
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.save.R
import loan.calculator.save.adapter.SavedAdapter
import loan.calculator.save.bottomsheet.ExportTypeBottomSheet
import loan.calculator.save.bottomsheet.exportTypeBottomSheet
import loan.calculator.save.databinding.FragmentSavePageBinding

@AndroidEntryPoint
class SavePageFragment : BaseFragment<SavePageState, SavePageEffect, SavePageViewModel, FragmentSavePageBinding>() {

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSavePageBinding
        get() = FragmentSavePageBinding::inflate

    override fun getViewModelClass() = SavePageViewModel::class.java
    override fun getViewModelScope() = this

    lateinit var savedAdapter: SavedAdapter

    var amountFocus = false
    var convertedAmountFocus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //startAppAd = StartAppAd(this)
    }

    var typeList = arrayListOf<ExportTypeModel>()

    override val bindViews: FragmentSavePageBinding.() -> Unit = {
        toolbar.setBackButtonVisibility(show = false)

        toolbar.setToolbarRightActionClick {
            typeList.clear()
            typeList.add(ExportTypeModel("export as PDF", R.drawable.ic_pdf))
            typeList.add(ExportTypeModel("export as XLS",R.drawable.ic_xls))
            typeList.add(ExportTypeModel("export as CSV",R.drawable.ic_csv))
            openExportTypeBottomModule(typeList)
        }

        // getSavedLoans
        viewmodel.getSavedLoans()
    }

    private fun openExportTypeBottomModule(list: List<ExportTypeModel>){
        exportTypeBottomSheet {
            itemList = list
            onItemsSelected = {
                when(it.type){
                    0 ->{
                        //update pdf
                    }
                    1 ->{
                        //update xlms
                    }
                }
            }
        }?.show(childFragmentManager, ExportTypeBottomSheet::class.java.canonicalName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedAdapter = SavedAdapter(SavedAdapter.SavedItemClick{
            /*viewmodel.navigate(
                NavigationCommand.To(
                    SettingPageFragmentDirections.actionSettingPageFragmentToConvertUnitDetailPageFragment(unitType = it.type)
                )
            )*/
        })
        binding.recyclerViewSaved.adapter = savedAdapter
    }
    override fun observeState(state: SavePageState) {
        when(state){
            is SavePageState.GetSavedList -> updateSavedList(state.savedList)
            is SavePageState.DeleteSaveLoan -> deletedLoan()
        }
    }

    private fun deletedLoan() {

    }

    private fun updateSavedList(savedList: List<GetSavedLoanModel>) {
        if(savedList.isNullOrEmpty()){
            binding.shimmerLayoutSaved.gone()
            binding.noData.show()
            binding.recyclerViewSaved.gone()
        } else {
            binding.noData.gone()
            binding.shimmerLayoutSaved.gone()
            binding.recyclerViewSaved.show()
            savedAdapter.submitList(savedList)
        }
    }


    private fun openCurrencyModule(){
        /*currencyMenuBottomSheet {
            itemList = viewmodel.availableCurrency
            onItemsSelected = {
                updateSelectedAmount(type,it)
            }
            onDismiss = {
                binding.convertedAmountContainer.isEnabled = true
                binding.amountContainer.isEnabled = true
            }
        }?.show(childFragmentManager, CurrencyMenuBottomSheet::class.java.canonicalName)*/
    }

}
