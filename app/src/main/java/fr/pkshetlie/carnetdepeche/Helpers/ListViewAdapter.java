package fr.pkshetlie.carnetdepeche.Helpers;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import fr.pkshetlie.carnetdepeche.MainActivity;
import fr.pkshetlie.carnetdepeche.PartiePecheView;
import fr.pkshetlie.carnetdepeche.PriseView;
import fr.pkshetlie.carnetdepeche.R;
import fr.pkshetlie.carnetdepeche.bdd.PecheBDD;
import fr.pkshetlie.carnetdepeche.objects.Peche;
import fr.pkshetlie.carnetdepeche.objects.Prise;

/**
 * Application Carnet de Peche
 * Pour et par les membres de traditionspeche.fr
 * Created by pierrick on 02/02/14.
 */
public class ListViewAdapter {
    private final ListView maListViewPerso;
    private final ArrayList<HashMap<String, String>> listItem;


    public ListViewAdapter(ListView viewById) {
        maListViewPerso = viewById;
        listItem = new ArrayList<HashMap<String, String>>();
    }


    public ListView createListViewTitle(MainActivity context) {
        PecheBDD p = new PecheBDD(context);
        p.open();
        ArrayList<Peche> peches = p.getAll();
        p.close();
        if (peches != null) {
            for (Peche peche : peches) {
                HashMap<String, String> map;
                map = new HashMap<String, String>();
                map.put("titre", peche.getTitre());
                map.put("date", peche.getDateDebut());
                map.put("id", "" + peche.getId());

                listItem.add(map);
            }
        }

        SimpleAdapter mSchedule = new SimpleAdapter(context, listItem, R.layout.peche_list,
                new String[]{
                        "titre",
                        "date"//,
                        //"description"
                }, new int[]{
                R.id.title_l
                , R.id.date_l
                //, R.id.description
        });

        //On attribut à notre listView l'adapter que l'on vient de créer
        maListViewPerso.setAdapter(mSchedule);
        return maListViewPerso;

    }

    public ListView createListViewTitlePrise(ArrayList<Prise> priseA, Activity context) {

        if (priseA != null) {
            for (Prise prise : priseA) {
                HashMap<String, String> map;
                map = new HashMap<String, String>();
                map.put("id", "" + prise.getId());
                map.put("poisson_type", prise.getType());
                map.put("poisson_stats", prise.getTaille() + " cm, " + prise.getPoids() + " Kg");
                map.put("poisson_photo", prise.getPhoto());

                listItem.add(map);
            }
        }

        LazyAdapter adapter = new LazyAdapter(context, listItem);
/*

 SimpleAdapter mSchedule = new SimpleAdapter(context, listItem, R.layout.prise_list,
                new String[]{
                        "poisson_type",
                        "poisson_stats",
                        "poisson_photo"
                }, new int[]{
                R.id.poisson_type
                , R.id.poisson_stats
                , R.id.poisson_photo
        });


 */


        maListViewPerso.setAdapter(adapter);


        return maListViewPerso;

    }
}
