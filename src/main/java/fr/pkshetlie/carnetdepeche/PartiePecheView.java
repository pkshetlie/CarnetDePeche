package fr.pkshetlie.carnetdepeche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import fr.pkshetlie.carnetdepeche.bdd.PriseBDD;
import fr.pkshetlie.carnetdepeche.objects.Peche;
import fr.pkshetlie.carnetdepeche.objects.Prise;
import fr.pkshetlie.carnetdepeche.utils.ListViewAdapter;
import fr.pkshetlie.carnetdepeche.utils.MenuPickerPriseFragment;

/**
 * Application Carnet de Peche
 * Pour et par les membres de traditionspeche.fr
 * Created by pierrick on 03/04/2014.
 */
public class PartiePecheView extends ActionBarActivity {
    static public int sIdPeche = -1;
    static public int sIdPrise = -1;
    private int idPeche = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partie_peche_view);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            idPeche = b.getInt("key");

            updateList();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.partie_peche_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_new_prise) {
            Intent intent = new Intent(this, PriseForm.class);
            Bundle b = new Bundle();
            b.putInt("peche_key", idPeche); //Your id
            b.putInt("prise_key", -1); //Your id
            intent.putExtras(b);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void updateList() {

        PriseBDD priseBdd = new PriseBDD(this);
        priseBdd.open();
        ArrayList<Prise> listdata = priseBdd.loadFromPecheId(idPeche);
        priseBdd.close();
        /*
        if (Datas.peches.get(idPeche).getPrises() != null) {

            for (int i = 0; i < Datas.peches.get(idPeche).getPrises().length(); i++) {
                Prise p = new Prise();
                Prise tmp = p.getFromJson(Datas.peches.get(idPeche).getPrises().getJSONObject(i));
                if (tmp.getPhoto().equals("")) {
                    tmp.setPhoto("android.resource://fr.pkshetlie.carnetdepeche/drawable/ic_launcher_web_rouge");
                }
                listdata.add(tmp);
            }
        }
        */
        ListView list = new ListViewAdapter((ListView) findViewById(R.id.liste_des_poissons)).createListViewTitlePrise(listdata, this);
        list.setOnItemClickListener(new DrawerItemClickListener());
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View v,
                                           int position, long arg3) {
                HashMap<String,String> c = (HashMap<String,String>)parent.getItemAtPosition(position);
                sIdPrise = Integer.parseInt(c.get("id"));
                sIdPeche = idPeche;

                DialogFragment newFragment = new MenuPickerPriseFragment();
                newFragment.show(getSupportFragmentManager(), "menuPicker");

                return true;
            }
        });
    }


    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (R.id.menu_open != id) {
                Intent intent = new Intent(PartiePecheView.this, PriseView.class);
                Bundle b = new Bundle();
                HashMap<String,String> c = (HashMap<String,String>)parent.getItemAtPosition(position);
                b.putInt("prise_key", Integer.parseInt(c.get("id")));
                b.putInt("peche_key", idPeche);
                intent.putExtras(b);
                intent.putExtras(b);
                startActivity(intent);
            }
        }
    }

}
