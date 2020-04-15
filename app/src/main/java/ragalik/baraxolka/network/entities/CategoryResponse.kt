package ragalik.baraxolka.network.entities

import com.google.gson.annotations.SerializedName
import java.util.*

class CategoryResponse {
    @SerializedName("categories")
    var categories: ArrayList<Categories>? = null

    @SerializedName("success")
    var success = 0

}

class Categories {
    @SerializedName("id")
    var id = 0

    @SerializedName("category_name")
    var category_name: String? = null

    @SerializedName("subcategories")
    var subcategories: ArrayList<Subcategories>? = null

}

class Subcategories {
    @SerializedName("id")
    var id = 0

    @SerializedName("subcategory_name")
    var subcategory_name: String? = null

    @SerializedName("category_id")
    var category_id = 0

}