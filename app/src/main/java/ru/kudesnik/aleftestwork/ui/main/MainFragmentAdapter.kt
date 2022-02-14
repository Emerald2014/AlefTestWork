package ru.kudesnik.aleftestwork.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Precision
import coil.size.Scale
import ru.kudesnik.aleftestwork.R
import ru.kudesnik.aleftestwork.databinding.MainFragmentRecyclerItemBinding

class MainFragmentAdapter(private val itemClickListener: MainFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var imageData: List<String> = listOf()

    private lateinit var binding: MainFragmentRecyclerItemBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<String>) {
        imageData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        binding = MainFragmentRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(imageData[position])
    }

    override fun getItemCount() = imageData.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: String) = with(binding) {

            imageViewRecyclerItem.load(data) {
                precision(Precision.EXACT)
                crossfade(true)
                error(R.drawable.no_image)
                placeholder(R.drawable.no_poster)
                scale(Scale.FILL)
            }
            root.setOnClickListener {
                itemClickListener.onItemViewClick(data)
            }
        }
    }
}