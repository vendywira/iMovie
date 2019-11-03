package app.learn.made.feature.discovery.detail

import app.learn.made.base.service.BasePresenter
import app.learn.made.base.service.BaseView
import app.learn.made.model.dto.MovieDetailDTO
import app.learn.made.model.dto.MovieImagesDTO

class DetailMovieService {
    interface Presenter : BasePresenter {
        fun getDetailMovie(movieId: Int)

        fun getImagesMovie(movieId: Int)
    }

    interface View : BaseView {
        fun showImagesCarousel(images: MovieImagesDTO)

        fun showMovieDetail(discoveryResponse: MovieDetailDTO)
    }
}