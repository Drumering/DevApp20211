package school.cesar.devapp20211.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import school.cesar.devapp20211.R
import school.cesar.devapp20211.databinding.ActivityDescriptionFruitsBinding
import school.cesar.devapp20211.models.Fruit
import java.lang.NumberFormatException

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
            try {
                binding.imgvFruitImage.setImageDrawable(MainActivity.fruitsImages?.getDrawable(it.image.toInt()))
            } catch (e: NumberFormatException) {
                binding.imgvFruitImage.setImageURI(Uri.parse(it.image))
            }
            binding.tvFruiName.text = it.name
            binding.tvFruitBenefits.text = it.description
        }
    }
}