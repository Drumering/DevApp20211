package school.cesar.devapp20211.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    private val fruitAdapter = FruitsRecyclerViewAdapter(this, fruits, this::onItemClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewFruits.adapter = fruitAdapter
        binding.recyclerViewFruits.layoutManager = LinearLayoutManager(this)
    }

    private fun onItemClickListener(fruit: Fruit) {

    }
}