package ragalik.baraxolka.paging_feed.administrator.editors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_editors.*
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R
import ragalik.baraxolka.network.ApiClient
import ragalik.baraxolka.network.entities.ServerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditorsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isReloaded = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editorsProgressBar = view.findViewById(R.id.progress_editors)
        if (isReloaded) {
            editorsProgressBar.visibility = View.GONE
        } else {
            editorsProgressBar.visibility = View.VISIBLE
        }

        MainActivity.isActualFragment = false
        MainActivity.invalidateSearchMenu()
        MainActivity.fab.hide()

        refresherEditors!!.setOnRefreshListener {
            itemViewModel?.liveDataSource?.value?.invalidate()
            refresherEditors!!.isRefreshing = false
            editorsProgressBar.visibility = View.VISIBLE
        }

        getEditors()

        setEditorButton.setOnClickListener {
            emailFromEditText = findUserET!!.text.toString().trim()
            setEditor(emailFromEditText.toString())
        }
    }

    companion object {
        private var emailFromEditText: String? = null
        var itemViewModel: EditorsViewModel? = null
        private var isReloaded = false
        lateinit var editorsProgressBar : ProgressBar
    }

    private fun getEditors() {
        val adminAdapter = EditorsAdapter()
        EditorsRecyclerView?.layoutManager = LinearLayoutManager(activity)
        itemViewModel = ViewModelProvider(this).get(EditorsViewModel::class.java)
        itemViewModel!!.editorsPagedList.observe(viewLifecycleOwner, Observer { adminAdapter.submitList(it) })
        EditorsRecyclerView?.adapter = adminAdapter
    }

    private fun setEditor(email: String) {
        val call = ApiClient.getApi().setEditor(email)
        call.enqueue(object : Callback<ServerResponse?> {
            override fun onResponse(call: Call<ServerResponse?>, response: Response<ServerResponse?>) {
                itemViewModel?.liveDataSource?.value!!.invalidate()
                Toast.makeText(context, "$email теперь редактор", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<ServerResponse?>, t: Throwable) {
                Toast.makeText(context, "Произошла ошибка", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isReloaded = true
    }
}