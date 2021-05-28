package school.cesar.devapp20211.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import com.squareup.picasso.Picasso
import school.cesar.devapp20211.R
import school.cesar.devapp20211.databinding.ActivityDescriptionFruitsBinding
import school.cesar.devapp20211.models.Fruit
import school.cesar.devapp20211.utils.Utils
import java.lang.NumberFormatException

class DescriptionFruitsActivity : AppCompatActivity() {

    /*
    * Private Vars
    */
    private lateinit var binding: ActivityDescriptionFruitsBinding
    private lateinit var fruit: Fruit
    private var position : Int = -1
    private var isEditMode : Boolean = false

    companion object {
        const val REQUEST_EDIT_FRUIT = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionFruitsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        getExtras()
    }

    private fun setupToolbar() {
        binding.toolbarDescription.navigationIcon = Utils.wrappedDrawable(this)
        setSupportActionBar(binding.toolbarDescription)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun getExtras() {
        intent.getParcelableExtra<Fruit>(MainActivity.EXTRA_FRUIT)?.let {
            fruit = it
            setupFruit(it)
        }

        position = intent.getIntExtra(MainActivity.EXTRA_FRUIT_POSITION, -1)
    }

    private fun setupFruit(fruit: Fruit) = fruit.let {
        try {
            binding.imgvFruitImage.setImageDrawable(MainActivity.fruitsImages?.getDrawable(it.image.toInt()))
        } catch (e: NumberFormatException) {
            Picasso.get().load(it.image).into(binding.imgvFruitImage)
        }
        binding.tvFruitName.text = it.name
        binding.tvFruitBenefits.text = it.description
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_description, menu)

        menu?.findItem(R.id.menu_edit_fruit)?.icon?.let { DrawableCompat.setTint(it, Color.WHITE) }
        menu?.findItem(R.id.menu_remove_fruit_from_description)?.icon?.let { DrawableCompat.setTint(it, Color.WHITE) }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            if (isEditMode) {
                val updateIntent = Intent()
                updateIntent.putExtra(MainActivity.EXTRA_FRUIT, fruit)
                updateIntent.putExtra(MainActivity.EXTRA_FRUIT_UPDATE, true)
                updateIntent.putExtra(MainActivity.EXTRA_FRUIT_POSITION, position)
                setResult(Activity.RESULT_OK, updateIntent)
            }
            finish()
            true
        }

        R.id.menu_edit_fruit -> {
            isEditMode = true
            val editIntent = Intent(this, AddFruitsActivity::class.java)
            editIntent.putExtra(MainActivity.EXTRA_FRUIT, fruit)
            startActivityForResult(editIntent, REQUEST_EDIT_FRUIT)
            true
        }

        R.id.menu_remove_fruit_from_description -> {

            val view = layoutInflater.inflate(R.layout.dialog_custom_warning_remove, null)
            val builder : AlertDialog.Builder = AlertDialog.Builder(this, R.style.Theme_DevApp20211_CustomWarningDialog)

            builder.apply {
                setView(view)
                setCancelable(true)
            }
            val dialog = builder.create()

            view.findViewById<Button>(R.id.dialog_warning_btn_remove).setOnClickListener {
                val removeIntent = Intent()
                removeIntent.putExtra(MainActivity.EXTRA_FRUIT, fruit)
                removeIntent.putExtra(MainActivity.EXTRA_FRUIT_POSITION, position)
                setResult(Activity.RESULT_OK, removeIntent)
                dialog.dismiss()
                finish()
            }

            view.findViewById<Button>(R.id.dialog_warning_btn_cancel).setOnClickListener {
                dialog.dismiss()
            }

            view.findViewById<TextView>(R.id.dialog_warning_remove_title).text = getString(R.string.warning_default_title)
            view.findViewById<TextView>(R.id.dialog_warning_remove_description).text = getString(R.string.warning_description_remove)

            dialog.show()

            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    interface OnClickListener {
        fun onRemoveClick()
        fun onCancelClick()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_EDIT_FRUIT) {
            data?.getParcelableExtra<Fruit>(MainActivity.EXTRA_FRUIT)?.let {
                fruit = it
                setupFruit(it)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}