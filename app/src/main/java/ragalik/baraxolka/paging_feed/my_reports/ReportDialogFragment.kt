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

class ReportDialogFragment(private val report : Report?) : DialogFragment() {

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
                    .append("${report?.reportMessage}")
        }
        tw_report_message.text = customReportMessage

        tw_report_datetime.text = "Дата создания жалобы: ${DateTimeUtils.getInstance()?.getNormalizedDatetime(report?.reportDatetime.toString())}"

        goto_ad_button.setOnClickListener {
            dialog?.dismiss()
            val intent = Intent(MyReportsActivity.activity, FullAdActivity::class.java)
            intent.putExtra("adId", report?.adId)
            startActivity(intent)
        }
    }
}