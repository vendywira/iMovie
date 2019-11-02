package app.learn.made.feature.discovery.list

import app.learn.made.base.impl.BasePresenterImpl
import app.learn.made.base.service.BaseView
import app.learn.made.model.constant.ErrorMessage.Companion.ERROR_GET_DISCOVERY_MOVIE
import app.learn.made.network.MovieClientService
import io.reactivex.android.schedulers.AndroidSchedulers

class DiscoveryPresenter(private val apiClient: MovieClientService)
    : BasePresenterImpl(), DiscoveryListService.Presenter {

    lateinit var view: DiscoveryListService.View

    override fun <T : BaseView> setupView(view: T) {
        this.view = view as DiscoveryListService.View
    }

    override fun getDiscoveryList(sortBy: String) {
        super.addDisposable(apiClient.getDiscoveryMovies(sortBy)
            .doOnSubscribe {
                view.showLoading()
            }
            .doAfterTerminate {
                view.hideLoading()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { view.showMessage(ERROR_GET_DISCOVERY_MOVIE) }
            .subscribe {
                view.showDiscoveryList(it)
            })
    }

}