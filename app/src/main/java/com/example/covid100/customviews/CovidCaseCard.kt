package com.example.covid100.customviews

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.covid100.R

class CovidCaseCard(context: Context, attrs: AttributeSet?) : CardView(context, attrs) {


    private val tvType by lazy { findViewById<TextView>(R.id.tvType) }
    private val ivType by lazy { findViewById<ImageView>(R.id.ivType) }
    private val tvCount by lazy { findViewById<TextView>(R.id.count) }
    private val tvDelta by lazy { findViewById<TextView>(R.id.delta) }


    init {
        inflate(context, R.layout.layout_covid_case_card, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CovidCaseCard)

        tvType.text = attributes.getText(R.styleable.CovidCaseCard_resType)
        ivType.setImageDrawable(attributes.getDrawable(R.styleable.CovidCaseCard_android_src))
        tvCount.text = attributes.getString(R.styleable.CovidCaseCard_count)
        tvCount.setTextColor(attributes.getColor(R.styleable.CovidCaseCard_android_textColor, Color.BLACK))
        tvDelta.text = attributes.getString(R.styleable.CovidCaseCard_delta)
        tvDelta.setTextColor(attributes.getColor(R.styleable.CovidCaseCard_android_textColor, Color.BLACK))

        attributes.recycle()
    }

    fun setCount(count: String?) {
        tvCount.text = count
    }

    fun setDelta(delta: String?) {
        tvDelta.text  = if(delta?.get(0) != '-') "+$delta" else delta
    }
}