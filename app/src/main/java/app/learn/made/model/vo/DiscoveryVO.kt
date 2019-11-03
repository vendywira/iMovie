package app.learn.made.model.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiscoveryVO (
    var id: Int?,
    var overview: String?,
    var posterPath: String?,
    var releaseDate: String?,
    var title: String?,
    var voteAverage: Double?
) : Parcelable {
    constructor() : this(null, null, null, null, null, null)
}