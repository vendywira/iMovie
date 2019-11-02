package app.learn.made.feature.discovery.list

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.learn.made.R
import app.learn.made.base.impl.BaseActivity
import app.learn.made.helper.mapper
import app.learn.made.model.constant.Constant
import app.learn.made.model.dto.DiscoveryDTO
import app.learn.made.model.dto.ListResponse
import app.learn.made.model.vo.DiscoveryVO
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_discovery.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.koin.android.ext.android.inject

class DiscoveryActivity : BaseActivity<DiscoveryListService.Presenter>(), DiscoveryListService.View {

    private val presenter: DiscoveryPresenter by inject()
    private lateinit var progressBar: LottieAnimationView
    private lateinit var rv: RecyclerView
    private lateinit var sr: SwipeRefreshLayout
    private lateinit var sortBy: Spinner
    private lateinit var discoveryAdapter: DiscoveryAdapter
    private var listOfMovies = mutableListOf<DiscoveryVO>()

    override fun getProgressBar(): LottieAnimationView? = progressBar

    override fun showDiscoveryList(discoveryResponse: ListResponse<DiscoveryDTO>?) {
        listOfMovies.clear()
        discoveryResponse?.let { it ->
            it.contents?.forEach {
                val discovery = mapper.map(it, DiscoveryVO::class.java)
                listOfMovies.add(discovery)
            }
        }
        Log.d("list of discovery", listOfMovies.size.toString())
        Log.d("discovery movies", listOfMovies.toString())
        discoveryAdapter.notifyDataSetChanged()
    }

    override fun onInitView() {
        presenter.setupView(this)
        setContentView(R.layout.activity_discovery)
        rv = rv_movie
        sr = sr_movie
        progressBar = movie_progress
        sortBy = spinner_sort_by
        setSpinner()
        rv.layoutManager = LinearLayoutManager(this)
        sr.setOnRefreshListener {
            presenter.getDiscoveryList(Constant.SORT_BY_IDS[sortBy.selectedItemPosition])
        }
        discoveryAdapter = DiscoveryAdapter(listOfMovies) { position ->
            // this.startActivity<MatchDetailActivity>(Constant.MATCH_EVENT_ID to listOfMatch[position].eventId)
            println(position)
        }
        rv.adapter = discoveryAdapter

    }

    fun setSpinner() {
        val spAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
            Constant.SORT_BY_VALUES)
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortBy.adapter = spAdapter
        sortBy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.getDiscoveryList(Constant.SORT_BY_IDS[position])
            }

        }
    }

    override fun getPresenter(): DiscoveryListService.Presenter? = presenter
}
