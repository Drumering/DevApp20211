package school.cesar.devapp20211.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import school.cesar.devapp20211.R

class Utils {
    companion object {

        fun wrappedDrawable (context: Context) = AppCompatResources.getDrawable(context, R.drawable.ic_arrow_back)?.apply {
            DrawableCompat.setTint(this, Color.WHITE)
        }

        fun showKeyboard (context: Context) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        fun hideKeyboard (context: Context, editText: EditText) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
        }
    }
}