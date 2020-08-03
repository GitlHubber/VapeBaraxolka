package ragalik.baraxolka.view.ui.fragment.dialog_fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ragalik.baraxolka.R;
import ragalik.baraxolka.view.ui.activity.SearchActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortDialogFragment extends BottomSheetDialogFragment {

    private RadioButton priceUp;
    private RadioButton priceDown;
    private RadioButton date;
    private RadioButton views;

    private int sortId;

    private TextView clearSort;
    private TextView acceptSortButton;

    public SortDialogFragment() {
        // Required empty public constructor
    }

    public SortDialogFragment(int sortId) {

        this.sortId = sortId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sort, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        priceUp = view.findViewById(R.id.byPriceUp);
        priceDown = view.findViewById(R.id.byPriceDown);
        date = view.findViewById(R.id.byDate);
        views = view.findViewById(R.id.byViews);

        clearSort = view.findViewById(R.id.clearSort);
        acceptSortButton = view.findViewById(R.id.AcceptSortButton);

        setAllFalseChecked();


        switch (sortId) {
            case -1 : SearchActivity.sort_field = "dateTime";
                SearchActivity.sort_type = false;
                SearchActivity.sortButton.setText("Сортировка");
                break;
            case 0 : priceUp.setChecked(true);
                SearchActivity.sort_field = "price";
                SearchActivity.sort_type = true;
                SearchActivity.sortButton.setText("Сортировка (дешевые - дорогие)");
                break;
            case 1 : priceDown.setChecked(true);
                SearchActivity.sort_field = "price";
                SearchActivity.sort_type = false;
                SearchActivity.sortButton.setText("Сортировка (дорогие - дешевые)");
                break;
            case 2 : date.setChecked(true);
                SearchActivity.sort_field = "dateTime";
                SearchActivity.sort_type = false;
                SearchActivity.sortButton.setText("Сортировка (по дате)");
                break;
            case 3 : views.setChecked(true);
                SearchActivity.sort_field = "views";
                SearchActivity.sort_type = false;
                SearchActivity.sortButton.setText("Сортировка (по просмотрам)");
                break;
            default : break;
        }

        priceUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.sort_field = "price";
                SearchActivity.sort_type = true;
                priceUp.setChecked(true);
                priceDown.setChecked(false);
                date.setChecked(false);
                views.setChecked(false);
                sortId = 0;
            }
        });
        priceDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.sort_field = "price";
                SearchActivity.sort_type = false;
                priceUp.setChecked(false);
                priceDown.setChecked(true);
                date.setChecked(false);
                views.setChecked(false);
                sortId = 1;
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.sort_field = "dateTime";
                SearchActivity.sort_type = false;
                priceUp.setChecked(false);
                priceDown.setChecked(false);
                date.setChecked(true);
                views.setChecked(false);
                sortId = 2;
            }
        });
        views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.sort_field = "views";
                SearchActivity.sort_type = false;
                priceUp.setChecked(false);
                priceDown.setChecked(false);
                date.setChecked(false);
                views.setChecked(true);
                sortId = 3;
            }
        });

        clearSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.sort_field = "dateTime";
                SearchActivity.sort_type = false;
                setAllFalseChecked();
                sortId = -1;
            }
        });

        acceptSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (sortId) {
                    case -1 : SearchActivity.sortButton.setText("Сортировка");
                        break;
                    case 0 : SearchActivity.sortButton.setText("Сортировка (дешевые - дорогие)");
                        break;
                    case 1 : SearchActivity.sortButton.setText("Сортировка (дорогие - дешевые)");
                        break;
                    case 2 : SearchActivity.sortButton.setText("Сортировка (по дате)");
                        break;
                    case 3 : SearchActivity.sortButton.setText("Сортировка (по просмотрам)");
                        break;
                    default : break;
                }

                SearchActivity.selectedSortId = sortId;
                dismiss();
            }
        });
    }

    private void setAllFalseChecked () {
        priceUp.setChecked(false);
        priceDown.setChecked(false);
        date.setChecked(false);
        views.setChecked(false);
    }
}
