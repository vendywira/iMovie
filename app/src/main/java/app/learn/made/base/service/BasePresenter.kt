package app.learn.made.base.service

interface BasePresenter {

    fun <T : BaseView> setupView(view: T)

    fun onAttach()

    fun onDetach()
}