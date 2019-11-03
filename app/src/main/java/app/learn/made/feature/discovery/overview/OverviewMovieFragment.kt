package app.learn.made.feature.discovery.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.learn.made.R
import app.learn.made.model.constant.Constant
import kotlinx.android.synthetic.main.fragment_overview_moview.view.*

class OverviewMovieFragment : Fragment() {

    lateinit var content: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content = arguments?.getString(Constant.MOVIE_OVERVIEW_INTENT).toString()
        Log.d("content", content)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_overview_moview, container, false)
        view.movie_overview.text = content
        return view
    }
}