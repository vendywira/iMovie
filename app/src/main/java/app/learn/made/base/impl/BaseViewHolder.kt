package app.learn.made.base.impl

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var currentPosition = 0

    protected abstract fun clear()

    open fun onBind(position: Int) {
        this.currentPosition = position
        clear()
    }

    fun getCurrentPosition() = this.currentPosition
}