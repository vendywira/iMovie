package app.learn.made.base.service

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView

interface BaseView{

    fun getProgressBar() : LottieAnimationView?

    fun getSwipeRefresh() : SwipeRefreshLayout?

    fun showLoading()

    fun hideLoading()

    fun showMessage(message: String)
}