package ragalik.baraxolka.utils

import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R


fun showToast(message: String) {
    Toast.makeText(MainActivity.activity, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.replaceActivity(activity: AppCompatActivity) {
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack: Boolean = true) {
    if (addStack) {
        supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(
                        R.id.constrLayout,
                        fragment
                ).commit()
    } else {
        supportFragmentManager.beginTransaction()
                .replace(
                        R.id.constrLayout,
                        fragment
                ).commit()
    }
}

fun hideKeyboard() {
    val imm: InputMethodManager = MainActivity.activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(MainActivity.activity.window.decorView.windowToken, 0)
}

fun Fragment.replaceFragment(fragment: Fragment) {
    fragmentManager?.beginTransaction()
            ?.addToBackStack(null)
            ?.replace(
                    R.id.constrLayout,
                    fragment
            )?.commit()
}