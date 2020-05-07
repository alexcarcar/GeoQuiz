package alex.carcar.geoquiz

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean) {
    var answered: Boolean = false
    var userAnswer: Boolean? = null
    var points: Int = 0
}