package ragalik.baraxolka.other_logic.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_edit_account.*
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R
import ragalik.baraxolka.network.ApiClient
import ragalik.baraxolka.network.entities.ServerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditAccountFragment : Fragment() {

    val PHONE_NUMBER : String = "(^\\+375\\d{9})"

    companion object {
        private lateinit var edit_flag : String
        private var isRegionSelected : Boolean = false
        private var regionFromSpinner: String = ""
        private var townFromSpinner : String = ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_edit_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar_edit_activity)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        MainActivity.isActualFragment = false
        MainActivity.invalidateSearchMenu()

//        Slidr.attach(this)

        edit_flag = arguments?.let { EditAccountFragmentArgs.fromBundle(it).editFlag }.toString()

        when (edit_flag) {
            "phone" -> {
                account_edit_layout.hint = "Номер телефона"
                tw_account_edit.text = "После указания номера телефона, его НЕЛЬЗЯ будет изменить!"
                toolbar_edit_activity.title = "Добавление номера тел."
                tw_account_edit_title.text = "Укажите номер телефона"
                edit_region_layout.visibility = View.GONE
            }
            "region" -> {
                account_edit_layout.visibility = View.GONE
                tw_account_edit.text = "Вы можете изменить регион в любой момент."
                toolbar_edit_activity.title = "Указание региона"
                tw_account_edit_title.visibility = View.GONE
                EditRegionSpinner.setAdapter(ArrayAdapter.createFromResource(MainActivity.activity, R.array.Spinner_region_items, R.layout.dropdown_text_color))
                EditRegionSpinner.setOnClickListener {
                    EditRegionSpinner.showDropDown()
                }
                EditRegionSpinner.setOnItemClickListener { parent: AdapterView<*>, _: View?, position: Int, _: Long ->
                    val text = parent.getItemAtPosition(position).toString()
                    isRegionSelected = true
                    tw_select_region.text = "Укажите область проживания"
                    EditRegionTextEdit.hint = "Выберите область"
                    tw_select_town.text = "Укажите город проживания"
                    EditTownTextEdit.hint = "Выберите город"

                    lateinit var adapterTown : ArrayAdapter<CharSequence>

                    when (text) {
                        "Не указано" -> {
                            adapterTown = ArrayAdapter.createFromResource(MainActivity.activity, R.array.Nothing, R.layout.dropdown_text_color)
                            isRegionSelected = false
                        }
                        "Минск" -> {
                            adapterTown = ArrayAdapter.createFromResource(MainActivity.activity, R.array.Minsk, R.layout.dropdown_text_color)
                            tw_select_region.text = "Укажите город проживания"
                            EditRegionTextEdit.hint = "Выберите город"
                            tw_select_town.text = "Укажите район проживания"
                            EditTownTextEdit.hint = "Выберите район"
                        }
                        "Брестская обл." -> adapterTown = ArrayAdapter.createFromResource(MainActivity.activity, R.array.BrestRegion, R.layout.dropdown_text_color)
                        "Витебская обл." -> adapterTown = ArrayAdapter.createFromResource(MainActivity.activity, R.array.VitebskRegion, R.layout.dropdown_text_color)
                        "Гомельская обл." -> adapterTown = ArrayAdapter.createFromResource(MainActivity.activity, R.array.GomelRegion, R.layout.dropdown_text_color)
                        "Гродненская обл." -> adapterTown = ArrayAdapter.createFromResource(MainActivity.activity, R.array.GrodnoRegion, R.layout.dropdown_text_color)
                        "Минская обл." -> adapterTown = ArrayAdapter.createFromResource(MainActivity.activity, R.array.MinskRegion, R.layout.dropdown_text_color)
                        "Могилевская обл." -> adapterTown = ArrayAdapter.createFromResource(MainActivity.activity, R.array.MogilevRegion, R.layout.dropdown_text_color)
                        else -> {}
                    }
                    regionFromSpinner = text
                    EditTownSpinner.setAdapter(adapterTown)
                    townFromSpinner = adapterTown.getItem(0).toString()
                    EditTownSpinner.setText(townFromSpinner, false)
                    EditTownSpinner.setOnClickListener {
                        EditTownSpinner.showDropDown()
                    }
                    EditTownSpinner.onItemClickListener = OnItemClickListener { parent: AdapterView<*>, _: View?, position: Int, _: Long -> townFromSpinner = parent.getItemAtPosition(position).toString() }
                }
            }
        }

        saveEditInfoButton.setOnClickListener {
            val email = MainActivity.sp.getString("email", "")
            when (edit_flag) {
                "phone" -> {
                    val phoneNumber = account_edit_layout.editText?.text.toString()
                    if (phoneNumber.matches(PHONE_NUMBER.toRegex())) {
                        val call = ApiClient.getApi().setPhoneNumber(phoneNumber, email)
                        call.enqueue(object : Callback<ServerResponse> {
                            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                                findNavController().navigate(R.id.action_editAccountFragment_to_accountFragment)
                                Toast.makeText(activity, "Добавлен номер телефона!", Toast.LENGTH_LONG).show()
                            }

                            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                                Toast.makeText(activity, "Такой номер телефона уже существует!", Toast.LENGTH_LONG).show()
                            }
                        })
                    } else {
                        Toast.makeText(activity, "Введен некорректный номер тел.", Toast.LENGTH_LONG).show()
                    }
                }
                "region" -> {
                    account_edit_layout.visibility = View.GONE
                    if (!isRegionSelected) {
                        regionFromSpinner = "0"
                        townFromSpinner = "0"
                    }
                    val call = ApiClient.getApi().setRegion(regionFromSpinner, townFromSpinner, email)
                    call.enqueue(object : Callback<ServerResponse> {
                        override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                            findNavController().navigate(R.id.action_editAccountFragment_to_accountFragment)
                            Toast.makeText(activity, "Регион изменен", Toast.LENGTH_LONG).show()
                        }

                        override fun onFailure(call: Call<ServerResponse>, t: Throwable) {}
                    })
                }
            }
        }
    }
}
