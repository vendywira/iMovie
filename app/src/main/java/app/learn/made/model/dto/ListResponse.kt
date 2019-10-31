package app.learn.made.model.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListResponse<out T : Parcelable> (
    val page: Int,
    val totalResults: Int,
    val totalPages: Int,
    @field:SerializedName("contents", alternate = ["results"])
    val contents: List<T>? = null
) : Parcelable {
    companion object {
        inline operator fun <reified T: Parcelable> invoke(): T = T::class.java.newInstance()
    }
}