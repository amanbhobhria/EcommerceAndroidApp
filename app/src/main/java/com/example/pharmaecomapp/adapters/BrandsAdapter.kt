package com.example.pharmaecomapp.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.pharmaecomapp.R
import com.example.pharmaecomapp.databinding.BrandsItemBinding
import com.example.pharmaecomapp.models.Brands


class BrandsAdapter(val seeAllBtnClicked: (Brands) -> Unit) :
    RecyclerView.Adapter<BrandsAdapter.BrandsAdapterViewHolder>() {
    class BrandsAdapterViewHolder(val binding: BrandsItemBinding) :
        ViewHolder(binding.root)

    val diffUtil = object : DiffUtil.ItemCallback<Brands>() {
        override fun areItemsTheSame(oldItem: Brands, newItem: Brands): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Brands, newItem: Brands): Boolean {
            return oldItem == newItem
        }

    }


    val differ = AsyncListDiffer(this, diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandsAdapterViewHolder {
        return BrandsAdapterViewHolder(
            BrandsItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: BrandsAdapterViewHolder, position: Int) {
        val productType = differ.currentList[position]
        holder.binding.apply {

//            tvProduxtType.text=productType.productType
            tvBrandName.text = productType.name

            ivBrandImage.setImageDrawable(
                ivBrandImage.context.getResources().getDrawable(R.drawable.brands_img, null)
            )


            // Load brand image using Glide or any other image loading library
//            Glide.with(ivBrandImage.context)
//                .load(productType.imageUrl)
//                .into(ivBrandImage)


        }

        holder.itemView.setOnClickListener {
            seeAllBtnClicked(productType)
        }
    }
}