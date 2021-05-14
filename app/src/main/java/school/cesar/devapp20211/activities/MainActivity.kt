package school.cesar.devapp20211.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import school.cesar.devapp20211.adapters.FruitsRecyclerViewAdapter
import school.cesar.devapp20211.databinding.ActivityMainBinding
import school.cesar.devapp20211.models.Fruit

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val fruits = mutableListOf(
        Fruit("Banana", "Bananas Contain Many Important Nutrients, Contain Nutrients That Moderate Blood Sugar Levels and etc.", 0),
        Fruit("Apple", "Apples May Be Good for Weight Loss, apples May Be Good for Weight Loss, and they’re Linked to a Lower Risk of Diabetes", 1),
        Fruit("Orange", "The vitamin C in oranges helps your body in lots of ways: protects your cells from damage, helps your body make collagen and etc.", 2)
    )

    companion object {
        const val REQUEST_CODE = 1
    }

    private val fruitAdapter = FruitsRecyclerViewAdapter(this, fruits, this::onItemClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewFruits.adapter = fruitAdapter
        binding.recyclerViewFruits.layoutManager = LinearLayoutManager(this)
    }

    private fun onItemClickListener(fruit: Fruit) {
        Toast.makeText(this, fruit.name, Toast.LENGTH_SHORT).show()
    }

    fun onClickAddFruits(view: View) {
        val addFruitsActivity = Intent(this, AddFruitsActivity::class.java)
        startActivityForResult(addFruitsActivity, REQUEST_CODE)
    }
}