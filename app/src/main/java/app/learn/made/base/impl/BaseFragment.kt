package app.learn.made.base.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.learn.made.base.service.BasePresenter
import app.learn.made.base.service.BaseView
import app.learn.made.helper.invisible
import app.learn.made.helper.visible
import org.jetbrains.anko.design.snackbar

abstract class BaseFragment<out T : BasePresenter> : Fragment(), BaseView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.getPresenter()?.run {
            this.onAttach()
        }

        return onInitView(inflater, container, savedInstanceState)
    }

    abstract fun getPresenter(): T?

    protected abstract fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View

    override fun showMessage(message: String) {
        this.view?.rootView?.let {
            it.snackbar(message)
        }
    }

    override fun onDestroy() {
        getPresenter()?.onDetach()
        super.onDestroy()
    }

    override fun showLoading() {
        getProgressBar()?.visible()
    }

    override fun hideLoading() {
        getProgressBar()?.invisible()
    }
}