package app.learn.made.feature.discovery.review

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.learn.made.R
import app.learn.made.base.impl.BaseFragment
import app.learn.made.feature.discovery.list.DiscoveryAdapter
import app.learn.made.feature.discovery.list.DiscoveryService
import app.learn.made.helper.mapper
import app.learn.made.model.constant.Constant
import app.learn.made.model.dto.ListResponse
import app.learn.made.model.dto.MovieReviewDTO
import app.learn.made.model.vo.MovieReviewVO
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.progress_bar.view.*
import kotlinx.android.synthetic.main.recycle_view.view.*
import org.jetbrains.anko.support.v4.ctx
import org.koin.android.ext.android.inject
import kotlin.properties.Delegates

class ReviewFragment : BaseFragment<ReviewService.Presenter>(), ReviewService.View {

    private val presenter : ReviewPresenter by inject()
    lateinit var view: DiscoveryService.View
    private lateinit var progressBar: LottieAnimationView
    private lateinit var rv: RecyclerView
    private lateinit var sr: SwipeRefreshLayout
    private lateinit var adapter: ReviewAdapter
    private var movieId: Int = 0
    private var listOfReview = mutableListOf<MovieReviewVO>()
    private var page = 1
    private var totalPage : Int? = 1
    private lateinit var listViewType: MutableList<Int>
    private var isLoading by Delegates.notNull<Boolean>()

    override fun getPresenter(): ReviewService.Presenter = presenter

    override fun getProgressBar(): LottieAnimationView? = progressBar

    override fun getSwipeRefresh(): SwipeRefreshLayout? = sr


    override fun onInitView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        presenter.setupView(this)
        val view = layoutInflater.inflate(R.layout.fragment_review_movie, container, false)
        isLoading = false
        listViewType = mutableListOf()
        rv = view.rv_movie
        sr = view.sr_movie
        movieId = this.arguments?.getInt(Constant.MOVIE_ID_INTENT)?:0
        progressBar = view.movie_progress
        val layoutManager = LinearLayoutManager(ctx)
        rv.layoutManager = layoutManager
        presenter.getReviewList(movieId, page)
        sr.setOnRefreshListener {
            listOfReview.clear()
            page = 1
            presenter.getReviewList(movieId, page)
        }
        adapter = ReviewAdapter(listOfReview, listViewType)
        rv.adapter = adapter
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val countItem = layoutManager.itemCount
                val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val isLastPosition = countItem.minus(1) == lastVisiblePosition
                if (!isLoading && isLastPosition && page <= totalPage?:1) {
                    isLoading = true
                    presenter.getReviewList(movieId, page++)
                }
            }
        })
        return view
    }

    override fun showReviewList(reviewResponse: ListResponse<MovieReviewDTO>?) {
        totalPage = reviewResponse?.totalPages
        val lenTemp = listOfReview.size - 1
        reviewResponse?.let { it ->
            it.contents?.forEach {
                try {
                    val review = mapper.map(it, MovieReviewVO::class.java)
                    listOfReview.add(review)
                    listViewType.add(DiscoveryAdapter.ITEM_VIEW_TYPE_CONTENT)
                } catch (e: Exception){}
            }
        }

        if (isLoading) {
            listOfReview.removeAt(lenTemp)
            listViewType.removeAt(lenTemp)


            if (page < totalPage ?: 0) {
                listOfReview.add(MovieReviewVO())
                listViewType.add(ReviewAdapter.ITEM_VIEW_TYPE_LOADING)
            }
        }
        adapter.refresh(listOfReview, listViewType)
        isLoading = false
        Log.d("list of discovery", listOfReview.size.toString())
        Log.d("discovery movies", listOfReview.toString())
        adapter.notifyDataSetChanged()
    }
}
