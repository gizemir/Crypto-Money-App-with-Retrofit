package com.gizemir.cryptomoneyappwithretrofit.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.gizemir.cryptomoneyappwithretrofit.R
import com.gizemir.cryptomoneyappwithretrofit.databinding.RecyclerRowBinding
import com.gizemir.cryptomoneyappwithretrofit.model.CryptoModel

class CryptoAdapter(private val cryptoList: ArrayList<CryptoModel>, private val listener: Listener):RecyclerView.Adapter<CryptoAdapter.CryptoHolder>() {

    interface Listener{
        fun onItemClick(cryptoModel: CryptoModel)
    }
    private val colors: Array<String> = arrayOf("#e72b2b", "#dbdcd9", "#d9dcd9", "#d9dcdb", "#d9dadc", "#dad9dc", "#dcd9da", "#36445f")


    class CryptoHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(cryptoModel: CryptoModel, colors: Array<String>, position: Int, listener: Listener){
            itemView.setOnClickListener{
                listener.onItemClick(cryptoModel)
            }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
            binding.textName.text = cryptoModel.currency
            binding.textPrice.text = cryptoModel.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoHolder(binding)
    }

    override fun getItemCount(): Int {
        return cryptoList.size

    }

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {
        holder.bind(cryptoList[position], colors, position, listener)
    }
}