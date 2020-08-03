package ragalik.baraxolka.utils;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.entities.ServerResponse;
import ragalik.baraxolka.network.entities.Ad;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetDeleteBookmark {

    private static SetDeleteBookmark instance;

    public static SetDeleteBookmark getInstance () {
        if (instance == null) {
            instance = new SetDeleteBookmark();
        }
        return instance;
    }

    public void setDeleteBookmark (int ad_id, CheckBox bookmark, String flag, Context context, Ad ad) {
        int user_id = MainActivity.sp.getInt("id", 0);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.forLanguageTag("ru"));
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        String datetime = formatter.format(date);

        Call<ServerResponse> call = ApiClient.getApi().setDeleteBookmark(ad_id, user_id, datetime, flag);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.body() != null && response.body().getSuccess() == 1) {
                    if (flag.equals("set")) {
                        Toast.makeText(context, "Объявление добавлено в закладки", Toast.LENGTH_SHORT).show();
                        bookmark.setChecked(true);
                        ad.setFavourite(true);
                    } else if (flag.equals("delete")) {
                        Toast.makeText(context, "Объявление убрано из закладок", Toast.LENGTH_SHORT).show();
                        bookmark.setChecked(false);
                        ad.setFavourite(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(context, "Произошла ошибка!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
