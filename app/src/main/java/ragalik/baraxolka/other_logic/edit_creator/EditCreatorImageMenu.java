package ragalik.baraxolka.other_logic.edit_creator;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.util.ArrayList;

import ragalik.baraxolka.R;
import ragalik.baraxolka.other_logic.account.FullImageLayout;
import ragalik.baraxolka.other_logic.full_ad.FullAdViewPagerAdapter;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static ragalik.baraxolka.other_logic.edit_creator.EditCreator.adImages;
import static ragalik.baraxolka.other_logic.edit_creator.EditCreator.fileWithUri;
import static ragalik.baraxolka.other_logic.edit_creator.EditCreator.outputFileUri;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditCreatorImageMenu extends BottomSheetDialogFragment {

    LinearLayout adCreatorMenuLinearLayout;
    AppCompatTextView ad_creator_show_image;
    AppCompatTextView ad_creator_take_image;
    AppCompatTextView ad_creator_load_image;
    AppCompatTextView ad_creator_delete_image;

    Dialog dialog;

    int imageUploadCount;
    int chooseIndex;

    private ArrayList<String> uris;

    public EditCreatorImageMenu(int imagUploadCount, int chooseIndex, ArrayList<String> uris) {
        this.imageUploadCount = imagUploadCount;
        this.chooseIndex = chooseIndex;
        this.uris = uris;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ad_creator_image_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adCreatorMenuLinearLayout = view.findViewById(R.id.adCreatorMenuLinearLayout);

        dialog = new Dialog(getContext());

        ad_creator_show_image = view.findViewById(R.id.ad_creator_show_image);
        ad_creator_take_image = view.findViewById(R.id.ad_creator_take_image);
        ad_creator_load_image = view.findViewById(R.id.ad_creator_load_image);
        ad_creator_delete_image = view.findViewById(R.id.ad_creator_delete_image);

        if (imageUploadCount == chooseIndex) {
            ad_creator_show_image.setVisibility(View.GONE);
            ad_creator_delete_image.setVisibility(View.GONE);

            adCreatorMenuLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }


        ad_creator_show_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();

                dialog.setContentView(R.layout.fragment_full_image_layout);
                dialog.show();

                FullAdViewPagerAdapter fullAdViewPagerAdapter = new FullAdViewPagerAdapter(getActivity(), uris, "AD");
                ViewPager viewPager = dialog.findViewById(R.id.fullImagePager);
                viewPager.setAdapter(fullAdViewPagerAdapter);
                viewPager.setCurrentItem(chooseIndex);

//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.editCreatorFrame, new FullImageLayout(chooseIndex, uris)).addToBackStack("").commit();
            }
        });

        ad_creator_take_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    int permissionStatus = getActivity().checkSelfPermission(WRITE_EXTERNAL_STORAGE);

                    if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileWithUri = new File(Environment.getExternalStorageDirectory() + "/DCIM/",
                                "photo_" + System.currentTimeMillis() + ".jpg");
                        outputFileUri = FileProvider.getUriForFile(getContext(), getActivity().getApplicationContext().getPackageName() + ".provider", fileWithUri);
                        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                        getActivity().startActivityForResult(intent, 1);
                        dismiss();
                    } else {
                        getActivity().requestPermissions(new String[] {WRITE_EXTERNAL_STORAGE},
                                1);
                    }
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileWithUri = new File(Environment.getExternalStorageDirectory() + "/DCIM/",
                            "photo_" + System.currentTimeMillis() + ".jpg");
                    outputFileUri = FileProvider.getUriForFile(getContext(), getActivity().getApplicationContext().getPackageName() + ".provider", fileWithUri);
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    getActivity().startActivityForResult(intent, 1);
                    dismiss();
                }
            }
        });

        ad_creator_load_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    int permissionStatus = getActivity().checkSelfPermission(READ_EXTERNAL_STORAGE);

                    if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        getActivity().startActivityForResult(Intent.createChooser(intent,
                                "Select Picture"), 1);

                        dismiss();
                    } else {
                        getActivity().requestPermissions(new String[] {READ_EXTERNAL_STORAGE},
                                1);
                    }
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    getActivity().startActivityForResult(Intent.createChooser(intent,
                            "Select Picture"), 1);

                    dismiss();
                }
            }
        });

        ad_creator_delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (chooseIndex <= imageUploadCount) {
                    for (int i = chooseIndex; i < imageUploadCount - 1; ++i) {
                        adImages.get(i).setImageDrawable(adImages.get(i + 1).getDrawable());
                        EditCreator.uris.set(i, EditCreator.uris.get(i + 1));
                        EditCreator.files.set(i, EditCreator.files.get(i + 1));
                    }

                    EditCreator.uris.remove(imageUploadCount - 1);
                    EditCreator.files.remove(imageUploadCount - 1);

                    //Fix algoritm 06.05.20
                    if (imageUploadCount != adImages.size()) {
                        adImages.get(imageUploadCount).setImageDrawable(null);
                        adImages.get(imageUploadCount).setBackground(getResources().getDrawable(R.color.colorAdCreatorImage));
                    } else {
                        adImages.get(imageUploadCount - 1).setImageDrawable(null);
                        adImages.get(imageUploadCount - 1).setBackground(getResources().getDrawable(R.color.colorAdCreatorImage));
                    }

                    imageUploadCount--;
                    EditCreator.imageUploadCount--;

                    if (imageUploadCount != 0) {
                        adImages.get(imageUploadCount).setBackground(getResources().getDrawable(R.drawable.ad_creator_image_style));
                        adImages.get(imageUploadCount).setImageDrawable(getResources().getDrawable(R.drawable.ic_circle_add));
                    } else {
                        adImages.get(imageUploadCount).setImageDrawable(getResources().getDrawable(R.drawable.ic_circle_add));
                        EditCreator.mainLabel.setVisibility(View.GONE);
                    }

                    EditCreator.uploadedCounterStr = EditCreator.uploadedCounterStr.substring(0, 10) + imageUploadCount + EditCreator.uploadedCounterStr.substring(11, 16);
                    EditCreator.uploadAmountTW.setText(EditCreator.uploadedCounterStr);

                    dismiss();
                }
            }
        });

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
