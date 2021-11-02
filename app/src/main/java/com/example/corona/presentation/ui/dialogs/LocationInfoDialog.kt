package com.example.corona.presentation.ui.dialogs

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.corona.R
import com.example.corona.databinding.DialogCountyInfoBinding
import com.example.corona.presentation.base.dialog.BaseBottomSheetDialog
import com.example.corona.presentation.model.dto.StatsInfoUiDto

class LocationInfoDialog : BaseBottomSheetDialog(R.layout.dialog_county_info) {
    private val binding: DialogCountyInfoBinding by viewBinding()

    private val data: StatsInfoUiDto? by lazy { arguments?.getParcelable(ARG_DATA_KEY) }

    override fun initViews() {
        super.initViews()
        data?.let {
            with(binding) {
                countryInfoConfirmedName.text = it.name

                val curConfirmed = it.confirmed[0]
                val curRecovered = it.recovered[0]
                val curDeath = it.deaths[0]

                val sum = (curConfirmed + curDeath + curRecovered).toDouble() / 100  // todo

                confirmedInfo.countryInfoConfirmedCount.text = curConfirmed.toString()
                confirmedInfo.progressBar.progress = (curConfirmed / sum).toInt()

                deathsInfo.countryInfoConfirmedCount.text = curDeath.toString()
                deathsInfo.progressBar.progress = (curDeath / sum).toInt()

                recoveredInfo.countryInfoConfirmedCount.text = curRecovered.toString()
                recoveredInfo.progressBar.progress = (curRecovered / sum).toInt()

                Glide.with(countyFlag).load(it.flagUrl).centerCrop().into(countyFlag)

                progressGraph.countyInfoGraph.data = it.confirmed
            }
        }
    }

    companion object {
        const val ARG_DATA_KEY = "LocationInfoDialog.ARG_DATA_KEY"

        fun newInstance(data: StatsInfoUiDto) = LocationInfoDialog().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_DATA_KEY, data)
            }
        }
    }
}