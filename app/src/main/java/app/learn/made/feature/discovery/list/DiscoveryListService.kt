package app.learn.made.feature.discovery.list

import app.learn.made.base.service.BasePresenter
import app.learn.made.base.service.BaseView
import app.learn.made.model.dto.DiscoveryDTO
import app.learn.made.model.dto.ListResponse

class DiscoveryListService {
    interface Presenter : BasePresenter {

        fun getDiscoveryList(sortBy: String)
    }

    interface View : BaseView {
        fun showDiscoveryList(teamResponse: ListResponse<DiscoveryDTO>?)
    }
}