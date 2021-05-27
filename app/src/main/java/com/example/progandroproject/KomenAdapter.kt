package com.example.progandroproject

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayInputStream

class KomenAdapter(val komen: ArrayList<Komentar>)
    : RecyclerView.Adapter<KomenAdapter.ViewHolder>() {


    class ViewHolder(val v: View) : RecyclerView.ViewHolder(v){
        var test = 0
        fun bindView(komen: Komentar){


            v.findViewById<TextView>(R.id.tvKomen).text = komen.komentar

            v.findViewById<ImageView>(R.id.gambarlike).setOnClickListener(View.OnClickListener {
                if(test == 0) {
                    test++
                    v.findViewById<ImageView>(R.id.gambarlike).setImageResource(R.drawable.ic_like)
                }else{
                    v.findViewById<ImageView>(R.id.gambarlike).setImageResource(R.drawable.ic_like_border)

            }})


        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KomenAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_komentar, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(komen[position])


    }

    override fun getItemCount(): Int {
        return komen.size
    }


}