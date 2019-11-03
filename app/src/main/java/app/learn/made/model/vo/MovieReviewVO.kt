package app.learn.made.model.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieReviewVO(
    var author: String?,
    var content: String?,
    var id: String?,
    var url: String?
): Parcelable
{
    constructor() : this(null, null, null, null)
}