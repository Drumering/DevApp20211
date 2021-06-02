package school.cesar.devapp20211.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import school.cesar.devapp20211.R
import school.cesar.devapp20211.adapters.FruitsRecyclerViewAdapter
import school.cesar.devapp20211.databinding.ActivityMainBinding
import school.cesar.devapp20211.helpers.FruitItemTouchHelperCallback
import school.cesar.devapp20211.models.Fruit

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val fruitAdapter by lazy {
        FruitsRecyclerViewAdapter(this, fruits, this::onItemClickListener, this::confirmRemoveFruit)
    }

    companion object {
        var fruitsImages: TypedArray? = null
        const val REQUEST_CODE = 1
        const val REQUEST_DESCRIPTION_FRUIT = 2
        const val EXTRA_FRUIT = "EXTRA_FRUIT"
        const val EXTRA_FRUIT_LIST = "EXTRA_FRUIT_LIST"
        const val EXTRA_FRUIT_UPDATE = "EXTRA_FRUIT_UPDATE"
        const val EXTRA_FRUIT_POSITION = "EXTRA_FRUIT_POSITION"

        var fruits = mutableListOf(
            Fruit("Banana", "Bananas Contain Many Important Nutrients, Contain Nutrients That Moderate Blood Sugar Levels and etc.", "0"),
            Fruit("Apple", "Apples May Be Good for Weight Loss, apples May Be Good for Weight Loss, and they’re Linked to a Lower Risk of Diabetes", "1"),
            Fruit("Orange", "The vitamin C in oranges helps your body in lots of ways: protects your cells from damage, helps your body make collagen and etc.", "2")
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fruitsImages = resources.obtainTypedArray(R.array.fruits)

        if (savedInstanceState != null) {
            fruits = savedInstanceState.getParcelableArrayList<Fruit>(EXTRA_FRUIT_LIST)
                ?.toMutableList() ?: fruits
            fruitAdapter.notifyDataSetChanged()
        }

        binding.recyclerViewFruits.adapter = fruitAdapter
        binding.recyclerViewFruits.layoutManager = LinearLayoutManager(this)

        val itemHelper = ItemTouchHelper(FruitItemTouchHelperCallback(fruitAdapter))
        itemHelper.attachToRecyclerView(binding.recyclerViewFruits)
    }

    private fun onItemClickListener(fruit: Fruit) {
        val fruitDesc = Intent(this, DescriptionFruitsActivity::class.java)
        fruitDesc.putExtra(EXTRA_FRUIT, fruit)
        fruitDesc.putExtra(EXTRA_FRUIT_POSITION, fruits.indexOf(fruit))
        startActivityForResult(fruitDesc, REQUEST_DESCRIPTION_FRUIT)
    }

    fun onClickAddFruits(view: View) {
        val addFruitsActivity = Intent(this, AddFruitsActivity::class.java)
        startActivityForResult(addFruitsActivity, REQUEST_CODE)
    }

    private fun confirmRemoveFruit(position: Int) {
        val view = layoutInflater.inflate(R.layout.dialog_custom_warning_remove, null)
        val builder : AlertDialog.Builder = AlertDialog.Builder(this, R.style.Theme_DevApp20211_CustomWarningDialog)

        builder.apply {
            setView(view)
            setCancelable(true)
        }
        val dialog = builder.create()

        view.findViewById<Button>(R.id.dialog_warning_btn_remove).setOnClickListener {
            fruits.removeAt(position)
            fruitAdapter.notifyItemRemoved(position)
            dialog.dismiss()
        }

        view.findViewById<Button>(R.id.dialog_warning_btn_cancel).setOnClickListener {
            fruitAdapter.notifyItemRangeChanged(position, fruitAdapter.itemCount)
            dialog.dismiss()
        }

        view.findViewById<TextView>(R.id.dialog_warning_remove_title).text = getString(R.string.warning_default_title)
        view.findViewById<TextView>(R.id.dialog_warning_remove_description).text = getString(R.string.warning_description_remove)

        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                data?.getParcelableExtra<Fruit>(EXTRA_FRUIT)?.let {
                    fruits.add(it)
                    fruitAdapter.notifyItemInserted(fruits.lastIndex)
                }
            }

            if (requestCode == REQUEST_DESCRIPTION_FRUIT) {
                val toUpdate : Boolean = data?.getBooleanExtra(EXTRA_FRUIT_UPDATE, false) == true
                val position = data?.getIntExtra(EXTRA_FRUIT_POSITION, -1)
                data?.getParcelableExtra<Fruit>(EXTRA_FRUIT)?.let { fruit ->
                    if (toUpdate) {
                        fruits[position!!] = fruit
                        fruitAdapter.notifyItemChanged(position)
                    } else {
                        fruits.remove(fruit)
                        fruitAdapter.notifyItemRemoved(position!!)
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_FRUIT_LIST, ArrayList(fruits))
    }
}