package com.example.corona.presentation.ui.dialogs

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.corona.R
import com.example.corona.databinding.DialogCountyInfoBinding
import com.example.corona.databinding.DialogLocationInfoBinding
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
                
                val sum = (it.confirmed + it.deaths + it.recovered).toDouble() / 100

                confirmedInfo.countryInfoConfirmedCount.text = it.confirmed.toString()
                confirmedInfo.progressBar.progress = (it.confirmed / sum).toInt()

                deathsInfo.countryInfoConfirmedCount.text = it.deaths.toString()
                deathsInfo.progressBar.progress = (it.deaths / sum).toInt()

                recoveredInfo.countryInfoConfirmedCount.text = it.recovered.toString()
                recoveredInfo.progressBar.progress = (it.recovered / sum).toInt()

                Glide.with(countyFlag).load(it.flagUrl).centerCrop().into(countyFlag)
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