package com.openclassrooms.realestatemanager.app.modules.loanSimulator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.utils.viewBindings.activityViewBinding
import com.openclassrooms.realestatemanager.databinding.ActivityLoanSimulatorBinding
import com.openclassrooms.realestatemanager.presenter.modules.loanSimulator.LoanSimulatorPresenter
import com.openclassrooms.realestatemanager.presenter.modules.loanSimulator.LoanSimulatorView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoanSimulatorActivity: AppCompatActivity(), LoanSimulatorView {
    
    @Inject
    lateinit var presenter: LoanSimulatorPresenter
    
    private val binding by activityViewBinding(ActivityLoanSimulatorBinding::inflate)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    
        presenter.attach(this)
        
        setupPieChart()
        setupUI()
    }
    
    private fun setupUI() {
        with(binding) {
            yearsRadioGroup.setOnCheckedChangeListener { _, _ ->
                presenter.didSelectLoanYears(getSelectedLoanYears())
                presenter.checkForm()
            }
            
            loanEditText.doOnTextChanged { text, _, _, _ ->
                if(text.isNullOrEmpty()) {
                    presenter.didSelectLoanValue(0)
                    presenter.checkForm()
                } else {
                    presenter.didSelectLoanValue(text.toString().toInt())
                    presenter.checkForm()
                }
            }
        }
    }
    
    private fun setupPieChart() {
        binding.pieChart.description.isEnabled = false
        binding.pieChart.legend.isEnabled = false
    }
    
    private fun getSelectedLoanYears(): Int =
        when (binding.yearsRadioGroup.checkedRadioButtonId) {
            R.id.fifteenYearsRadioButton -> 15
            R.id.twentyYearsRadioButton -> 20
            R.id.twentyFiveYearsRadioButton -> 25
            else -> 0
        }
    
    //region LoanSimulatorView Callbacks
    override fun onShowLoanDetail(notaryFees: Float, interestRate: Float, loan: Float, totalLoan: Float, monthlyPayment: Float) {
        binding.pieChart.isVisible = true
        
        val totalFeesList: ArrayList<PieEntry> = arrayListOf(
                PieEntry(loan, getString(R.string.loan_simulator_loan)),
                PieEntry(notaryFees, getString(R.string.loan_simulator_notary_fees)),
                PieEntry(interestRate, getString(R.string.loan_simulator_interest_rate))
        )
    
        val pieDataSet = PieDataSet(totalFeesList, getString(R.string.loan_simulator_total))
    
        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        pieDataSet.colors = colors
    
        val pieData = PieData(pieDataSet)
        pieData.setValueTextSize(12f)
    
        with(binding) {
            pieChart.isVisible = false
            pieChart.data = pieData
            pieChart.isVisible = true
            monthlyPaymentTextView.text = monthlyPayment.toString()
            totalCostTextView.text = totalLoan.toString()
        }
    }
    
    override fun onEmptyAllView() {
        with(binding) {
            binding.monthlyPaymentTextView.text = ""
            binding.totalCostTextView.text = ""
            binding.pieChart.isVisible = false
        }
    }
    //endregion
}