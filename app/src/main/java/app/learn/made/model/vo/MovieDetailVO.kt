package app.learn.made.model.vo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetailVO(
    val adult: Boolean?,

    @SerializedName("backdrop_path")
    var backdropPath: String?,
    var budget: Int?,
    var Genres: List<Genre>?,
    var homepage: String?,
    var id: Int?,

    @SerializedName("imdb_id")
    var imdbId: String?,

    @SerializedName("original_language")
    var originalLanguage: String?,

    @SerializedName("original_title")
    var originalTitle: String?,
    var overview: String?,
    var popularity: Double?,

    @SerializedName("poster_path")
    var posterPath: String?,

    @SerializedName("production_companies")
    var productionCompanies: List<ProductionCompany>?,

    @SerializedName("production_countries")
    var productionCountries: List<ProductionCountry>?,

    @SerializedName("release_date")
    var releaseDate: String?,
    var revenue: Int?,
    var runtime: Int?,

    @SerializedName("spoken_languages")
    var spokenLanguages: List<SpokenLanguage>?,
    var status: String?,
    var tagline: String?,
    var title: String?,
    var video: Boolean?,
    @SerializedName("vote_average")
    var vote_average: Double?,
    @SerializedName("vote_count")
    var vote_count: Int?
) : Parcelable {
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}

@Parcelize
data class Genre(
    var id: Int?,
    var name: String?
) : Parcelable {
    constructor() : this(null, null)
}

@Parcelize
data class ProductionCountry(
    @SerializedName("iso_3166_1")
    var iso: String?,
    var name: String?
) : Parcelable {
    constructor() : this(null, null)
}

@Parcelize
data class ProductionCompany(
    var id: Int?,

    @SerializedName("logo_path")
    var logoPath: String?,
    var name: String?,

    @SerializedName("origin_country")
    var originCountry: String?
) : Parcelable {
    constructor() : this(null, null, null, null)
}

@Parcelize
data class SpokenLanguage(
    @SerializedName("iso_639_1")
    var iso: String?,
    var name: String?
) : Parcelable {
    constructor() : this(null, null)
}