package ragalik.baraxolka.paging_feed.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.HashMap;

import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.entities.AdsCount;
import ragalik.baraxolka.network.entities.CategoryResponse;
import ragalik.baraxolka.network.entities.Subcategories;
import ragalik.baraxolka.old_xyina.Ad;
import ragalik.baraxolka.R;
import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.paging_feed.ads.ADS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements  SearchView.OnQueryTextListener{

    private SearchView searchView;
    private ArrayList<Ad> ads;
    //private ListViewAdapter adapter;
    private LinearLayout subcategorySearchLayout;
    private AppCompatAutoCompleteTextView categorySpinner;
    private AppCompatAutoCompleteTextView subcategorySpinner;
    private String categoryFromSpinner;
    private String subcategoryFromSpinner;
    public static AppCompatActivity activity;
    private static AppCompatButton showAdsButton;
    private static AppCompatTextView priceButton;
    public static AppCompatTextView sortButton;
    private static int adsCount;

    public static HashMap<String, String> searchRequests;
    public static String sort_field = "dateTime";
    public static boolean sort_type = false;
    public static int from;
    public static int to;
    public static int selectedSortId;
    private boolean isCategorySelected;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar_edit_activity);
        toolbar.setTitle("Поиск");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Slidr.attach(this);

        ads = new ArrayList<>();
        searchRequests = new HashMap<>();
        activity = this;
        showAdsButton = findViewById(R.id.showAdsButton);
       // adapter = new ListViewAdapter(this, ads);

        from = -1;
        to = -1;
        selectedSortId = -1;

        isCategorySelected = false;

        searchView = (SearchView) findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(this);

        subcategorySpinner = findViewById(R.id.subcategorySearchSpinner);
        categorySpinner = findViewById(R.id.categorySearchSpinner);
        subcategorySearchLayout = findViewById(R.id.subcategorySearchLayout);
        subcategorySearchLayout.setVisibility(View.GONE);

        getSearchAdsCount(searchRequests);

        createCategorySpinner();

        priceButton = findViewById(R.id.SearchPriceButton);
        priceButton.setOnClickListener(v -> {
            SearchPrice searchPrice = new SearchPrice(from, to);
            searchPrice.show(activity.getSupportFragmentManager(), "");
        });

        sortButton = findViewById(R.id.SearchSortButton);
        switch (selectedSortId) {
            case -1 : SearchActivity.sort_field = "dateTime";
                SearchActivity.sort_type = false;
                SearchActivity.sortButton.setText("Сортировка");
                break;
            case 0 : SearchActivity.sort_field = "price";
                SearchActivity.sort_type = true;
                SearchActivity.sortButton.setText("Сортировка (дешевые - дорогие)");
                break;
            case 1 : SearchActivity.sort_field = "price";
                SearchActivity.sort_type = false;
                SearchActivity.sortButton.setText("Сортировка (дорогие - дешевые)");
                break;
            case 2 : SearchActivity.sort_field = "dateTime";
                SearchActivity.sort_type = false;
                SearchActivity.sortButton.setText("Сортировка (по дате)");
                break;
            case 3 : SearchActivity.sort_field = "views";
                SearchActivity.sort_type = false;
                SearchActivity.sortButton.setText("Сортировка (по просмотрам)");
                break;
            default : break;
        }
        sortButton.setOnClickListener(v -> {
            Sort sort = new Sort(selectedSortId);
            sort.show(activity.getSupportFragmentManager(), "");
        });

        showAdsButton = findViewById(R.id.showAdsButton);
        showAdsButton.setOnClickListener(v -> {
            ADS.isFilteredAds = true;
            Intent myIntent = new Intent(activity, MainActivity.class);
            v.getContext().startActivity(myIntent);
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final String text = "ad.title LIKE '%" + newText + "%'";
        searchRequests.put("title", text);
        getSearchAdsCount(searchRequests);
        return false;
//        adapter.filter(text);
    }

    private void createCategorySpinner() {

        ApiClient.getApi().getCategoriesWithSubcategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                HashMap<String, ArrayList<Subcategories>> subcategoriesHashMap = new HashMap<>();
                ArrayList<String> categories = new ArrayList<>();
                categories.add("Не выбрано");

                for (int i = 0; i < response.body().getCategories().size(); ++i) {
                    categories.add(response.body().getCategories().get(i).getCategory_name());
                    subcategoriesHashMap.put(response.body().getCategories().get(i).getCategory_name(), response.body().getCategories().get(i).getSubcategories());
                }

                categorySpinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_text_color, categories));
                categorySpinner.setOnItemClickListener((parent, view, position, id) -> {
                    ArrayList<String> subcategories = new ArrayList<>();
                    subcategories.add("Не выбрано");

                    if (subcategoriesHashMap.get(parent.getItemAtPosition(position).toString()) != null) {
                        if (position != 0) {
                            subcategorySearchLayout.setVisibility(View.VISIBLE);

                            isCategorySelected = true;

                            for (int i = 0; i < subcategoriesHashMap.get(parent.getItemAtPosition(position).toString()).size(); ++i) {
                                subcategories.add(subcategoriesHashMap.get(parent.getItemAtPosition(position).toString()).get(i).getSubcategory_name());
                            }

                            if (searchRequests.containsKey("subcategories")) {
                                searchRequests.remove("subcategories");
                            }

                            if (isCategorySelected) {
                                categoryFromSpinner = "categories.category_name = '" + parent.getItemAtPosition(position).toString() + "'";
                                searchRequests.put("category", categoryFromSpinner);
                            } else {
                                categoryFromSpinner = "";
                                if (searchRequests.containsKey("categories")) {
                                    searchRequests.remove("category");
                                }
                            }
                            getSearchAdsCount(searchRequests);

                            categoryFromSpinner = parent.getItemAtPosition(position).toString();

                            subcategorySpinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_text_color, subcategories));
                            if (subcategoriesHashMap.get(parent.getItemAtPosition(position).toString()).size() == 1) {
                                subcategorySpinner.setSelection(1);
                            }
                        } else {
                            subcategorySearchLayout.setVisibility(View.GONE);
                        }
                    } else {
                        subcategorySearchLayout.setVisibility(View.GONE);
                    }
                });

                subcategorySpinner.setOnItemClickListener((parent, view, position, id) -> {
                    if (parent.getItemAtPosition(position).toString().equals("Не выбрано")) {
                        subcategoryFromSpinner = "";
                        if (searchRequests.containsKey("subcategory")) {
                            searchRequests.remove("subcategory");
                            getSearchAdsCount(searchRequests);
                        }
                    } else {
                        searchRequests.remove("category");
                        subcategoryFromSpinner = "subcategories.subcategory_name = '" + parent.getItemAtPosition(position).toString() + "'";
                        searchRequests.put("subcategory", subcategoryFromSpinner);
                        getSearchAdsCount(searchRequests);
                    }
                });
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Toast.makeText(SearchActivity.activity, "Не удалось получить список категорий. Проверьте интернет соединение.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getSearchAdsCount (HashMap<String, String> requests) {
        String request = "";

        for (String value : requests.values()) {
            request += value + " AND ";
        }
        if (requests.size() > 0) {
            request = request.substring(0, request.length() - 5);
        }
        if (requests.size() == 0) {
            request = "ad.title LIKE '%%'";
        }

        Call<AdsCount> call = ApiClient.getApi().getSearchAdsCount(1, request);
        call.enqueue(new Callback<AdsCount>() {
            @Override
            public void onResponse(Call<AdsCount> call, Response<AdsCount> response) {
                if (response.body() != null) {
                    adsCount = response.body().getCount();

                    if (adsCount <= 0) {
                        showAdsButton.setClickable(false);
                    } else if (adsCount > 0) {
                        showAdsButton.setClickable(true);
                    }

                    if (from != -1 && to == -1) {
                        priceButton.setText("Цена (от " + from + " Руб.)");
                    } else if (from == -1 && to != -1) {
                        priceButton.setText("Цена (до " + to + " Руб.)");
                    } else if (from != -1 && to != -1) {
                        if (from > to) {
                            priceButton.setText("Цена (от " + to + " до " + from + " Руб.)");
                        } else {
                            priceButton.setText("Цена (от " + from + " до " + to + " Руб.)");
                        }
                    } else if (from != 0 && to != 0) {
                        priceButton.setText("Цена");
                    }

                    showAdsButton.setText("Показать (" + adsCount + ")");
                }
            }

            @Override
            public void onFailure(Call<AdsCount> call, Throwable t) { }
        });
    }
}
