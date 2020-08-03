package ragalik.baraxolka.view.ui.fragment.dialog_fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ragalik.baraxolka.R;
import ragalik.baraxolka.view.ui.activity.SearchActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchPriceDialogFragment extends BottomSheetDialogFragment {

    private EditText fromPrice;
    private EditText toPrice;
    private TextView clearPrice;
    private TextView acceptPriceButton;
    private int from = -1;
    private int to = -1;
    private int from1 = -1;
    private int to1 = -1;

    public SearchPriceDialogFragment() {}

    public SearchPriceDialogFragment(int from1, int to1) {
        this.from1 = from1;
        this.to1 = to1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_price, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fromPrice = view.findViewById(R.id.priceFrom);
        toPrice = view.findViewById(R.id.priceTo);
        clearPrice = view.findViewById(R.id.clearPrice);
        acceptPriceButton = view.findViewById(R.id.AcceptPriceButton);

        if (from1 != -1) {
            fromPrice.setText(from1 + "");
        }
        if (to1 != -1) {
            toPrice.setText(to1 + "");
        }

        clearPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromPrice.setText("");
                toPrice.setText("");
                SearchActivity.from = from;
                SearchActivity.to = to;
                from = -1;
                to = -1;
            }
        });

        acceptPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fromPrice.getText().toString().equals("")) {
                    from = Integer.parseInt(fromPrice.getText().toString());
                }
                if (!toPrice.getText().toString().equals("")) {
                    to = Integer.parseInt(toPrice.getText().toString());
                }
                if (from == -1 && to == -1) {
                    SearchActivity.searchRequests.put("price", "ad.price > -1");
                    SearchActivity.getSearchAdsCount(SearchActivity.searchRequests);
                    SearchActivity.from = from;
                    SearchActivity.to = to;
                }
                if (from != -1 || to != -1) {
                    SearchActivity.searchRequests.put("price", getPrice());
                    SearchActivity.getSearchAdsCount(SearchActivity.searchRequests);
                    SearchActivity.from = from;
                    SearchActivity.to = to;
                    from = -1;
                    to = -1;
                }
                dismiss();
            }
        });
    }

    public String getPrice () {
        if (to == -1) {
            return "ad.price > " + (from - 1);
        }
        if (from > to && to != -1) {
            return "ad.price > " + (to - 1) + " AND ad.price < " + (from + 1);
        }
        if (from == -1) {
            return "ad.price < " + (to + 1);
        }
        return "ad.price > " + (from - 1) + " AND ad.price < " + (to + 1);
    }
}
