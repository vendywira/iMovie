package app.learn.made.base.impl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.learn.made.base.service.BasePresenter
import app.learn.made.base.service.BaseView
import app.learn.made.helper.invisible
import app.learn.made.helper.visible
import org.jetbrains.anko.contentView
import org.jetbrains.anko.design.snackbar

abstract class BaseActivity<out T : BasePresenter> : AppCompatActivity(),
    BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInitView()

        getPresenter()?.run { this.onAttach() }
    }

    protected abstract fun onInitView()

    protected abstract fun getPresenter() : T?

    override fun showMessage(message: String) {
        this.contentView?.rootView?.let {
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