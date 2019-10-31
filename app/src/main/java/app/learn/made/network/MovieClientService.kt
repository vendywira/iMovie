package app.learn.made.network

import app.learn.made.model.dto.ListResponse
import app.learn.made.model.dto.MovieDetailDTO
import app.learn.made.model.dto.RecoveryDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieClientService {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String): Observable<ListResponse<RecoveryDTO>>

    @GET("movie/{movie_id}")
    fun getDetailMovies(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String): Observable<MovieDetailDTO>

}