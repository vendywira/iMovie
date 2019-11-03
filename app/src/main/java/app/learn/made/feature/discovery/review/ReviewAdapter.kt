package app.learn.made.feature.discovery.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.learn.made.R
import app.learn.made.base.impl.BaseViewHolder
import app.learn.made.model.vo.MovieReviewVO
import org.jetbrains.anko.find

class ReviewAdapter(
    private var listOfMovies: List<MovieReviewVO>,
    private var listViewType: List<Int>
) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        val ITEM_VIEW_TYPE_CONTENT = 1
        val ITEM_VIEW_TYPE_LOADING = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_VIEW_TYPE_CONTENT -> ViewHolderContent(
                layoutInflater.inflate(R.layout.item_list_review, null)
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
            }
        }

    }

    override fun getItemViewType(position: Int): Int = listViewType[position]

    fun refresh(listOfMovies: List<MovieReviewVO>, listViewType: List<Int>) {
        this.listOfMovies = listOfMovies
        this.listViewType = listViewType
        notifyDataSetChanged()
    }

    inner class ViewHolderContent(view: View) : BaseViewHolder(view) {

        private val tvAuthor: TextView = view.find(R.id.review_author)
        private val tvreviewContent: TextView = view.find(R.id.review_content)

        fun bindItem(movie: MovieReviewVO) {
            tvAuthor.text = movie.author
            tvreviewContent.text = movie.content
        }

        override fun clear() {}

    }

    inner class ViewHolderLoading(view: View) : BaseViewHolder(view) {
        override fun clear() {}
    }
}