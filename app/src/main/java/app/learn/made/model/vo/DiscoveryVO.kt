package app.learn.made.model.vo

data class DiscoveryVO (
    var id: Int?,
    var overview: String?,
    var posterPath: String?,
    var releaseDate: String?,
    var title: String?,
    var voteAverage: Double?
) {
    constructor() : this(null, null, null, null, null, null)
}