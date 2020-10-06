package ragalik.baraxolka.view.ui.fragment

import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_code.*
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R
import ragalik.baraxolka.utils.*
import ragalik.baraxolka.view.ui.activity.RegisterActivity


class EnterCodeFragment(val phoneNumber: String, val id: String) : BaseFragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = phoneNumber
        register_input_code.addTextChangedListener(AppTextWatcher {
            val string = register_input_code.text.toString()
            if (string.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = register_input_code.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                showToast("Добро пожаловать")
                (activity as RegisterActivity).replaceActivity(MainActivity())
//                val uid = AUTH.currentUser?.uid.toString()
//                val dateMap = mutableMapOf<String, Any>()
//                dateMap[CHILD_ID] = uid
//                dateMap[CHILD_PHONE] = phoneNumber
//                dateMap[CHILD_USERNAME] = uid
//
//                REF_DATABASE_ROOT.child(NODE_PHONES).child(phoneNumber).setValue(uid)
//                        .addOnFailureListener { task1 ->
//                            showToast(task1.message.toString())
//                        }
//                        .addOnSuccessListener {
//                            REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
//                                    .addOnSuccessListener {
//                                        showToast("Добро пожаловать")
//                                        (activity as RegisterActivity).replaceActivity(MainActivity())
//                                    }
//                                    .addOnFailureListener { task2 ->
//                                        showToast(task2.message.toString())
//                                    }
//                        }
            } else {
                showToast(task.exception?.message.toString())
            }
        }
    }
}