package app.learn.made.feature.discovery.review

import app.learn.made.base.service.BasePresenter
import app.learn.made.base.service.BaseView
import app.learn.made.model.dto.ListResponse
import app.learn.made.model.dto.MovieReviewDTO

class ReviewService {
    interface Presenter : BasePresenter {

        fun getReviewList(movieId: Int, page: Int)
    }

    interface View : BaseView {
        fun showReviewList(teamResponse: ListResponse<MovieReviewDTO>?)
    }
}