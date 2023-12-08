package com.example.githubreposearcher.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale

/**
Method to get the correct Time format
 */
@BindingAdapter("isoDateToFormattedDate")
   fun TextView.setFormattedDateFromIso(timestamp: String?) {
        timestamp?.let {
            val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())

            try {
                val date = isoDateFormat.parse(timestamp)
                val formattedDate = outputDateFormat.format(date)
                text = formattedDate
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

//@BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
//fun loadImage(view: ImageView, imageUrl: String?, binding: ViewDataBinding?, placeholder: Drawable?) {
//    imageUrl?.let {
//        Glide.with(view.context)
//            .load(imageUrl)
//            .apply {
//                binding?.let {
//                    // If binding is provided, use its root as the target view
//                    into(view)
//                } ?: into(view) // Otherwise, use the original view
//                placeholder?.let { placeholder(it) }
//            }
//    }
//}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Picasso.get()
            .load(imageUrl)
            .into(view)
    }
}
