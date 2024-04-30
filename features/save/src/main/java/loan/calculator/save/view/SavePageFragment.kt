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
import androidx.navigation.fragment.findNavController
import loan.calculator.core.base.BaseFragment
import loan.calculator.save.effect.SavePageEffect
import loan.calculator.save.state.SavePageState
import loan.calculator.save.viewmodel.SavePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.common.extensions.getDoubleValue
import loan.calculator.common.extensions.getIntValue
import loan.calculator.common.extensions.gone
import loan.calculator.common.extensions.show
import loan.calculator.core.extension.DeeplinkNavigationTypes
import loan.calculator.core.extension.NavigationArgs
import loan.calculator.core.extension.deeplinkNavigate
import loan.calculator.core.extension.toast
import loan.calculator.core.tools.NavigationCommand
import loan.calculator.domain.entity.home.LanguageModel
import loan.calculator.domain.entity.home.Loan
import loan.calculator.domain.entity.home.LoanInfo
import loan.calculator.domain.entity.home.SavedModel
import loan.calculator.domain.entity.saved.ExportTypeModel
import loan.calculator.domain.entity.saved.GetPdfLoanModel
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.save.R
import loan.calculator.save.adapter.SavedAdapter
import loan.calculator.save.bottomsheet.DialogBottomSheet
import loan.calculator.save.bottomsheet.ExportTypeBottomSheet
import loan.calculator.save.bottomsheet.dialogBottomSheet
import loan.calculator.save.bottomsheet.exportTypeBottomSheet
import loan.calculator.save.databinding.FragmentSavePageBinding
import loan.calculator.uikit.toolbar.LoanToolbar
import loan.calculator.uikit.util.returnValueIfNull

@AndroidEntryPoint
class SavePageFragment :
    BaseFragment<SavePageState, SavePageEffect, SavePageViewModel, FragmentSavePageBinding>() {

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSavePageBinding
        get() = FragmentSavePageBinding::inflate

    override fun getViewModelClass() = SavePageViewModel::class.java
    override fun getViewModelScope() = this

    lateinit var savedAdapter: SavedAdapter

    var typeList = arrayListOf<ExportTypeModel>()

    override val bindViews: FragmentSavePageBinding.() -> Unit = {
        toolbar.setBackButtonVisibility(show = false)

        toolbar.setToolbarRightActionClick {
            getListOfExport()
            openExportTypeBottomModule(typeList)
        }

        toolbar.setToolbarRightDeleteActionClick {
            // delete item and update adapter
            openDeleteDialogBottomModule()
        }

        toolbar.setToolbarRightActionClick {
            getListOfExport()
            openExportTypeBottomModule(typeList)
        }

        // getSavedLoans
        viewmodel.getSavedLoans()
    }

    private fun openDeleteDialogBottomModule() {
        dialogBottomSheet {
            onOkButtonClicked = {
                viewmodel.deleteSavedLoan(viewmodel.selectedItem.name ?: "")
            }
        }?.show(childFragmentManager, DialogBottomSheet::class.java.canonicalName)
    }

    fun getListOfExport() {
        typeList.clear()
        typeList.add(ExportTypeModel("Export as PDF", R.drawable.ic_pdf, 0))
        typeList.add(ExportTypeModel("Export as XLS", R.drawable.ic_xls, 1))
        typeList.add(ExportTypeModel("Export as CSV", R.drawable.ic_csv, 2))
    }

    private fun openExportTypeBottomModule(list: List<ExportTypeModel>) {
        exportTypeBottomSheet {
            itemList = list
            onItemsSelected = {
                when (it.type) {
                    0 -> {
                        //update pdf
                        saveAsPdf()
                    }

                    1 -> {
                        //update xls
                    }

                    2 -> {
                        //update csv
                    }
                }
            }
        }?.show(childFragmentManager, ExportTypeBottomSheet::class.java.canonicalName)
    }

    private fun saveAsPdf() {
        viewmodel.navigate(
            NavigationCommand.To(
                SavePageFragmentDirections.actionSavePageFragmentToSavePdfFragment(
                    getSavedLoanModel = viewmodel.selectedItem
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.shimmerLayoutSaved.startShimmer()
        savedAdapter = SavedAdapter(SavedAdapter.SavedItemClick {
            viewmodel.navigate(
                NavigationCommand.To(
                    SavePageFragmentDirections.actionSavePageFragmentToSavedAmortizationFragment(
                        loanInfo = LoanInfo(
                            name = it.name ?: "",
                            backgroundColor = 0,
                            startDate = it.startDate.toString(),
                            paidOff = it.paidOff.toString(),
                            loanAmount = it.loanAmount?.getDoubleValue() ?: 0.0,
                            interestRate = it.interestRate?.getDoubleValue() ?: 0.0,
                            frequency = it.compoundingFrequency.toString(),
                            totalRepayment = it.totalPayment ?: "",
                            termInMonth = it.termInMonth
                        )
                    )
                )
            )
        }, SavedAdapter.SavedItemOnLongClick { selected ->

            // set selected item to shared
            viewmodel.selectedItem = selected

            viewmodel.list.forEach {
                it.selected = selected.name == it.name
            }
            updateListAdapter(true)
        })
        binding.recyclerViewSaved.adapter = savedAdapter
    }

    fun updateListAdapter(on: Boolean) {
        savedAdapter.submitList(viewmodel.list)
        savedAdapter.notifyDataSetChanged()
        binding.toolbar.setToolbarActionIcon(LoanToolbar.LoanToolbarOption.WITH_FULL_RIGHT.value)
    }

    override fun observeState(state: SavePageState) {
        when (state) {
            is SavePageState.GetSavedList -> {
                binding.shimmerLayoutSaved.apply {
                    stopShimmer()
                    visibility = View.GONE
                }
                binding.recyclerViewSaved.apply {
                    visibility = View.VISIBLE
                }
                binding.noData.apply {
                    visibility = if (state.savedList.isNullOrEmpty()) View.VISIBLE else View.GONE
                }
                viewmodel.list = state.savedList
                savedAdapter.submitList(viewmodel.list)
            }

            is SavePageState.DeleteSaveLoan -> deletedLoan()
        }
    }

    private fun deletedLoan() {
        toast("'${viewmodel.selectedItem.name}' deleted.")
        binding.toolbar.setToolbarActionIcon(LoanToolbar.LoanToolbarOption.WITHOUT_LEFT_AND_RIGHT.value)
    }


    private fun openCurrencyModule() {
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
