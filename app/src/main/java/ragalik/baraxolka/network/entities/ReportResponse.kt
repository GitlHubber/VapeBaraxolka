package ragalik.baraxolka.network.entities

import com.google.gson.annotations.SerializedName

class Report {

    @field:SerializedName("id")
    val reportId: Int? = null

    @field:SerializedName("reason_name")
    val reasonName: String? = null

    @field:SerializedName("report_message")
    val reportMessage: String? = null

    @field:SerializedName("datetime")
    val reportDatetime: String? = null

    @field:SerializedName("ad_id")
    val adId: Int? = null

    @field:SerializedName("nickname")
    val reporterNickname: String? = null

    @field:SerializedName("image")
    val reporterImage: String? = null

    @field:SerializedName("user_id")
    val userId: Int? = null
}

class ReportResponse {
    @field:SerializedName("reports")
    val reports: List<Report>? = null
}