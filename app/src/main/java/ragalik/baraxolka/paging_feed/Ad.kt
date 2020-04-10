package ragalik.baraxolka.paging_feed

import com.google.gson.annotations.SerializedName

class Ad {
    @field:SerializedName("id")
    var id: Int? = null

    @field:SerializedName("title")
    var title: String? = null

    @field:SerializedName("nickname")
    var userNickname: String? = null

    @field:SerializedName("userImage")
    var userImage: String? = null

    @field:SerializedName("dateTime")
    var dateTime: String? = null

    @field:SerializedName("description")
    var description: String? = null

    @field:SerializedName("image1")
    var image1url: String? = null

    @field:SerializedName("image2")
    var image2url: String? = null

    @field:SerializedName("image3")
    var image3url: String? = null

    @field:SerializedName("image4")
    var image4url: String? = null

    @field:SerializedName("image5")
    var image5url: String? = null

    @field:SerializedName("price")
    var price: Int? = null

    @field:SerializedName("views")
    var views: Int? = null

    @field:SerializedName("region")
    var region: String? = null
    @field:SerializedName("town")
    var town: String? = null
    @field:SerializedName("subcategory_name")
    var subcategoryName: String? = null

    @field:SerializedName("category_name")
    var categoryName: String? = null

    @field:SerializedName("id_user")
    var idUser: String? = null

    @field:SerializedName("favourite")
    var isFavourite: Boolean? = null
}

class AdResponse {
    @field:SerializedName("ads")
    var ads: List<Ad>? = null
}

class FullAdResponse {
    @field:SerializedName("ad")
    var ad: List<Ad>? = null
}

