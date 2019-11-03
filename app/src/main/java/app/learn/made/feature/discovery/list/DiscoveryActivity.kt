package app.learn.made.feature.discovery.list

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.learn.made.base.impl.BaseActivity
import app.learn.made.feature.discovery.detail.DetailMovieActivity
import app.learn.made.helper.mapper
import app.learn.made.model.constant.Constant
import app.learn.made.model.dto.DiscoveryDTO
import app.learn.made.model.dto.ListResponse
import app.learn.made.model.vo.DiscoveryVO
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_discovery.*
import kotlinx.android.synthetic.main.progress_bar.*
import kotlinx.android.synthetic.main.recycle_view.*
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject
import kotlin.properties.Delegates


class DiscoveryActivity : BaseActivity<DiscoveryService.Presenter>(), DiscoveryService.View {

    private val presenter: DiscoveryPresenter by inject()
    private lateinit var progressBar: LottieAnimationView
    private lateinit var rv: RecyclerView
    private lateinit var sr: SwipeRefreshLayout
    private lateinit var sortBy: Spinner
    private lateinit var discoveryAdapter: DiscoveryAdapter
    private var listOfMovies = mutableListOf<DiscoveryVO>()
    private var page = 1
    private var totalPage : Int? = 1
    private lateinit var listViewType: MutableList<Int>
    private var isLoading by Delegates.notNull<Boolean>()

    override fun getProgressBar(): LottieAnimationView? = progressBar

    override fun getSwipeRefresh(): SwipeRefreshLayout? = sr

    override fun getPresenter(): DiscoveryService.Presenter? = presenter

    override fun showDiscoveryList(discoveryResponse: ListResponse<DiscoveryDTO>?) {
        totalPage = discoveryResponse?.totalPages
        val lenTemp = listOfMovies.size - 1
        discoveryResponse?.let { it ->
            it.contents?.forEach {
                try {
                    val discovery = mapper.map(it, DiscoveryVO::class.java)
                    listOfMovies.add(discovery)
                    listViewType.add(DiscoveryAdapter.ITEM_VIEW_TYPE_CONTENT)
                } catch (e: Exception){}
            }
        }

        if (isLoading) {
            listOfMovies.removeAt(lenTemp)
            listViewType.removeAt(lenTemp)


            if (page < totalPage ?: 0) {
                listOfMovies.add(DiscoveryVO())
                listViewType.add(DiscoveryAdapter.ITEM_VIEW_TYPE_LOADING)
            }
        }
        discoveryAdapter.refresh(listOfMovies, listViewType)
        isLoading = false
        Log.d("list of discovery", listOfMovies.size.toString())
        Log.d("discovery movies", listOfMovies.toString())
        discoveryAdapter.notifyDataSetChanged()
    }

    override fun onInitView() {
        presenter.setupView(this)
        setContentView(app.learn.made.R.layout.activity_discovery)
        isLoading = false
        listViewType = ArrayList()
        rv = rv_movie
        sr = sr_movie
        progressBar = movie_progress
        sortBy = spinner_sort_by
        setSpinner()
        val layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
        sr.setOnRefreshListener {
            listOfMovies.clear()
            page = 1
            presenter.getDiscoveryList(Constant.SORT_BY_IDS[sortBy.selectedItemPosition], page)
        }
        discoveryAdapter = DiscoveryAdapter(listOfMovies, listViewType) { position ->
            this.startActivity<DetailMovieActivity>(Constant.DISCOVERY_INTENT to listOfMovies[position])
        }
        rv.adapter = discoveryAdapter
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val countItem = layoutManager.itemCount
                val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val isLastPosition = countItem.minus(1) == lastVisiblePosition
                if (!isLoading && isLastPosition && page <= totalPage?:1) {
                    isLoading = true
                    presenter.getDiscoveryList(Constant.SORT_BY_IDS[sortBy.selectedItemPosition], page++)
//                    doAsync {
//                        val lenTemp = listOfMovies.size - 1
//                        repeat(10) { a ->
//                            val lastValue: Int = when (a) {
//                                0 -> {
//                                    listData[listData.size - 2].toInt()
//                                }
//                                else -> {
//                                    listData[listData.size - 1].toInt()
//                                }
//                            }
//                            listOfMovies.add(lastValue.plus(1).toString())
//                            listViewType.add(DiscoveryAdapter.ITEM_VIEW_TYPE_CONTENT)
//                        }
//                        Thread.sleep(1000 * 10)
//                        uiThread {
//                            listOfMovies.removeAt(lenTemp)
//                            listViewType.removeAt(lenTemp)
//
//                            if (countLoadMore + 1 < 3) {
//                                listOfMovies.add(DiscoveryVO())
//                                listViewType.add(DiscoveryAdapter.ITEM_VIEW_TYPE_LOADING)
//                            }
//                            discoveryAdapter.refresh(listOfMovies, listViewType)
//                            countLoadMore += 1
//                            isLoading = false
//                        }
//                    }
                }
            }
        })

    }

    private fun setSpinner() {
        val spAdapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item,
            Constant.SORT_BY_VALUES
        )
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortBy.adapter = spAdapter
        sortBy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                listOfMovies.clear()
                page = 1
                presenter.getDiscoveryList(Constant.SORT_BY_IDS[position], page)
            }

        }
    }
}
