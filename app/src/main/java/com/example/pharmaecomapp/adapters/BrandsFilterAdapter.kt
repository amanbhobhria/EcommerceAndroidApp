package com.example.pharmaecomapp.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.pharmaecomapp.databinding.ListItemBrandBinding
import com.example.pharmaecomapp.models.Brands


class BrandFilterAdapter(private val context: Context, private val itemList: List<Brands>) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val checkedItems = BooleanArray(itemList.size)

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ListItemBrandBinding = if (convertView == null) {
            ListItemBrandBinding.inflate(inflater, parent, false)
        } else {
            ListItemBrandBinding.bind(convertView)
        }

        val brand = itemList[position]
        binding.tvBrandName.text = brand.name
        binding.checkBoxBrand.isChecked = checkedItems[position]

        binding.root.setOnClickListener {
            binding.checkBoxBrand.isChecked = !binding.checkBoxBrand.isChecked
            checkedItems[position] = binding.checkBoxBrand.isChecked
        }

        return binding.root
    }

    fun getSelectedBrands(): List<Brands> {
        return itemList.filterIndexed { index, _ -> checkedItems[index] }
    }
}

