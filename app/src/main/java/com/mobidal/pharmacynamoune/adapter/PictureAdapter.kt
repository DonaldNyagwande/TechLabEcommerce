package com.mobidal.pharmacynamoune.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobidal.pharmacynamoune.R;
import com.mobidal.pharmacynamoune.model.Picture;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {

    private Context mContext;
    private List<Picture> mPictureList;
    private OnPictureClickListener mOnPictureClickListener;

    public PictureAdapter(Context mContext, List<Picture> mPictureList,
                          OnPictureClickListener mOnPictureClickListener) {
        this.mContext = mContext;
        this.mPictureList = mPictureList;
        this.mOnPictureClickListener = mOnPictureClickListener;
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create item view for RecyclerView
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_picture, parent, false);

        return new PictureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        holder.bind(mPictureList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPictureList == null ? 0 : mPictureList.size();
    }

    public void setList(List<Picture> list) {
        mPictureList = list;
        notifyDataSetChanged();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_picture)
        ImageView mPictureImageView;

        public PictureViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind TopArticle views
            ButterKnife.bind(this, itemView);

            // Setup OnClickListener for TopArticle
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnPictureClickListener != null) {
                // Get selected item position
                int picturePosition = getAdapterPosition();
                // Get selected top article
                Picture picture = mPictureList.get(picturePosition);
                // Callback onTopArticleClicked
                mOnPictureClickListener.onPictureClicked(picture);
            }
        }

        public void bind(Picture picture) {
            // TODO 1 get image using picasso
            Picasso.get()
                    .load(picture.getPictureUrl())
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(mPictureImageView);
        }
    }

    public interface OnPictureClickListener {
        void onPictureClicked(Picture picture);
    }

}
