package com.mobidal.pharmacynamoune.adapter

import android.content.Context
import com.mobidal.pharmacynamoune.adapter.CommandAdapter.OnCommandClickListener
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.OnProduct5ClickListener
import androidx.recyclerview.widget.RecyclerView
import com.mobidal.pharmacynamoune.adapter.CommandAdapter.CommandViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.mobidal.pharmacynamoune.R
import butterknife.BindView
import android.widget.TextView
import com.mobidal.pharmacynamoune.adapter.ProductAdapter
import butterknife.ButterKnife
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobidal.pharmacynamoune.helper.GridSpacingItemDecoration
import com.mobidal.pharmacynamoune.model.Command
import com.mobidal.pharmacynamoune.model.Product

class CommandAdapter(
    private val mContext: Context, private var mCommandList: List<Command>?,
    private val mOnCommandClickListener: OnCommandClickListener,
    private val mOnProductClickListener: OnProduct5ClickListener
) : RecyclerView.Adapter<CommandViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandViewHolder {
        val itemView = LayoutInflater.from(mContext)
            .inflate(R.layout.item_command, parent, false)
        return CommandViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommandViewHolder, position: Int) {
        holder.bind(mCommandList!![position])
    }

    override fun getItemCount(): Int {
        return if (mCommandList == null) 0 else mCommandList!!.size
    }

    fun setList(list: List<Command>?) {
        mCommandList = list
        notifyDataSetChanged()
    }

    inner class CommandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        OnProduct5ClickListener {
        @JvmField
        @BindView(R.id.tv_id)
        var mIdTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_date)
        var mDateTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_state)
        var mStateTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_sub_total)
        var mSubTotalTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_delivery)
        var mDeliveryTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_total)
        var mTotalTextView: TextView? = null

        @JvmField
        @BindView(R.id.rv_product)
        var mProductRecyclerView: RecyclerView? = null
        var mProductAdapter: ProductAdapter? = null

        init {

            // Bind views
            ButterKnife.bind(this, itemView)

            // Setup product list
            setupProductList()
        }

        private fun setupProductList() {
            val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
                false)
            mProductAdapter = ProductAdapter(mContext, null, ProductAdapter.TYPE_FIVE)
            mProductAdapter!!.setLoading(false)

            // Create {@link RecyclerView.ItemDecoration}
            val spacingInPixel = mContext.resources
                .getDimensionPixelSize(R.dimen.grid_secondary_category_spacing)
            val gridSpacingItemDecoration = GridSpacingItemDecoration(1, spacingInPixel, true)

            // Setup {link RecyclerView} for {@link Article}
            mProductRecyclerView!!.layoutManager = layoutManager
            mProductRecyclerView!!.adapter = mProductAdapter
            mProductRecyclerView!!.addItemDecoration(gridSpacingItemDecoration)
        }

        fun bind(command: Command) {
            mIdTextView!!.text = "Command #" + command.id
            mDateTextView!!.text = "2022-06-18"
            mStateTextView!!.text = "Command " + command.state
            mProductAdapter!!.setAll(command.productList)
            mSubTotalTextView!!.text = "$" + command.subTotalPrice
            mDeliveryTextView!!.text = "$" + command.deliveryPrice
            mTotalTextView!!.text = "$" + command.totalPrice
        }

        override fun onProductClicked(product: Product) {}
    }

    interface OnCommandClickListener {
        fun onCommandClicked(command: Command?)
    }
}