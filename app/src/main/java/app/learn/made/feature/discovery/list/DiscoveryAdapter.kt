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
import app.learn.made.base.impl.BaseViewHolder
import app.learn.made.helper.dateFormatting
import app.learn.made.helper.loadImageUrl
import app.learn.made.model.constant.Constant
import app.learn.made.model.vo.DiscoveryVO
import org.jetbrains.anko.find

class DiscoveryAdapter(
    private var listOfMovies: List<DiscoveryVO>,
    private var listViewType: List<Int>,
    private val onClick: (position: Int) -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        val ITEM_VIEW_TYPE_CONTENT = 1
        val ITEM_VIEW_TYPE_LOADING = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_VIEW_TYPE_CONTENT -> ViewHolderContent(
                layoutInflater.inflate(R.layout.item_list_discovery, null)
            )
            else -> ViewHolderLoading(
                layoutInflater.inflate(R.layout.empty_resource, null)
            )
        }
    }

    override fun getItemCount(): Int = listOfMovies.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val viewType = listViewType[position]
        val data = listOfMovies[position]
        when (viewType) {
            ITEM_VIEW_TYPE_CONTENT -> {
                val vh = holder as ViewHolderContent
                vh.bindItem(data)
                vh.itemView.setOnClickListener {
                    onClick(position)
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int = listViewType[position]

    fun refresh(listOfMovies: List<DiscoveryVO>, listViewType: List<Int>) {
        this.listOfMovies = listOfMovies
        this.listViewType = listViewType
        notifyDataSetChanged()
    }

    inner class ViewHolderContent(view: View) : BaseViewHolder(view) {

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

        override fun clear() {}

    }

    inner class ViewHolderLoading(view: View) : BaseViewHolder(view) {
        override fun clear() {}
    }
}