package ragalik.baraxolka.other_logic.full_ad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ragalik.baraxolka.R;
import ragalik.baraxolka.other_logic.account.FullImageLayout;

public class FullAdViewPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<String> urls;
    String flag;

    public FullAdViewPagerAdapter(Context context, ArrayList<String> urls, String flag) {
        this.context = context;
        this.urls = urls;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        PhotoView imageView = new PhotoView(context);

        if (flag.equals("AD")) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction fragmentTransaction = FullAdActivity.appCompatActivity.getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fullAdCoordinator, new FullImageLayout(position, urls)).addToBackStack("").commit();
                }
            });

            Picasso.get().invalidate(urls.get(position));
            Picasso.get()
                    .load(urls.get(position))
                    .fit()
                    .centerCrop()
                    .into(imageView);

        } else if (flag.equals("Image")) {
            Picasso.get().invalidate(urls.get(position));
            Picasso.get()
                    .load(urls.get(position))
                    .fit()
                    .centerInside()
                    .into(imageView);
        }

        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
