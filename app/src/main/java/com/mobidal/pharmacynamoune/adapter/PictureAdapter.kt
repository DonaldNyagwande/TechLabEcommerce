package com.mobidal.pharmacynamoune.adapter

import android.content.Context
import com.mobidal.pharmacynamoune.adapter.PictureAdapter.OnPictureClickListener
import androidx.recyclerview.widget.RecyclerView
import com.mobidal.pharmacynamoune.adapter.PictureAdapter.PictureViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.mobidal.pharmacynamoune.R
import butterknife.BindView
import butterknife.ButterKnife
import com.mobidal.pharmacynamoune.model.Picture
import com.squareup.picasso.Picasso

class PictureAdapter(
    private val mContext: Context, private var mPictureList: List<Picture>?,
    private val mOnPictureClickListener: OnPictureClickListener?
) : RecyclerView.Adapter<PictureViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        // Create item view for RecyclerView
        val itemView = LayoutInflater.from(mContext)
            .inflate(R.layout.item_picture, parent, false)
        return PictureViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(mPictureList!![position])
    }

    override fun getItemCount(): Int {
        return if (mPictureList == null) 0 else mPictureList!!.size
    }

    fun setList(list: List<Picture>?) {
        mPictureList = list
        notifyDataSetChanged()
    }

    inner class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        @JvmField
        @BindView(R.id.iv_picture)
        var mPictureImageView: ImageView? = null

        init {

            // Bind TopArticle views
            ButterKnife.bind(this, itemView)

            // Setup OnClickListener for TopArticle
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mOnPictureClickListener != null) {
                // Get selected item position
                val picturePosition = adapterPosition
                // Get selected top article
                val picture = mPictureList!![picturePosition]
                // Callback onTopArticleClicked
                mOnPictureClickListener.onPictureClicked(picture)
            }
        }

        fun bind(picture: Picture) {
            // TODO 1 get image using picasso
            Picasso.get()
                .load(picture.pictureUrl)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(mPictureImageView)
        }
    }

    interface OnPictureClickListener {
        fun onPictureClicked(picture: Picture?)
    }
}