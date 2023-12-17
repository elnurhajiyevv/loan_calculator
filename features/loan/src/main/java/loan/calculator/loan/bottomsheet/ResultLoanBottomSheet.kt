package loan.calculator.loan.bottomsheet

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import loan.calculator.common.extensions.addCorners
import loan.calculator.common.extensions.dp
import loan.calculator.core.base.BaseNotSerializableBottomSheet
import loan.calculator.loan.R
import loan.calculator.loan.databinding.DialogFragmentResultBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class ResultLoanBottomSheet : BaseNotSerializableBottomSheet() {

    private var backButton = false
    private var onDismiss: (() -> Unit)? = null
    private var monthly: String? = null
    private var interestRate: String? = null
    private var month: String? = null
    private var total: Double = 0.0
    private var interest: Double = 0.0

    lateinit var binding: DialogFragmentResultBinding

    override var showFullscreen: Boolean
        get() = false
        set(value) {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogFragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
        }
        initView()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun initView() {
        with(binding) {
            mainLayout.addCorners(
                solidColor = R.color.color_pure_white,
                radius = floatArrayOf(24.dp.toFloat(), 24.dp.toFloat(), 24.dp.toFloat(), 24.dp.toFloat(), 0f, 0f, 0f, 0f)
            )
            loanAmount.text = "$monthly $"
            loanTotalTitle.text = getString(R.string.loan_result_total_header,"$month")
            loanTotalAmount.text = "${String.format("%.2f",total)} $"
            loanTotalInterest.text = "${String.format("%.2f",interest)} $"
            loanInterestRateAmount.text = "$interestRate %"

            showPieChart(total,interest)
            binding.closeButton.setOnClickListener {
                dismiss()
            }

        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke()
    }


    class Builder {

        var onDismiss: (() -> Unit)? = null
        var onBack: (() -> Unit)? = null
        private var monthly: String? = null
        private var interestRate: String? = null
        private var month: String? = null
        private var total: Double = 0.0
        private var interest: Double = 0.0

        fun setMonthly(monthly: String){
            this.monthly = monthly
        }
        fun setInterestRate(interestRate: String){
            this.interestRate = interestRate
        }
        fun setMonth(month:String){
            this.month = month
        }
        fun setTotal(total:Double){
            this.total = total
        }
        fun setInterest(interest: Double){
            this.interest = interest
        }
        fun onDismiss(onDismiss: () -> Unit) {
            this.onDismiss = onDismiss
        }

        fun build(): ResultLoanBottomSheet? {
            val bottomSheet = ResultLoanBottomSheet()
            bottomSheet.onDismiss = onDismiss
            bottomSheet.month = month
            bottomSheet.monthly = monthly
            bottomSheet.interestRate = interestRate
            bottomSheet.total = total
            bottomSheet.interest = interest
            return bottomSheet
        }
    }

    private fun showPieChart(total:Double,interest: Double) {
        val pieEntries = ArrayList<PieEntry>()

        //initializing data
        val typeAmountMap: MutableMap<String, Double> = HashMap()
        typeAmountMap["Principal"] = total
        typeAmountMap["Interest"] = interest

        //initializing colors for the entries
        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#8200FF"))
        colors.add(Color.parseColor("#FFCC00"))


        //input data and fit data into pie chart entry
        for (type in typeAmountMap.keys) {
            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
        }

        //collecting the entries with label name
        val pieDataSet = PieDataSet(pieEntries,"")
        //setting text size of the value
        pieDataSet.valueTextSize = 9f
        //providing color list for coloring different entries
        pieDataSet.colors = colors
        pieDataSet.sliceSpace = 2f
        //grouping the data set from entry to chart
        val pieData = PieData(pieDataSet)
        //showing the value of the entries, default true if not set

        binding.chart.setUsePercentValues(true)
        binding.chart.description.isEnabled = false

        binding.chart.dragDecelerationFrictionCoef = 0.95f
        binding.chart.centerText = "Loan"
        binding.chart.isDrawHoleEnabled = true
        binding.chart.setHoleColor(Color.WHITE)
        binding.chart.setTransparentCircleColor(Color.WHITE)
        binding.chart.setTransparentCircleAlpha(110)
        binding.chart.holeRadius = 60f
        binding.chart.transparentCircleRadius = 76f
        binding.chart.setDrawCenterText(true)
        binding.chart.rotationAngle = 0f
        // enable rotation of the chart by touch
        binding.chart.isRotationEnabled = true
        binding.chart.isHighlightPerTapEnabled = true
        binding.chart.animateY(1400, Easing.EaseInOutQuad)
        binding.chart.legend.isEnabled = true

        // entry label styling
        binding.chart.setEntryLabelColor(Color.WHITE)
        binding.chart.setEntryLabelTextSize(9f)
        binding.chart.data = pieData
        binding.chart.setEntryLabelColor(R.color.color_pure_white)
        binding.chart.invalidate()
    }

}

fun resultLoanBottomSheet(lambda: ResultLoanBottomSheet.Builder.() -> Unit) =
    ResultLoanBottomSheet.Builder().apply(lambda).build()
