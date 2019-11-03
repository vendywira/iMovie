package app.learn.made.model.constant

class Constant {
    companion object {
        const val DISCOVERY_INTENT = "discovery_intent"
        const val MAX_CAROUSEL_LENGTH = 5
        const val MAX_WORD_CHAR = 200
        const val LANGUAGE = "en-US"
        val SORT_BY_IDS = listOf("popularity.asc", "popularity.desc", "release_date.asc",
            "release_date.desc", "revenue.asc", "revenue.desc", "primary_release_date.asc", "primary_release_date.desc",
            "original_title.asc", "original_title.desc", "vote_average.asc", "vote_average.desc", "vote_count.asc",
            "vote_count.desc")
        val SORT_BY_VALUES = listOf("Popularity Ascending", "Popularity Descending",
            "Release Date Ascending", "Release Date Descending", "Revenue Ascending", "Revenue desc",
            "Primary Release Date Ascending", "Primary Release Date Descending",
            "Title (A-Z)", "Title (Z-A)", "Rating Ascending", "Rating Descending", "Vote Ascending",
            "Vote Descending")
    }
}