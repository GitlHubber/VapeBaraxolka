package ragalik.baraxolka.old_xyina;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {


    Context mContext;
    LayoutInflater inflater;
    private List<Ad> animalNamesList = null;
    private ArrayList<Ad> arraylist;

    public ListViewAdapter(Context context, List<Ad> animalNamesList) {
        mContext = context;
        this.animalNamesList = animalNamesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(animalNamesList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return animalNamesList.size();
    }

    @Override
    public Ad getItem(int position) {
        return animalNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

//    public View getView(final int position, View view, ViewGroup parent) {
//        final ViewHolder holder;
//        if (view == null) {
//            holder = new ViewHolder();
////            view = inflater.inflate(R.layout.list_view_items, null);
////            holder.name = (TextView) view.findViewById(R.id.name);
//            view.setTag(holder);
//        } else {
//            holder = (ViewHolder) view.getTag();
//        }
//
////        holder.name.setText(animalNamesList.get(position).getTitle());
//        return view;
//    }

//    public void filter(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        animalNamesList.clear();
//        if (charText.length() == 0) {
//            animalNamesList.addAll(arraylist);
//        } else {
//            for (Ad wp : arraylist) {
//                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    animalNamesList.add(wp);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }

}
