package ragalik.baraxolka.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_report.view.*
import ragalik.baraxolka.R
import ragalik.baraxolka.network.entities.Report
import ragalik.baraxolka.view.ui.fragment.ReportsFragment
import ragalik.baraxolka.view.ui.fragment.dialog_fragment.ReportDialogFragment
import ragalik.baraxolka.view.ui.fragment.MyReportsFragment
import java.lang.Exception

class ReportsAdapter(private val flag : String) : PagedListAdapter<Report, RecyclerView.ViewHolder>(AD_COMPARATOR) {

    companion object {

        private val AD_COMPARATOR = object : DiffUtil.ItemCallback<Report>() {
            override fun areItemsTheSame(oldItem: Report, newItem: Report): Boolean =
                    oldItem.reportId == newItem.reportId

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Report, newItem: Report): Boolean =
                    newItem == oldItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false)

        return ReportsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val report = getItem(position)
        val view = holder.itemView

        view.setOnClickListener {
            val reportDialogFragment = ReportDialogFragment(report, flag)
            reportDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme)

            val supportFragment = when (flag) {
                "MY_REPORTS" -> MyReportsFragment.supportFragment
                "REPORTS" -> ReportsFragment.supportFragment
                else -> throw Exception()
            }

            reportDialogFragment.show(supportFragment, "report_dialog_fragment")
        }

        report?.let { (holder as ReportsViewHolder).bind(report) }
    }

    class ReportsViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.reporter_image
        private val nickname = view.reporter_nickname
        private val reasonPreview = view.reporter_reason_preview
        private val messagePreview = view.reporter_message_preview

        fun bind (report: Report) {
            nickname.text = report.reporterNickname
            val customReportMessage = when {
                report.reportMessage.isNullOrEmpty() -> {
                    "Комментарий: отсутствует."
                }
                report.reportMessage.length > 7 -> {
                    "Комментарий: ${report.reportMessage.substring(0, 7)}..."
                }
                else -> {
                    "Комментарий: ${report.reportMessage}"
                }
            }
            messagePreview.text = customReportMessage

            reasonPreview.text = "Причина: ${report.reasonName?.substring(0, 12)}..."

            Picasso.get().invalidate(report.reporterImage)
            Picasso.get()
                    .load(report.reporterImage)
                    .fit()
                    .centerCrop()
                    .into(imageView)
        }
    }
}