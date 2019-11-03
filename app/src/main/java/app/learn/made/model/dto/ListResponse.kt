package app.learn.made.model.dto

import com.google.gson.annotations.SerializedName

data class ListResponse<out T> (
    val page: Int,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("contents", alternate = ["results"])
    val contents: List<T>? = null
) {
    companion object {
        inline operator fun <reified T> invoke(): T = T::class.java.newInstance()
    }
}