package app.learn.made.network

import app.learn.made.model.dto.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieClientService {

    companion object {
        const val MOVIE_ID = "movie_id"
        const val SORT_BY = "sort_by"
        const val PAGE = "page"
    }

    @GET("/3/discover/movie")
    fun getDiscoveryMovies(
        @Query(SORT_BY) sortBy: String,
        @Query(PAGE) page: Int): Observable<ListResponse<DiscoveryDTO>>

    @GET("/3/movie/{movie_id}")
    fun getDetailMovies(@Path(MOVIE_ID) movie_id: Int): Observable<MovieDetailDTO>

    @GET("/3/movie/{movie_id}/images")
    fun getImagesMovie(@Path(MOVIE_ID) movie_id: Int): Observable<MovieImagesDTO>

    @GET("/3/movie/{movie_id}/reviews")
    fun getReviewsMovie(
        @Path(MOVIE_ID) movie_id: Int,
        @Query(PAGE) page: Int): Observable<ListResponse<MovieReviewDTO>>
}