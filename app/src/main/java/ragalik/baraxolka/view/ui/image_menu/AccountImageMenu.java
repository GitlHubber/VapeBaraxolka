package ragalik.baraxolka.view.ui.image_menu;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;

import ragalik.baraxolka.R;
import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.entities.ServerResponse;
import ragalik.baraxolka.view.ui.fragment.AccountFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.*;
import static ragalik.baraxolka.view.ui.fragment.AccountFragment.outputFileUri;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountImageMenu extends BottomSheetDialogFragment {

    private static final int PICK_FILE_REQUEST = 1000;

    public static File fileWithUri;
    private AppCompatTextView account_take_image;
    private AppCompatTextView account_load_image;
    private AppCompatTextView account_delete_image;

    private LinearLayout accountMenuLinearLayout;
    private ProgressDialog pDialog;


    public AccountImageMenu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_image_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountMenuLinearLayout = view.findViewById(R.id.accountMenuLinearLayout);

        account_take_image = view.findViewById(R.id.account_take_image);
        account_load_image = view.findViewById(R.id.account_load_image);
        account_delete_image = view.findViewById(R.id.account_delete_image);

        if (MainActivity.sp.getString("image", "").equals("null")) {
            account_delete_image.setVisibility(View.GONE);

            accountMenuLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        account_take_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    int permissionStatus = getActivity().checkSelfPermission(WRITE_EXTERNAL_STORAGE);

                    if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                        takePicture((AppCompatActivity) MainActivity.activity);
                        dismiss();
                    } else {
                        getActivity().requestPermissions(new String[] {WRITE_EXTERNAL_STORAGE},
                                1);
                    }
                } else {
                    takePicture((AppCompatActivity) MainActivity.activity);
                    dismiss();
                }

            }
        });

        account_load_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    int permissionStatus = getActivity().checkSelfPermission(READ_EXTERNAL_STORAGE);

                    if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                        chooseFile();
                        dismiss();
                    } else {
                        getActivity().requestPermissions(new String[] {READ_EXTERNAL_STORAGE},
                                1);
                    }
                } else {
                    chooseFile();
                    dismiss();
                }
            }
        });

        account_delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUserImage();
                dismiss();
            }
        });
    }

    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        getActivity().startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    public static void takePicture (AppCompatActivity appCompatActivity) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileWithUri = new File(Environment.getExternalStorageDirectory() + "/DCIM/",
                MainActivity.sp.getString("email", "") + ".jpg");
        outputFileUri = FileProvider.getUriForFile(appCompatActivity, appCompatActivity.getApplicationContext().getPackageName() + ".provider", fileWithUri);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        appCompatActivity.startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }

    private void deleteUserImage () {
        pDialog = new ProgressDialog(MainActivity.activity);
        pDialog.setMessage("Удаление фотографии профиля");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        int id = MainActivity.sp.getInt("id", 0);
        String filename = MainActivity.sp.getString("image", "").substring(MainActivity.SERVER_URL.length());
        Call<ServerResponse> call = ApiClient.getApi().deleteAccountImage(id, filename);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                pDialog.dismiss();
                if (response.body() != null) {
                    Toast.makeText(MainActivity.activity, "Изображение успешно удалено!", Toast.LENGTH_LONG).show();
                    AccountFragment.accountPhoto.setImageResource(R.drawable.gradient_navigation);
                    MainActivity.navigationPhoto.setImageResource(R.drawable.gradient_navigation);

                    SharedPreferences.Editor editor = MainActivity.sp.edit();
                    editor.putString("image", "null");
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(MainActivity.activity, "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });
    }
}
