package com.oskarrek.fridgemanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oskarrek.fridgemanager.R
import com.oskarrek.fridgemanager.models.Product
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProductsListAdapter(var products : ArrayList<Product> = ArrayList()) :
    RecyclerView.Adapter<ProductsListAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        // Inflating views.
        Picasso
            .get()
            .load(if(product.imageUri.isEmpty()) null else product.imageUri)
            .error(R.drawable.ic_launcher_background)
            .placeholder(R.color.colorDarkGray)
            .into(holder.imageView)

        holder.nameView.text = product.name
        holder.quantityView.text = product.quantity
        // Formatting date.
        val date = Date(product.expirationDate)
        val dateFormat = SimpleDateFormat("dd.MM.yy")
        holder.dateView.text = dateFormat.format(date)
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.product_image)
        val nameView: TextView = itemView.findViewById(R.id.product_name)
        val quantityView: TextView = itemView.findViewById(R.id.product_quantity)
        val dateView: TextView = itemView.findViewById(R.id.product_expiration_date)
    }
}