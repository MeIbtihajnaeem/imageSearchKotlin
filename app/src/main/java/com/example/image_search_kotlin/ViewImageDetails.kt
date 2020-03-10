package com.example.image_search_kotlin

import android.os.Bundle
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import org.w3c.dom.Text


class ViewImageDetails : AppCompatActivity() {
    private var imageTitle: TextView? = null
    private var imageWidth: TextView? = null
    private var imageHeight: TextView? = null
    private var author: TextView? = null
    private var viewport: TextView? = null
    private var imageView: ImageView? = null
    private var imageDetails: ImageDetails = ImageDetails()
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image_details)
        imageView = findViewById(R.id.fullImageView)
        imageTitle = findViewById(R.id.imageTitle)
        imageHeight = findViewById(R.id.imageHeightValue)
        imageWidth = findViewById(R.id.imageWidthValue)
        viewport = findViewById(R.id.viewportValue)
        author = findViewById(R.id.authorValue)
        ConnectionCheck.isNetworkConnected(this)
        imageDetails = (getIntent().getSerializableExtra("imageDetails") as ImageDetails?)!!
        setDetails()
    }

    fun setDetails() {
        imageTitle!!.text = imageDetails!!.getTitle()
        if (!imageDetails!!.isPageMapNull()) {
            if (!imageDetails!!.getPageMap()!!.isThumbnailsNULL()) {
                imageWidth?.setText(imageDetails!!.getPageMap()!!.getThumbnails()!![0]?.getWidth())

                imageHeight?.setText(imageDetails!!.getPageMap()!!.getThumbnails()!![0]?.getHeight())
            }
            if (!imageDetails!!.getPageMap()!!.isMetaDataNULL()) {
                viewport?.setText(imageDetails!!.getPageMap()!!.getMetaData()!![0]?.getViewport())
                author?.setText(imageDetails!!.getPageMap()!!.getMetaData()!![0]?.getAuthor())
            }
            val circularProgressDrawable = CircularProgressDrawable(this)
            circularProgressDrawable.setStrokeWidth(10f)
            circularProgressDrawable.setCenterRadius(30f)
            circularProgressDrawable.start()
            //check if thumbnail is present
            if (!imageDetails!!.getPageMap()!!.isImageNULL()) {
                val imageSrc = imageDetails!!.getPageMap()!!.getImages()!![0]!!.getSrc()
                imageView?.let {
                    Glide.with(this)
                        .asBitmap()
                        .load(imageSrc)
                        .placeholder(circularProgressDrawable)
                        .error(R.drawable.image_not_found)
                        .into(it)
                }
            } else {
                Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.image_not_found)
            }
        }
    }

    companion object {
        private const val TAG = "ViewImageDetails"
    }
}
