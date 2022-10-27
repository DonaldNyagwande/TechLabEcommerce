package com.mobidal.pharmacynamoune.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.mobidal.pharmacynamoune.R
import com.mobidal.pharmacynamoune.adapter.SearchAdapter.SearchViewHolder
import com.mobidal.pharmacynamoune.model.Search

class SearchAdapter(
    private val mContext: Context,
    private var mSearchList: List<Search>?,
    private val mOnSearchClickListener: OnSearchClickListener?
) : RecyclerView.Adapter<SearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView = LayoutInflater
            .from(mContext)
            .inflate(R.layout.item_search, parent, false)
        return SearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(mSearchList!![position])
    }

    override fun getItemCount(): Int {
        return if (mSearchList == null) 0 else mSearchList!!.size
    }

    fun setList(list: List<Search>?) {
        mSearchList = list
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @JvmField
        @BindView(R.id.tv_search)
        var mSearchTextView: TextView? = null

        init {

            // Bind views
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(View.OnClickListener {
                mOnSearchClickListener?.onSearchClicked(mSearchList!!.get(
                    adapterPosition))
            })
        }

        fun bind(search: Search) {
            mSearchTextView!!.text = search.value
        }
    }

    interface OnSearchClickListener {
        fun onSearchClicked(search: Search?)
    }
}