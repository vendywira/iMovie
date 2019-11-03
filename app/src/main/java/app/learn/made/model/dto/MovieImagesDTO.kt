package app.learn.made.model.dto

import com.google.gson.annotations.SerializedName

data class MovieImagesDTO(
    val backdrops: List<Image>,
    val id: Int,
    val posters: List<Image>
)

data class Image(
    @SerializedName("aspect_ratio")
    val aspectRatio: Double,

    @SerializedName("file_path")
    val filePath: String,

    val height: Int,

    @SerializedName("iso_639_1")
    val iso: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")

    val voteCount: Int,

    val width: Int
)