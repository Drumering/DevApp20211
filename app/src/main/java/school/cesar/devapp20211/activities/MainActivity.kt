package school.cesar.devapp20211.activities

import android.app.Activity
import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import school.cesar.devapp20211.R
import school.cesar.devapp20211.adapters.FruitsRecyclerViewAdapter
import school.cesar.devapp20211.databinding.ActivityMainBinding
import school.cesar.devapp20211.models.Fruit
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var fruits = mutableListOf(
        Fruit("Banana", "Bananas Contain Many Important Nutrients, Contain Nutrients That Moderate Blood Sugar Levels and etc.", "0"),
        Fruit("Apple", "Apples May Be Good for Weight Loss, apples May Be Good for Weight Loss, and they’re Linked to a Lower Risk of Diabetes", "1"),
        Fruit("Orange", "The vitamin C in oranges helps your body in lots of ways: protects your cells from damage, helps your body make collagen and etc.", "2")
    )

    companion object {
        const val REQUEST_CODE = 1
        const val EXTRA_FRUIT = "EXTRA_FRUIT"
        const val EXTRA_FRUIT_LIST = "EXTRA_FRUIT_LIST"
        var fruitsImages: TypedArray? = null
    }

    private lateinit var fruitAdapter: FruitsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            fruits = savedInstanceState.getParcelableArrayList<Fruit>(EXTRA_FRUIT_LIST)
                ?.toMutableList() ?: fruits
        }

        fruitAdapter = FruitsRecyclerViewAdapter(this, fruits, this::onItemClickListener)

        fruitsImages = resources.obtainTypedArray(R.array.fruits)

        binding.recyclerViewFruits.adapter = fruitAdapter
        binding.recyclerViewFruits.layoutManager = LinearLayoutManager(this)
    }

    private fun onItemClickListener(fruit: Fruit) {
        val fruitDesc = Intent(this, DescriptionFruitsActivity::class.java)
        fruitDesc.putExtra(EXTRA_FRUIT, fruit)
        startActivity(fruitDesc)
    }

    fun onClickAddFruits(view: View) {
        val addFruitsActivity = Intent(this, AddFruitsActivity::class.java)
        startActivityForResult(addFruitsActivity, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            data?.getParcelableExtra<Fruit>(EXTRA_FRUIT)?.let {
                fruits.add(it)
                fruitAdapter.notifyItemInserted(fruits.lastIndex)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_FRUIT_LIST, ArrayList(fruits))
    }
}