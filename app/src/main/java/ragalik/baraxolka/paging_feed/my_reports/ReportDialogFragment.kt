package ragalik.baraxolka.paging_feed.my_reports

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_report_dialog.*
import ragalik.baraxolka.R
import ragalik.baraxolka.network.entities.Report
import ragalik.baraxolka.other_logic.full_ad.FullAdActivity
import ragalik.baraxolka.paging_feed.DateTimeUtils
import ragalik.baraxolka.paging_feed.administrator.AdministratorActivity
import ragalik.baraxolka.paging_feed.seller.SellerProfileActivity
import java.lang.Exception
import java.util.*

class ReportDialogFragment(private val report : Report?, private val flag : String) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_report_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tw_report_id.text = "Жалоба #${report?.reportId}"
        val customReportReason = SpannableStringBuilder()
                .bold { append("Причина жалобы: ") }
                .append("${report?.reasonName}")
        tw_report_reason.text = customReportReason

        val customReportMessage = if (report?.reportMessage.isNullOrEmpty()) {
            SpannableStringBuilder()
                    .bold { append("Комментарий к жалобе: ") }
                    .append("отсутствует.")
        } else {
            SpannableStringBuilder()
                    .bold { append("Комментарий к жалобе: ") }
                    .append("${report?.reportMessage?.substring(0, 1)?.toUpperCase(Locale.ROOT)}")
                    .append("${report?.reportMessage?.substring(1, report.reportMessage.length)}")
        }
        tw_report_message.text = customReportMessage

        tw_report_datetime.text = "Дата создания жалобы: ${DateTimeUtils.getInstance()?.getNormalizedDatetime(report?.reportDatetime.toString())}"

        goto_ad_button.setOnClickListener {
            dialog?.dismiss()
            val intent = when (flag) {
                "MY_REPORTS" -> Intent(MyReportsFragment.activity, FullAdActivity::class.java)
                "REPORTS" -> Intent(AdministratorActivity.activity, FullAdActivity::class.java)
                else -> throw Exception()
            }
            intent.putExtra("adId", report?.adId)
            startActivity(intent)
        }

        if (flag == "REPORTS") {
            goto_user_button.visibility = View.VISIBLE
            goto_user_button.setOnClickListener {
                dialog?.dismiss()
                val intent = Intent(AdministratorActivity.activity, SellerProfileActivity::class.java)
                intent.putExtra("sellerId", report?.userId)
                startActivity(intent)
            }
        }
    }
}