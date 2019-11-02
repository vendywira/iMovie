package app.learn.made.feature.discovery.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.learn.made.BuildConfig
import app.learn.made.R
import app.learn.made.helper.dateFormatting
import app.learn.made.helper.loadImageUrl
import app.learn.made.helper.timeFormating
import app.learn.made.model.constant.Constant
import app.learn.made.model.vo.DiscoveryVO
import org.jetbrains.anko.find

class DiscoveryAdapter(
    private val listOfMovies: List<DiscoveryVO>,
    private val onClick: (position: Int) -> Unit
) : RecyclerView.Adapter<DiscoveryAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_discovery, parent, false)
        )
    }

    override fun getItemCount(): Int = listOfMovies.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindItem(listOfMovies[position])
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }


    class Holder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle: TextView = view.find(R.id.movie_title)
        private val tvReleaseDate: TextView = view.find(R.id.movie_release)
        private val tvOverview: TextView = view.find(R.id.movie_overview)
        private val tvRating: TextView = view.find(R.id.movie_rating)
        private val imgMovie: ImageView = view.find(R.id.movie_image)
        private val progressBar: ProgressBar = view.find(R.id.circular_progress_bar)

        fun bindItem(movie: DiscoveryVO) {
            tvTitle.text = movie.title
            tvReleaseDate.text = dateFormatting(movie.releaseDate.orEmpty())
            tvOverview.text = movie.overview?.let {
                if (it.length > Constant.MAX_WORD_CHAR)
                    it.substring(0, Constant.MAX_WORD_CHAR).plus(". . .")
                else it
            }
            tvRating.text = movie.voteAverage.toString()
            progressBar.progress = movie.voteAverage?.times(10)?.toInt() ?: 0
            movie.posterPath?.let {
                imgMovie.loadImageUrl(BuildConfig.API_POSTER_PATH.plus(it))
            }

        }
    }

}