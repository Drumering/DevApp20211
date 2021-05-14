package school.cesar.devapp20211.adapters

import android.content.Context
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import school.cesar.devapp20211.R
import school.cesar.devapp20211.databinding.RowFruitBinding
import school.cesar.devapp20211.models.Fruit

class FruitsRecyclerViewAdapter (private val context: Context, private val fruits: MutableList<Fruit>, private val callback: (Fruit) -> Unit) : RecyclerView.Adapter<FruitsRecyclerViewAdapter.ViewHolder> (){

    private val fruitsImages : TypedArray by lazy {
        context.resources.obtainTypedArray(R.array.fruits)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_fruit, parent, false)
        val binding = RowFruitBinding.bind(view)
        val viewHolder = ViewHolder(binding)

        viewHolder.itemView.setOnClickListener {
            val fruit = fruits[viewHolder.adapterPosition]
            callback(fruit)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, description, image) = fruits[position]
        holder.imgvFruit.setImageDrawable(fruitsImages.getDrawable(image))
        holder.tvFruitName.text = name
        holder.tvBenefitsDescription.text = description
    }

    override fun getItemCount(): Int = fruits.size

    class ViewHolder (view: RowFruitBinding) : RecyclerView.ViewHolder(view.root) {
        val imgvFruit : ImageView = view.imgvFruit
        val tvFruitName : TextView = view.tvFruitName
        val tvBenefitsDescription : TextView = view.tvBenefitsDescription
    }
}