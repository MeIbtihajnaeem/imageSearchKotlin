package com.example.image_search_kotlin

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable


@JsonIgnoreProperties(ignoreUnknown = true)
class ImageDetails : Serializable {

    val Tag: String = "ImageDetails"
    @JsonProperty("pagemap")
    private var pageMap: PageMap? = null

    private var title: String? = null;

    fun getTitle(): String? {
        return title
    }

    fun isPageMapNull(): Boolean {
        return pageMap == null
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getPageMap(): PageMap? {
        println("I am here get")
        return pageMap
    }

    fun setPageMap(pageMap: PageMap?) {
        println("I am here set")
        this.pageMap = pageMap
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
class PageMap : Serializable {
    @JsonProperty("cse_thumbnail")
    private var thumbnails: List<Thumbnail>? = null
    @JsonProperty("cse_image")
    private var images: List<Image>? = null
    @JsonProperty("metatags")
    private var metaData: List<MetaData>? = null

    fun getThumbnails(): List<Thumbnail>? {
        return thumbnails
    }

    fun isThumbnailsNULL(): Boolean {
        return thumbnails == null
    }

    fun isImageNULL(): Boolean {
        return images == null
    }

    fun isMetaDataNULL(): Boolean {
        return metaData == null
    }

    fun setThumbnails(thumbnails: List<Thumbnail>?) {
        this.thumbnails = thumbnails
    }

    fun getImages(): List<Image>? {
        return images
    }

    fun setImages(images: List<Image>?) {
        this.images = images
    }

    fun getMetaData(): List<MetaData>? {
        return metaData
    }

    fun setMetaData(metaData: List<MetaData>?) {
        this.metaData = metaData
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Thumbnail : Serializable {
    private var width: String? = null
    private var height: String? = null
    private var src: String? = null

    fun getWidth(): String? {
        return width
    }

    fun setWidth(width: String?) {
        this.width = width
    }

    fun getHeight(): String? {
        return height
    }

    fun setHeight(height: String?) {
        this.height = height
    }
    fun getSrc(): String? {
        return src
    }

    fun setSrc(src: String?) {
        this.src = src
    }

}

@JsonIgnoreProperties(ignoreUnknown = true)
class MetaData : Serializable {
    private var author: String? = null
    private var viewport: String? = null

    fun getAuthor(): String? {
        return author
    }

    fun setAuthor(author: String?) {
        this.author = author
    }

    fun getViewport(): String? {
        return viewport
    }

    fun setViewport(viewport: String?) {
        this.viewport = viewport
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Image : Serializable {
   private var src: String? = null
    fun getSrc(): String? {
        return src
    }

    fun setSrc(src: String?) {
        this.src = src
    }
}