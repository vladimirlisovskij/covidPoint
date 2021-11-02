package com.example.corona.presentation.ui.listTab.coronaList

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.example.corona.databinding.ItemCountryInfoBinding
import com.example.corona.presentation.model.dto.StatsInfoUiDto
import com.example.corona.presentation.utils.animateGone
import com.example.corona.presentation.utils.animateVisible

class CoronaListAdapter : RecyclerView.Adapter<CoronaListAdapter.ViewHolder>() {
    var onClickListener: ((String) -> Unit) = { }

    private val openCards = HashSet<Int>()

    var items: List<StatsInfoUiDto> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCountryInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(
        private val binding: ItemCountryInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(index: Int, data: StatsInfoUiDto) {
            if (openCards.contains(index)) {
                openInfo()
            } else {
                closeInfo()
            }
            with(binding) {
                closeIcon.setOnClickListener {
                    openCards.remove(index)
                    closeInfo()
                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                }

                showMore.setOnClickListener {
                    openCards.add(index)
                    openInfo()
                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                    root.animate()
                        .translationY(0f)
                }

                countryInfoConfirmedName.text = data.name

                val curConfirmed = data.confirmed[0]
                val curRecovered = data.recovered[0]
                val curDeath = data.deaths[0]

                val sum = (curConfirmed + curDeath + curRecovered).toDouble() / 100  // todo

                confirmedInfo.countryInfoConfirmedCount.text = curConfirmed.toString()
                confirmedInfo.progressBar.progress = (curConfirmed / sum).toInt()

                deathsInfo.countryInfoConfirmedCount.text = curDeath.toString()
                deathsInfo.progressBar.progress = (curDeath / sum).toInt()

                recoveredInfo.countryInfoConfirmedCount.text = curRecovered.toString()
                recoveredInfo.progressBar.progress = (curRecovered / sum).toInt()

                Glide.with(countyFlag).load(data.flagUrl).centerCrop().into(countyFlag)

                progressGraph.countyInfoGraph.data = data.confirmed

                countyFlag.setOnClickListener {
                    onClickListener(data.countryCode)
                }
            }
        }

        private fun closeInfo() {
            with(binding) {
                closeIcon.animateGone()
                moreContainer.animateGone()
                showMore.animateVisible()

            }
        }

        private fun openInfo() {
            with(binding) {
                closeIcon.animateVisible()
                moreContainer.animateVisible()
                showMore.animateGone()
            }
        }
    }
}