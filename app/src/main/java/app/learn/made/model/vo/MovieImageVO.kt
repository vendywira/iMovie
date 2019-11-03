package app.learn.made.model.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieImageVO(
    var filePath: String?,
    var height: Int?,
    var width: Int?
): Parcelable {
    constructor() : this(null, null, null)
}