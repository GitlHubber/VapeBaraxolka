package ragalik.baraxolka.paging_feed.administrator;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ragalik.baraxolka.R;
import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.entities.ServerResponse;
import ragalik.baraxolka.other_logic.entrance.JSONParser;
import ragalik.baraxolka.paging_feed.AdAdapter;
import ragalik.baraxolka.paging_feed.favourites.FavouritesViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Administrator extends Fragment {

    private String emailFromEditText;
    private EditText email;
    private Button setEditorButton;
    private RecyclerView editorsRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static AdminViewModel itemViewModel;
    public static ProgressBar progressBar;
    private boolean isReloaded;

    public Administrator() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isReloaded = false;
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

        progressBar = view.findViewById(R.id.progress_editors);
        if (isReloaded) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }

        MainActivity.isActualFragment = false;
        MainActivity.invalidateSearchMenu();
        MainActivity.fab.hide();

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

        swipeRefreshLayout = view.findViewById(R.id.refresherEditors);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            itemViewModel.getLiveDataSource().getValue().invalidate();
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.VISIBLE);
        });

        editorsRecyclerView = view.findViewById(R.id.EditorsRecyclerView);
        getEditors();

        email = view.findViewById(R.id.findUserET);
        setEditorButton = view.findViewById(R.id.setEditorButton);
        setEditorButton.setOnClickListener(view1 -> {
            emailFromEditText = email.getText().toString().trim();
            setEditor(emailFromEditText);
            itemViewModel.getLiveDataSource().getValue().invalidate();
        });

    }

    private void getEditors () {
        AdminAdapter adminAdapter = new AdminAdapter();
        editorsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemViewModel = new ViewModelProvider(this).get(AdminViewModel.class);
        itemViewModel.getAdminPagedList().observe(getViewLifecycleOwner(), adminAdapter::submitList);

        editorsRecyclerView.setAdapter(adminAdapter);
    }

    private void setEditor (String email) {
        Call<ServerResponse> call = ApiClient.getApi().setEditor(email);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Toast.makeText(getContext(), email + " теперь редактор", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });
    }
}
