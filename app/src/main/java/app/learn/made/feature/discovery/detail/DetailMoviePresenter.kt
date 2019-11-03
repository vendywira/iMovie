package app.learn.made.feature.discovery.detail

import app.learn.made.base.impl.BasePresenterImpl
import app.learn.made.base.service.BaseView
import app.learn.made.model.constant.ErrorMessage
import app.learn.made.network.MovieClientService
import io.reactivex.android.schedulers.AndroidSchedulers

class DetailMoviePresenter(private val apiClient: MovieClientService)
    : BasePresenterImpl(), DetailMovieService.Presenter {

    lateinit var view: DetailMovieService.View

    override fun <T : BaseView> setupView(view: T) {
        this.view = view as DetailMovieService.View
    }

    override fun getDetailMovie(movieId: Int) {
        super.addDisposable(apiClient.getDetailMovies(movieId)
            .doOnSubscribe {
                view.showLoading()
            }
            .doAfterTerminate {
                view.hideLoading()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { view.showMessage(ErrorMessage.ERROR_HTTP_GET_MOVIE) }
            .subscribe {
                view.showMovieDetail(it)
            })
    }

    override fun getImagesMovie(movieId: Int) {
        super.addDisposable(apiClient.getImagesMovie(movieId)
            .doOnSubscribe {
                view.showLoading()
            }
            .doAfterTerminate {
                view.hideLoading()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { view.showMessage(ErrorMessage.ERROR_HTTP_GET_MOVIE) }
            .subscribe {
                view.showImagesCarousel(it)
            })
    }
}