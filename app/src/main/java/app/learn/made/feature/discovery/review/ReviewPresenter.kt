package app.learn.made.feature.discovery.review

import app.learn.made.base.impl.BasePresenterImpl
import app.learn.made.base.service.BaseView
import app.learn.made.model.constant.ErrorMessage
import app.learn.made.network.MovieClientService
import io.reactivex.android.schedulers.AndroidSchedulers

class ReviewPresenter(private val apiClient: MovieClientService)
    : BasePresenterImpl(), ReviewService.Presenter {

    lateinit var view: ReviewService.View

    override fun <T : BaseView> setupView(view: T) {
        this.view = view as ReviewService.View
    }

    override fun getReviewList(movieId: Int, page: Int) {
        super.addDisposable(apiClient.getReviewsMovie(movieId, page)
            .doOnSubscribe {
                view.showLoading()
            }
            .doAfterTerminate {
                view.hideLoading()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { view.showMessage(ErrorMessage.ERROR_HTTP_GET_MOVIE) }
            .subscribe {
                view.showReviewList(it)
            })
    }

}