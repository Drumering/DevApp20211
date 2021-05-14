package school.cesar.devapp20211.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fruit(val name: String, val description: String, val image: Int) : Parcelable
