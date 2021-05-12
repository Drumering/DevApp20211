package school.cesar.devapp20211.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import school.cesar.devapp20211.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}