package school.cesar.devapp20211.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import school.cesar.devapp20211.R
import school.cesar.devapp20211.activities.MainActivity
import school.cesar.devapp20211.databinding.RowFruitBinding
import school.cesar.devapp20211.models.Fruit
import java.lang.NumberFormatException
import java.util.*

class FruitsRecyclerViewAdapter (private val context: Context, private val fruits: MutableList<Fruit>, private val callback: (Fruit) -> Unit) : RecyclerView.Adapter<FruitsRecyclerViewAdapter.ViewHolder> (){

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

        try {
            holder.imgvFruit.setImageDrawable(MainActivity.fruitsImages?.getDrawable(image.toInt()))
        } catch (e: NumberFormatException) {
            Picasso.get().load(image).into(holder.imgvFruit)
        }

        holder.tvFruitName.text = name
        holder.tvBenefitsDescription.text = description
    }

    override fun getItemCount(): Int = fruits.size
    fun swap(initPosition: Int, targetPosition: Int) {
        Collections.swap(fruits, initPosition, targetPosition)
        notifyItemMoved(initPosition, targetPosition)
    }

    fun remove(position: Int) {
        fruits.removeAt(position)
        notifyItemRemoved(position)
    }

    class ViewHolder (view: RowFruitBinding) : RecyclerView.ViewHolder(view.root) {
        val imgvFruit : ImageView = view.imgvFruit
        val tvFruitName : TextView = view.tvFruitName
        val tvBenefitsDescription : TextView = view.tvBenefitsDescription
    }
}