package com.dezzy.course.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Course(
    @StringRes val title: Int,
    val availableCourses: Int,
    @DrawableRes val image: Int
)