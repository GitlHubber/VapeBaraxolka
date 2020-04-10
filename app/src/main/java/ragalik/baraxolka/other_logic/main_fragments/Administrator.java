package ragalik.baraxolka.other_logic.main_fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ragalik.baraxolka.R;
import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.other_logic.entrance.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class Administrator extends Fragment {


    private static String url_create_product = MainActivity.SERVER_URL + "setEditor.php";
    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    private JSONParser jsonParser = new JSONParser();
    private String emailFromEditText;
    private EditText email;
    private Button setEditor;

    public Administrator() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_administrator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.administratorToolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar);
            toolbar.setTitle("Назначение редактора");
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), MainActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        MainActivity.drawer.addDrawerListener(toggle);
        toggle.syncState();


        email = view.findViewById(R.id.findUserET);
        setEditor = view.findViewById(R.id.SET_EDITOR);
        setEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailFromEditText = email.getText().toString().trim();
                new SetEditor().execute();
            }
        });

    }

    class SetEditor extends AsyncTask<String, String, Integer> {

        private int success;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Поиск редактора");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected Integer doInBackground(String[] args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("email", emailFromEditText));

            JSONObject json = jsonParser.makeHttpRequest(url_create_product, "GET", params);
            System.out.println(json);

            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    publishProgress();
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }

            return success;
        }

        @Override
        protected void onProgressUpdate(String... values) {

            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(Integer success) {

            super.onPostExecute(success);
            pDialog.dismiss();
        }
    }
}
