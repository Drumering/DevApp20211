package school.cesar.devapp20211.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import school.cesar.devapp20211.R
import school.cesar.devapp20211.databinding.ActivityDescriptionFruitsBinding
import school.cesar.devapp20211.models.Fruit

class DescriptionFruitsActivity : AppCompatActivity() {

    /*
    * Private Vars
    */
    private lateinit var binding: ActivityDescriptionFruitsBinding
    private var fruit: Fruit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionFruitsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        fruit = intent.getParcelableExtra(MainActivity.EXTRA_FRUIT)
        fruit?.let {
            binding.imgvFruitImage.setImageDrawable(MainActivity.fruitsImages?.getDrawable(it.image))
            binding.tvFruiName.text = it.name
            binding.tvFruitBenefits.text = it.description
        }
    }
}