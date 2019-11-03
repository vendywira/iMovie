package app.learn.made.feature.discovery.list

import app.learn.made.base.impl.BasePresenterImpl
import app.learn.made.base.service.BaseView
import app.learn.made.model.constant.ErrorMessage.Companion.ERROR_HTTP_GET_MOVIE
import app.learn.made.network.MovieClientService
import io.reactivex.android.schedulers.AndroidSchedulers

class DiscoveryPresenter(private val apiClient: MovieClientService)
    : BasePresenterImpl(), DiscoveryService.Presenter {

    lateinit var view: DiscoveryService.View

    override fun <T : BaseView> setupView(view: T) {
        this.view = view as DiscoveryService.View
    }

    override fun getDiscoveryList(sortBy: String, page: Int) {
        super.addDisposable(apiClient.getDiscoveryMovies(sortBy, page)
            .doOnSubscribe {
                view.showLoading()
            }
            .doAfterTerminate {
                view.hideLoading()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { view.showMessage(ERROR_HTTP_GET_MOVIE) }
            .subscribe {
                view.showDiscoveryList(it)
            })
    }

}