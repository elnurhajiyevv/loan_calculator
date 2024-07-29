/*
 * Created by Elnur Hajiyev on on 8/6/22, 2:32 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.loan.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatImageView
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.core.base.BaseFragment
import loan.calculator.loan.R
import loan.calculator.loan.databinding.FragmentLoanPageBinding
import loan.calculator.loan.effect.LoanPageEffect
import loan.calculator.loan.state.LoanPageState
import loan.calculator.loan.viewmodel.LoanPageViewModel


@AndroidEntryPoint
class LoanPageFragment : BaseFragment<LoanPageState, LoanPageEffect, LoanPageViewModel, FragmentLoanPageBinding>() {

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoanPageBinding
        get() = FragmentLoanPageBinding::inflate

    override fun getViewModelClass() = LoanPageViewModel::class.java
    override fun getViewModelScope() = this



    override val bindViews: FragmentLoanPageBinding.() -> Unit = {

    }


    private fun returnValueIfNull(editText: EditText): String{
        return if(editText.text.toString().trim().isNullOrEmpty())
            editText.hint.toString()
        else
            editText.text.toString()
    }



    private fun selection(backgroundResource: View, backgroundColor: View, imageView: AppCompatImageView) {
        backgroundResource.setBackgroundResource(R.drawable.radius_10_blue)
        backgroundColor.setBackgroundColor(resources.getColor(R.color.light_blue_100))
        imageView.setImageResource(R.drawable.ic_lock)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun observeState(state: LoanPageState) {
        when(state){

            else -> {}
        }
    }


}
