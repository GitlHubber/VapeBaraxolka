package ragalik.baraxolka.other_logic.full_ad

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.italic
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_send_report_dialog.*
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R
import ragalik.baraxolka.network.ApiClient
import ragalik.baraxolka.network.entities.ServerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class SendReportDialogFragment(private val adId : Int) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_send_report_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        send_report_button.setOnClickListener {
            sendReport()
        }
    }

    private fun sendReport () {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.forLanguageTag("ru"))
        dateFormat.timeZone = TimeZone.getTimeZone("GMT+3")
        val datetime = dateFormat.format(Date())
        val message = if (send_report_message_layout.editText?.text.isNullOrEmpty()) "" else send_report_message_layout.editText?.text.toString()
        val call = ApiClient.getApi().insertAdReport(3, message, datetime, adId, MainActivity.sp.getInt("id", 0))
        call.enqueue(object : Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Toast.makeText(FullAdActivity.appCompatActivity, "Жалоба отправлена!", Toast.LENGTH_LONG).show()
                dialog?.dismiss()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                dialog?.dismiss()
            }
        })
    }
}