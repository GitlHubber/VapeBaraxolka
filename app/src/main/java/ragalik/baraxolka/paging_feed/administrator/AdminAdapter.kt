package ragalik.baraxolka.paging_feed.administrator

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_ad.view.*
import kotlinx.android.synthetic.main.item_editor.view.*
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R
import ragalik.baraxolka.network.ApiClient
import ragalik.baraxolka.network.entities.ServerResponse
import ragalik.baraxolka.network.entities.User
import ragalik.baraxolka.paging_feed.AdAdapter
import ragalik.baraxolka.paging_feed.seller.SellerProfileActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminAdapter : PagedListAdapter<User, RecyclerView.ViewHolder>(AdminAdapter.AD_COMPARATOR) {

    companion object {
        var activity = MainActivity()
        var reasonPosition = 1
        private val AD_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                    oldItem.email == newItem.email

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                    newItem == oldItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_editor, parent, false)
        return AdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = getItem(position)
        val view = holder.itemView

        view.setOnClickListener {
            val myIntent = Intent(MainActivity.activity, SellerProfileActivity::class.java)
            myIntent.putExtra("sellerId", user?.id)
            MainActivity.activity.startActivity(myIntent)
        }

        view.removeEditorButton.setOnClickListener {
            removeEditor(user?.email.toString(), it)
            Administrator.itemViewModel.liveDataSource.value?.invalidate()
        }

        user?.let { (holder as AdminViewHolder).bind(user) }
    }

    private fun removeEditor (email : String, view : View) {
        val call = ApiClient.getApi().removeEditor(email)
        call.enqueue(object : Callback<ServerResponse> {

            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                Toast.makeText(view.context, "$email больше не редактор", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Toast.makeText(view.context, "Ошибка удаления редактора", Toast.LENGTH_LONG).show()
            }
        })
    }

    class AdminViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.editor_image
        private val nickname = view.editor_nickname
        private val email = view.editor_email

        fun bind (user: User) {
            nickname.text = user.nickname
            email.text = user.email

            Picasso.get().invalidate(user.image)
            Picasso.get()
                    .load(user.image)
                    .fit()
                    .centerCrop()
                    .into(imageView)
        }
    }
}