package com.mudhut.software.justiceapp.onboarding.models

import android.content.Context
import com.mudhut.software.justiceapp.R

data class OnBoardingStep(
    val title: String,
    val detail: String,
    val illustration: Int
)

fun getOnBoardingSteps(context: Context): List<OnBoardingStep> {
    return listOf(
        OnBoardingStep(
            "Justice App",
            context.resources.getString(R.string.lorem_ipsum),
            R.drawable.ic_blm_signs
        ),
        OnBoardingStep(
            "Sample Title ${2}",
            context.resources.getString(R.string.lorem_ipsum),
            R.drawable.ic_blm_laptop
        ),
        OnBoardingStep(
            "Sample Title ${3}",
            context.resources.getString(R.string.lorem_ipsum),
            R.drawable.ic_blm_protests
        ),
        OnBoardingStep(
            "Sample Title ${4}",
            context.resources.getString(R.string.lorem_ipsum),
            R.drawable.ic_blm_feminism
        )
    )
}
