package com.example.placesdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.placesdemo.databinding.PlaceListItemBinding

class PlacesAdapter (private val places : List<Place>) : RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {
    inner class PlacesViewHolder(val binding: PlaceListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(place : Place ) {
            binding.placeText.text = "Latitude: ${place.latitude}, Longitude: ${place.longitude}"



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PlaceListItemBinding.inflate(inflater,parent,false)
        return PlacesViewHolder(binding)
    }





    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place)

    }

    override fun getItemCount() = places.size

}

