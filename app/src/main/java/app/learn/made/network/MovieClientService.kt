package app.learn.made.network

import app.learn.made.model.dto.ListResponse
import app.learn.made.model.dto.MovieDetailDTO
import app.learn.made.model.dto.DiscoveryDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieClientService {

    companion object {
        const val MOVIE_ID = "movie_id"
        const val SORT_BY = "sort_by"
    }

    @GET("/3/discover/movie")
    fun getDiscoveryMovies(@Query(SORT_BY) sortBy: String): Observable<ListResponse<DiscoveryDTO>>

    @GET("/3/movie/{movie_id}")
    fun getDetailMovies(@Path(MOVIE_ID) movie_id: Int): Observable<MovieDetailDTO>

}