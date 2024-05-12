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
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.core.base.BaseFragment
import loan.calculator.core.extension.DeeplinkNavigationTypes
import loan.calculator.core.extension.NavigationArgs
import loan.calculator.core.extension.toast
import loan.calculator.core.tools.NavigationCommand
import loan.calculator.domain.entity.saved.ExportTypeModel
import loan.calculator.uikit.R
import loan.calculator.save.adapter.SavedAdapter
import loan.calculator.save.bottomsheet.DialogBottomSheet
import loan.calculator.save.bottomsheet.ExportTypeBottomSheet
import loan.calculator.save.bottomsheet.dialogBottomSheet
import loan.calculator.save.bottomsheet.exportTypeBottomSheet
import loan.calculator.save.databinding.FragmentSavePageBinding
import loan.calculator.save.effect.SavePageEffect
import loan.calculator.save.state.SavePageState
import loan.calculator.save.viewmodel.SavePageViewModel
import loan.calculator.showCase.GuideListener
import loan.calculator.showCase.GuideView
import loan.calculator.showCase.config.DismissType
import loan.calculator.showCase.config.Gravity
import loan.calculator.showCase.config.PointerType
import loan.calculator.uikit.toolbar.LoanToolbar


@AndroidEntryPoint
class SavePageFragment :
    BaseFragment<SavePageState, SavePageEffect, SavePageViewModel, FragmentSavePageBinding>() {

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSavePageBinding
        get() = FragmentSavePageBinding::inflate

    override fun getViewModelClass() = SavePageViewModel::class.java
    override fun getViewModelScope() = this

    lateinit var savedAdapter: SavedAdapter

    var typeList = arrayListOf<ExportTypeModel>()

    var selectedView: View? = null

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
        typeList.add(ExportTypeModel(getString(R.string.export_pdf), R.drawable.ic_pdf, 0))
        typeList.add(ExportTypeModel(getString(R.string.export_csv), R.drawable.ic_csv, 1))
        typeList.add(ExportTypeModel(getString(R.string.export_xls), R.drawable.ic_xls, 2))

    }

    private fun openExportTypeBottomModule(list: List<ExportTypeModel>) {
        exportTypeBottomSheet {
            itemList = list
            onItemsSelected = {
                when (it.type) {
                    0 -> {
                        // update pdf
                        saveAsPdf()
                    }

                    1 -> {
                        //update csv
                        saveAsCsv()
                    }

                    2 -> {
                        //update xls

                    }
                }
            }
        }?.show(childFragmentManager, ExportTypeBottomSheet::class.java.canonicalName)
    }

    private fun saveAsCsv() {
        viewmodel.navigate(
            NavigationCommand.To(
                SavePageFragmentDirections.actionSavePageFragmentToSaveCsvFragment()
            )
        )
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
                NavigationCommand.Deeplink(
                    DeeplinkNavigationTypes.AMORTIZATION,
                    extras = mutableMapOf(
                        NavigationArgs.NAME to (it.name ?: ""),
                        NavigationArgs.START_DATE to (it.startDate ?: ""),
                        NavigationArgs.PAID_OFF to (it.paidOff ?: ""),
                        NavigationArgs.LOAN_AMOUNT to (it.loanAmount ?: ""),
                        NavigationArgs.INTEREST_RATE to (it.interestRate ?: ""),
                        NavigationArgs.FREQUENCY to (it.compoundingFrequency ?: ""),
                        NavigationArgs.TOTAL_REPAYMENT to (it.totalRePayment ?: ""),
                        NavigationArgs.TERM_IN_MONTH to (it.termInMonth.toString()),
                        NavigationArgs.TYPE to (it.type?:"")
                    ), false
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
                    selectedView = layoutManager?.findViewByPosition(0)
                }
                binding.noData.apply {
                    visibility = if (state.savedList.isNullOrEmpty()) View.VISIBLE else View.GONE
                }
                viewmodel.list = state.savedList
                savedAdapter.submitList(viewmodel.list)
                if (state.savedList.isNotEmpty())
                    if (viewmodel.getShowCase())
                        showCase()
            }

            is SavePageState.DeleteSaveLoan -> deletedLoan()
        }
    }

    private fun showCase() {
        GuideView.Builder(requireActivity())
            .setTitle("List Item")
            .setContentText(resources.getString(R.string.save_guide))
            .setGravity(Gravity.auto)
            .setTargetView(binding.recyclerViewSaved)
            .setPointerType(PointerType.circle)
            .setGuideListener(object : GuideListener {
                override fun onDismiss(view: View) {
                    viewmodel.setShowCase(false)
                }
            })
            .setDismissType(DismissType.outside) //optional - default dismissible by TargetView
            .build()
            .show()
    }

    private fun deletedLoan() {
        toast("'${viewmodel.selectedItem.name}' deleted.")
        binding.toolbar.setToolbarActionIcon(LoanToolbar.LoanToolbarOption.WITHOUT_LEFT_AND_RIGHT.value)
    }

}
