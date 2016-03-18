package fr.pkshetlie.carnetdepeche.Helpers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import fr.pkshetlie.carnetdepeche.R;

/**
 * Created by pierrick on 17/03/2016.
 */
public class LazyAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;

    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.prise_list, null);

        TextView type = (TextView) vi.findViewById(R.id.poisson_type);
        TextView stats = (TextView) vi.findViewById(R.id.poisson_stats);
        ImageView thumb_image = (ImageView) vi.findViewById(R.id.poisson_photo);

        HashMap<String, String> fish = new HashMap<String, String>();
        fish = data.get(position);

        // Setting all values in listview
        type.setText(fish.get("poisson_type"));
        stats.setText(fish.get("poisson_stats"));
        imageLoader.DisplayImage(fish.get("poisson_photo"), thumb_image);
        return vi;
    }
}
