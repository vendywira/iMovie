package app.learn.made.base.service

import com.airbnb.lottie.LottieAnimationView

interface BaseView{

    fun getProgressBar() : LottieAnimationView?

    fun showLoading()

    fun hideLoading()

    fun showMessage(message: String)
}