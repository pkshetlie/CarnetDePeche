package fr.pkshetlie.carnetdepeche;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.HashMap;

import fr.pkshetlie.carnetdepeche.objects.Peche;
import fr.pkshetlie.carnetdepeche.utils.ListViewAdapter;
import fr.pkshetlie.carnetdepeche.utils.MenuPickerFragment;

public final class MainActivity extends ActionBarActivity implements View.OnClickListener {
    static public final boolean pubActive = true;
    static public int idPeche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Parties de pêche");
        updateList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_new_peche) {
            startActivity(new Intent(this, PartiePecheForm.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateList(boolean loadbefore) {
        ListView list = new ListViewAdapter((ListView) findViewById(R.id.liste_des_peches)).createListViewTitle(this);
        list.setOnItemClickListener(new DrawerItemClickListener());
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                idPeche = index;

                DialogFragment newFragment = new MenuPickerFragment();
                newFragment.show(getSupportFragmentManager(), "menuPicker");

                return true;
            }
        });
    }

    public void updateList() {
        ListView list = new ListViewAdapter((ListView) findViewById(R.id.liste_des_peches)).createListViewTitle(this);
        list.setOnItemClickListener(new DrawerItemClickListener());
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View v,
                                           int position, long arg3) {

                HashMap<String,String> c = (HashMap<String,String>)parent.getItemAtPosition(position);
                idPeche = Integer.parseInt(c.get("id"));

                DialogFragment newFragment = new MenuPickerFragment();
                newFragment.show(getSupportFragmentManager(), "menuPicker");

                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.action_new_peche) {
            startActivity(new Intent(this, PartiePecheForm.class));
        }
    }


    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this, PartiePecheView.class);
            Bundle b = new Bundle();

            HashMap<String,String> c = (HashMap<String,String>)parent.getItemAtPosition(position);

            b.putInt("key",Integer.parseInt(c.get("id"))); //Your id
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}
