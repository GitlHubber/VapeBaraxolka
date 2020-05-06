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
    var id : Int? = null

    @SerializedName("category_name")
    var category_name: String? = null

    @SerializedName("subcategories")
    var subcategories: ArrayList<Subcategories>? = null
}

class Subcategories {
    @SerializedName("id")
    var id : Int? = null

    @SerializedName("subcategory_name")
    var subcategory_name: String? = null

    @SerializedName("category_id")
    var category_id : Int? = null
}

class ReasonsResponse {
    @SerializedName("reasons")
    var reasons: ArrayList<Reasons>? = null

    @SerializedName("success")
    var success = 0
}

class Reasons {
    @SerializedName("reason_name")
    var reason_name: String? = null
}