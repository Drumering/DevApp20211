package school.cesar.devapp20211.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import school.cesar.devapp20211.databinding.ActivityAddFruitsBinding
import school.cesar.devapp20211.models.Fruit
import school.cesar.devapp20211.utils.Utils

class AddFruitsActivity : AppCompatActivity() {

    /*
    * Private Vars
    */
    private lateinit var binding : ActivityAddFruitsBinding
    private lateinit var fruit : Fruit
    private lateinit var uri : String

    companion object {
        const val REQUEST_CODE = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFruitsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        binding.imgvAddFruitImage.setOnClickListener {
            getImageFromGallery()
        }

        binding.btnSave.setOnClickListener {
            val returnIntent = Intent()
            fruit = Fruit(binding.etName.text.toString(), binding.etBenefits.text.toString(), uri)
            returnIntent.putExtra(MainActivity.EXTRA_FRUIT, fruit)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            binding.imgvAddFruitImage.setImageURI(data?.data)
            uri = data?.data.toString()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        hideKeyboardAndClearFocus()
        return super.onTouchEvent(event)
    }

    private fun hideKeyboardAndClearFocus() {
        Utils.hideKeyboard(this, binding.etName)
        Utils.hideKeyboard(this, binding.etBenefits)
        binding.etName.clearFocus()
        binding.etBenefits.clearFocus()
    }

    private fun getImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }
}