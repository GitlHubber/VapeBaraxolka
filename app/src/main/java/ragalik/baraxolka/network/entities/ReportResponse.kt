package ragalik.baraxolka.network.entities

import com.google.gson.annotations.SerializedName

class Report {

    @field:SerializedName("id")
    val id: Int? = null

    @field:SerializedName("report_reason_id")
    val reportReasonId: String? = null

    @field:SerializedName("report_message")
    val reportMessage: String? = null

    @field:SerializedName("datetime")
    val datetime: String? = null

    @field:SerializedName("ad_id")
    val ad_id: String? = null

    @field:SerializedName("user_id")
    val user_id: String? = null
}

class ReportResponse {
    @field:SerializedName("reports")
    val reports: List<Report>? = null
}