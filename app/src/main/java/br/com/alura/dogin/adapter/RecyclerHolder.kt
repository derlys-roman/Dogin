package br.com.alura.dogin.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.dogin.databinding.ItemDogBinding
import com.squareup.picasso.Picasso

class RecyclerHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemDogBinding.bind(view)

    fun bind(image: String) {
        Picasso.get().load(image).into(binding.ivdog)
    }
}