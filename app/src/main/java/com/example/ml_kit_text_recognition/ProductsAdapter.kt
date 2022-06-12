package com.example.ml_kit_text_recognition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductsAdapter(val productList: List<Products>) :
    RecyclerView.Adapter<ProductsAdapter.ProductVH>(){
    class ProductVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var codeNameTxt : TextView = itemView.findViewById(R.id.code_name)
        var scrollViewTxt : ScrollView = itemView.findViewById(R.id.scrollViewProduct)
        var scrollInside : LinearLayout = itemView.findViewById(R.id.scrollInside)
        var linearLayout : LinearLayout = itemView.findViewById(R.id.linearLayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        val view : View = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false)

        return ProductVH(view)
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        val products : Products = productList[position]
        holder.codeNameTxt.text = products.codeName

        val isexpandable : Boolean = productList[position].expandable
        holder.scrollViewTxt.visibility = if (isexpandable) View.VISIBLE else View.GONE

        holder.linearLayout.setOnClickListener {
            val products = productList[position]
            products.expandable = !products.expandable
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}