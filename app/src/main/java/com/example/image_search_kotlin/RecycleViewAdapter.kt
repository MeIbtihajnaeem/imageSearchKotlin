package com.example.image_search_kotlin

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

class RecycleViewAdapter(
    private val imageDetails: List<ImageDetails>,
    private val context: Context
) :
    RecyclerView.Adapter<RecycleViewAdapter.ViewHolder?>() {
    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): ViewHolder {
        Log.i("R view ","1")
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_listimageiteam, viewGroup, false)
        Log.i("R view ","2")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull viewHolder: ViewHolder, position: Int) {
        Log.i("R view ","checking onBindViewHolder")
        val imageDetail:ImageDetails = imageDetails[position]
        var imageSrc: String? = ""
        val imageTitle = imageDetail.getTitle()
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.setStrokeWidth(10f)
        circularProgressDrawable.setCenterRadius(30f)
        circularProgressDrawable.start()
        //check if thumbnail is present
        if (!imageDetail.isPageMapNull()) {
            Log.i("R view ","checking onBindViewHolder2")
            if (!imageDetail.getPageMap()!!.isThumbnailsNULL()) {
                Log.i("R view ","checking onBindViewHolder3")
                imageSrc = imageDetail.getPageMap()!!.getThumbnails()!![0]?.getSrc()
                Glide.with(context)
                    .asBitmap()
                    .load(imageSrc)
                    .placeholder(circularProgressDrawable)
                    .error(R.drawable.image_not_found)
                    .into(viewHolder.resultImage)
            } else {
                Log.i("R view ","checking onBindViewHolder4")
                Glide.with(context)
                    .asBitmap()
                    .load(R.drawable.image_not_found)
            }
        }
        Log.i("R view ","checking onBindViewHolder5")
        viewHolder.resultImageTitle.text = imageTitle
        viewHolder.resultLayout.setOnClickListener {
            fun onClick(view:View):Unit{
                val intent = Intent(view.context, ViewImageDetails::class.java)
                intent.putExtra("imageDetails", imageDetail)
                view.context.startActivity(intent)
            }
            onClick(it)
        }
    }

    override fun getItemCount(): Int {
        Log.i("R view ","9")
        return imageDetails.size
    }

      public class ViewHolder(@NonNull itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var resultImage: ImageView
        var resultImageTitle: TextView
        var resultLayout: RelativeLayout

        init {
            Log.i("R view ","10")
            resultImage = itemView.findViewById(R.id.result_image)
            resultImageTitle = itemView.findViewById(R.id.result_imageTitle)
            resultLayout = itemView.findViewById(R.id.result_layout)
        }
    }
}



