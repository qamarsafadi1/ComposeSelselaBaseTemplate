package com.qamar.composetemplate.data.remote.config.model.config

import com.selsela.cpapp.R


enum class Filter(val highPrice: Int) {
    ASC(R.string.low_prcie) ,DESC(R.string.high_price), NONE(0)
}