package app.learn.made.base.impl

import app.learn.made.base.service.BasePresenter
import app.learn.made.helper.RxHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenterImpl : BasePresenter {

    private var disposableHolder: CompositeDisposable? = null

    override fun onAttach() {
        initDisposableHolder()
    }

    override fun onDetach() {
        if (RxHelper.isDisposableInitialized(this.disposableHolder)) {
            RxHelper.disposeComposite(this.disposableHolder)
        }
    }

    private fun initDisposableHolder() {
        if (!RxHelper.isDisposableInitialized(this.disposableHolder)) {
            this.disposableHolder = CompositeDisposable()
        }
    }

    fun addDisposable(disposable: Disposable) {
        initDisposableHolder()
        this.disposableHolder?.add(disposable)
    }
}
