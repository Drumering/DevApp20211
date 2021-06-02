package school.cesar.devapp20211.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.DrawableCompat
import com.squareup.picasso.Picasso
import school.cesar.devapp20211.R
import school.cesar.devapp20211.databinding.ActivityAddFruitsBinding
import school.cesar.devapp20211.models.Fruit
import school.cesar.devapp20211.utils.Utils
import java.lang.NumberFormatException

class AddFruitsActivity : AppCompatActivity() {

    /*
    * Private Vars
    */
    private lateinit var binding : ActivityAddFruitsBinding
    private lateinit var fruit : Fruit
    private var uri = ""
    private var toUpdate : Boolean = false

    companion object {
        const val REQUEST_CODE = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFruitsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        getExtras()
    }

    private fun getExtras() {
        intent.getParcelableExtra<Fruit>(MainActivity.EXTRA_FRUIT)?.let {
            toUpdate = true
            fruit = it
            uri = it.image

            binding.etName.setText(it.name)
            binding.etBenefits.setText(it.description)

            try {
                binding.imgvAddFruitImage.setImageDrawable(MainActivity.fruitsImages?.getDrawable(it.image.toInt()))
            } catch (e: NumberFormatException) {
                Picasso.get().load(it.image).into(binding.imgvAddFruitImage)
            }
        }
    }

    private fun setupToolbar() {
        binding.toolbar.navigationIcon = Utils.wrappedDrawable(this)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onResume() {
        super.onResume()

        binding.imgvAddFruitImage.setOnClickListener {
            getImageFromGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            Picasso.get().load(data?.data).into(binding.imgvAddFruitImage)
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

    private fun isValidFruit(fruit: Fruit) = when {
        fruit.name.isNotEmpty() && fruit.description.isNotEmpty() && fruit.image.isNotEmpty() -> true
        fruit.name.isEmpty() -> {
            Toast.makeText(this, "Please, fill the fruit name", Toast.LENGTH_SHORT).show()
            false }
        fruit.description.isEmpty() -> {
            Toast.makeText(this, "Please, fill the fruit benefits", Toast.LENGTH_SHORT).show()
            false
        }
        fruit.image.isEmpty() -> {
            Toast.makeText(this, "Please, tap on the image to choose one", Toast.LENGTH_SHORT).show()
            false
        }
        else -> false
    }

    private fun verifyDuplicatedFruit(fruit: Fruit, context: Context) : Boolean {
        var isDuplicated = false
        MainActivity.fruits.forEach {
            if (it.name.lowercase() == fruit.name.lowercase()) {
                isDuplicated = true
            }
        }

        if (isDuplicated) {
            val view = layoutInflater.inflate(R.layout.dialog_custom_warning_duplicated, null)
            val builder : AlertDialog.Builder = AlertDialog.Builder(context, R.style.Theme_DevApp20211_CustomWarningDialog)

            builder.apply {
                setView(view)
                setCancelable(true)
            }
            val dialog = builder.create()

            view.findViewById<Button>(R.id.dialog_warning_btn).setOnClickListener {
                dialog.dismiss()
            }

            view.findViewById<TextView>(R.id.dialog_warning_title).text = getString(R.string.warning_default_title)
            view.findViewById<TextView>(R.id.dialog_warning_description).text = getString(R.string.warning_description_duplicated)

            dialog.show()
        }

        return isDuplicated
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_fruit, menu)

        menu?.findItem(R.id.menu_add_fruit)?.icon?.let { DrawableCompat.setTint(it, Color.WHITE) }
        menu?.findItem(R.id.menu_cancel_operation)?.icon?.let { DrawableCompat.setTint(it, Color.WHITE) }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }

        R.id.menu_add_fruit -> {
            fruit = Fruit(binding.etName.text.toString(), binding.etBenefits.text.toString(), uri)

            if (toUpdate) {
                if (isValidFruit(fruit)) {
                    returnWithSetResult()
                }
            } else {
                if (isValidFruit(fruit) && !verifyDuplicatedFruit(fruit, this)) {
                    returnWithSetResult()
                }
            }
            true
        }

        R.id.menu_cancel_operation -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun returnWithSetResult() {
        val returnIntent = Intent()
        returnIntent.putExtra(MainActivity.EXTRA_FRUIT, fruit)
        setResult(RESULT_OK, returnIntent)
        finish()
    }
}