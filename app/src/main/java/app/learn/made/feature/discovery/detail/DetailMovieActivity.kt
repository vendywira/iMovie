package app.learn.made.feature.discovery.detail

import android.R
import android.util.Log
import android.view.MenuItem
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.learn.made.BuildConfig
import app.learn.made.base.impl.BaseActivity
import app.learn.made.helper.loadImageUrl
import app.learn.made.helper.mapper
import app.learn.made.model.constant.Constant
import app.learn.made.model.dto.MovieDetailDTO
import app.learn.made.model.dto.MovieImagesDTO
import app.learn.made.model.vo.DiscoveryVO
import app.learn.made.model.vo.MovieImageVO
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.synnapps.carouselview.CarouselView
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.progress_bar.*
import me.grantland.widget.AutofitTextView
import org.jetbrains.anko.design.snackbar
import org.koin.android.ext.android.inject


class DetailMovieActivity : BaseActivity<DetailMovieService.Presenter>(), DetailMovieService.View {

    private val presenter: DetailMoviePresenter by inject()
    private lateinit var progressBar: LottieAnimationView
    private lateinit var carouselBackdrop: CarouselView
    private lateinit var discovery: DiscoveryVO
    private lateinit var movieTitle: AutofitTextView
    private lateinit var movieReleaseDate: AutofitTextView
    private var listOfBackdrops = mutableListOf<MovieImageVO>()


    override fun getProgressBar(): LottieAnimationView? = progressBar

    override fun getSwipeRefresh(): SwipeRefreshLayout? = null

    override fun getPresenter(): DetailMovieService.Presenter? = presenter

    override fun onInitView() {
        presenter.setupView(this)
        setContentView(app.learn.made.R.layout.activity_detail_movie)
        progressBar = movie_progress
        discovery = intent.getParcelableExtra(Constant.DISCOVERY_INTENT)
        discovery.id?.let {
            presenter.getDetailMovie(it)
            presenter.getImagesMovie(it)
        }
        app_bar_movie_detail.addOnOffsetChangedListener(titleShownOnCollapse(collapsing_toolbar))

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun titleShownOnCollapse(collapsingToolbarLayout: CollapsingToolbarLayout):
            AppBarLayout.OnOffsetChangedListener {

        return object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.title = discovery.title
                    isShow = true
                } else if (isShow) {
                    collapsingToolbarLayout.title = " "
                    isShow = false
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun showMovieDetail(discoveryResponse: MovieDetailDTO) {

    }

    override fun showImagesCarousel(images: MovieImagesDTO) {
        listOfBackdrops.clear()
        images.let { it ->
            it.backdrops.forEach {
                val image = mapper.map(it, MovieImageVO::class.java)
                if (listOfBackdrops.size < Constant.MAX_CAROUSEL_LENGTH) listOfBackdrops.add(image)
            }
        }
        Log.d("list of images", listOfBackdrops.toString())
        carouselBackdrop = carousel_backdrop
        carouselBackdrop.setImageListener { position, imageView ->
            imageView.loadImageUrl(BuildConfig.API_POSTER_PATH.plus(listOfBackdrops[position].filePath))
        }
        carouselBackdrop.pageCount = listOfBackdrops.size
    }
}
