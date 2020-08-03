package ragalik.baraxolka.view.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_filter.*
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R
import ragalik.baraxolka.network.ApiClient
import ragalik.baraxolka.network.entities.AdsCount
import ragalik.baraxolka.network.entities.CategoryResponse
import ragalik.baraxolka.network.entities.Subcategories
import ragalik.baraxolka.view.ui.fragment.AdsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FilterActivity : AppCompatActivity() {


    companion object {
        var categoryFromSpinner = ""
        var subcategoryFromSpinner = ""
        private var adsCount : Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_filter_activity)
        toolbar.title = "Сортировка объявлений"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        Slidr.attach(this)
        createCategorySpinner()
        filterCategoryButton.isClickable = false

        filterCategoryButton.setOnClickListener {
            AdsFragment.isFilteredByCategories = true
            AdsFragment.isFilteredAds = false
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }
    }

    private fun createCategorySpinner() {
        ApiClient.getApi().categoriesWithSubcategories.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                val subcategoriesHashMap = HashMap<String?, ArrayList<Subcategories>?>()
                val categories = ArrayList<String?>()
                categories.add("Не выбрано")
                for (i in response.body()!!.categories!!.indices) {
                    categories.add(response.body()!!.categories!![i].category_name)
                    subcategoriesHashMap[response.body()!!.categories!![i].category_name] = response.body()!!.categories!![i].subcategories
                }
                SF_categorySpinner.setAdapter(applicationContext?.let { ArrayAdapter(it, R.layout.dropdown_text_color, categories) })
                SF_categorySpinner.setOnClickListener { SF_categorySpinner.showDropDown() }
                SF_categorySpinner.onItemClickListener = AdapterView.OnItemClickListener { parent: AdapterView<*>, _: View?, position: Int, _: Long ->
                    val subcategories = ArrayList<String?>()
                    subcategories.add("Не выбрано")
                    if (subcategoriesHashMap[parent.getItemAtPosition(position).toString()] != null) {
                        if (position != 0) {
                            SF_subcategoryLayout.visibility = View.VISIBLE
                            for (i in subcategoriesHashMap[parent.getItemAtPosition(position).toString()]!!.indices) {
                                subcategories.add(subcategoriesHashMap[parent.getItemAtPosition(position).toString()]!![i].subcategory_name)
                            }
                            categoryFromSpinner = parent.getItemAtPosition(position).toString()
                            if (categoryFromSpinner == "Не выбрано") {
                                categoryFromSpinner = ""
                            }
                            getSearchAdsCount()
                            val adapterSubcategory = applicationContext?.let { ArrayAdapter(it, R.layout.dropdown_text_color, subcategories) }
                            SF_subcategorySpinner.setAdapter(adapterSubcategory)
                            SF_subcategorySpinner.setOnClickListener { SF_subcategorySpinner.showDropDown() }
                            subcategoryFromSpinner = ""
                            SF_subcategorySpinner.setText(adapterSubcategory?.getItem(0), false)
                            if (subcategoriesHashMap[parent.getItemAtPosition(position).toString()]!!.size == 1) {
                                subcategoryFromSpinner = adapterSubcategory?.getItem(1).toString()
                                SF_subcategorySpinner.setText(subcategoryFromSpinner, false)
                            }
                        } else {
                            SF_subcategoryLayout.visibility = View.GONE
                        }
                    } else {
                        SF_subcategoryLayout.visibility = View.GONE
                    }
                }
                SF_subcategorySpinner.onItemClickListener = AdapterView.OnItemClickListener { parent: AdapterView<*>, _: View?, position: Int, _: Long ->
                    subcategoryFromSpinner = if (parent.getItemAtPosition(position).toString() == "Не выбрано") {
                        ""
                    } else {
                        parent.getItemAtPosition(position).toString()
                    }
                    getSearchAdsCount()
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Проверьте интернет соединение.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getSearchAdsCount() {
        var request = ""

        if (categoryFromSpinner != "") {
            request = "categories.category_name = '$categoryFromSpinner'"
            if (subcategoryFromSpinner != "") {
                request += " AND subcategories.subcategory_name = '$subcategoryFromSpinner'"
            }
        } else {
            request = "ad.title LIKE '%%'"
        }

        val call = ApiClient.getApi().getSearchAdsCount(1, request)
        call.enqueue(object : Callback<AdsCount?> {
            override fun onResponse(call: Call<AdsCount?>, response: Response<AdsCount?>) {
                if (response.body() != null) {
                    adsCount = response.body()!!.count!!
                    if (adsCount <= 0) {
                        filterCategoryButton.isClickable = false
                    } else if (adsCount > 0) {
                        filterCategoryButton.isClickable = true
                    }

                    filterCategoryButton.text = "Показать ($adsCount)"
                }
            }

            override fun onFailure(call: Call<AdsCount?>, t: Throwable) {}
        })
    }
}
