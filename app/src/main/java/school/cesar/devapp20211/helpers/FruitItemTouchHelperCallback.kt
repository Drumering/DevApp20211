package school.cesar.devapp20211.helpers

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import school.cesar.devapp20211.adapters.FruitsRecyclerViewAdapter

class FruitItemTouchHelperCallback (private val fruitsRecyclerViewAdapter: FruitsRecyclerViewAdapter) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val swipe = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(drag, swipe)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val initPosition = viewHolder.adapterPosition
        val targetPosition = target.adapterPosition
        fruitsRecyclerViewAdapter.swap(initPosition, targetPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        fruitsRecyclerViewAdapter.remove(position)
    }
}