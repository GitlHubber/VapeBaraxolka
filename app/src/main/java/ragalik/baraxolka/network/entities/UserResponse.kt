package ragalik.baraxolka.network.entities

import com.google.gson.annotations.SerializedName

class User {

    @field:SerializedName("id")
    val id: Int? = null

    @field:SerializedName("nickname")
    val nickname: String? = null

    @field:SerializedName("email")
    val email: String? = null

    @field:SerializedName("phoneNumber")
    val phoneNumber: String? = null

    @field:SerializedName("region")
    val region: String? = null

    @field:SerializedName("town")
    val town: String? = null

    @field:SerializedName("image")
    val image: String? = null

    @field:SerializedName("status_name")
    val statusName: String? = null
}

class UserResponse {
    @field:SerializedName("success")
    val success: Boolean? = null

    @field:SerializedName("correct_email")
    val correctEmail: Boolean? = null

    @field:SerializedName("user")
    val user: List<User>? = null
}

class RegisterResponse {
    @field:SerializedName("success")
    val success: Boolean? = null

    @field:SerializedName("id_user")
    var idUser: Int? = null

    @field:SerializedName("email_exist")
    val isEmailExist: Boolean? = null

    @field:SerializedName("number_exist")
    val isNumberExist: Boolean? = null
}