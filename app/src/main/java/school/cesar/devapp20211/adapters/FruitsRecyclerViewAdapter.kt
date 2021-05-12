package school.cesar.devapp20211.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import school.cesar.devapp20211.models.Fruit

class FruitsRecyclerViewAdapter (private val context: Context, private val fruits: MutableList<Fruit>, private val callback: (Fruit) -> Unit) : RecyclerView.Adapter<FruitsRecyclerViewAdapter.ViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    }
}