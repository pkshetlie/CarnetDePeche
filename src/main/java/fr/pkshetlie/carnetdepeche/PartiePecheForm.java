package fr.pkshetlie.carnetdepeche;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.pkshetlie.carnetdepeche.bdd.PecheBDD;
import fr.pkshetlie.carnetdepeche.objects.Peche;
import fr.pkshetlie.carnetdepeche.utils.DatePickerFragment;
import fr.pkshetlie.carnetdepeche.utils.SelectTypeFragment;
import fr.pkshetlie.carnetdepeche.utils.SelectWaterFragment;
import fr.pkshetlie.carnetdepeche.utils.TimePickerFragment;

public final class PartiePecheForm extends ActionBarActivity implements View.OnClickListener {
    private int editMode = -1;
    private int currentViewTime = 0;
    private int currentViewDay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partie_peche_form);

        findViewById(R.id.date_debut).setOnClickListener(this);
        findViewById(R.id.date_fin).setOnClickListener(this);
        findViewById(R.id.heure_debut).setOnClickListener(this);
        findViewById(R.id.heure_fin).setOnClickListener(this);
        findViewById(R.id.select_type).setOnClickListener(this);
        findViewById(R.id.select_eau).setOnClickListener(this);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            editMode = b.getInt("key");
            loadPeche(editMode);
        }
    }

    @Override
    public void onBackPressed() {
        createPeche();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.heure_debut || id == R.id.heure_fin) {
            currentViewTime = id;
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(getSupportFragmentManager(), "timePicker");
        } else if (id == R.id.date_debut || id == R.id.date_fin) {
            currentViewDay = id;
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
        } else if (id == R.id.select_type) {
            DialogFragment newFragment = new SelectTypeFragment();
            newFragment.show(getSupportFragmentManager(), "select");
        } else if (id == R.id.select_eau) {
            DialogFragment newFragment = new SelectWaterFragment();
            newFragment.show(getSupportFragmentManager(), "select");
        }
    }

    private void loadPeche(int id) {
        PecheBDD pecheBdd = new PecheBDD(this);
        pecheBdd.open();
        Peche peche = pecheBdd.getOneId(id);
        pecheBdd.close();
        if(peche == null){
            return;
        }
        ((EditText) findViewById(R.id.title)).setText(peche.getTitre());
        String[] date_debut = peche.getDateDebut().split(" ");
        String[] date_fin = peche.getDateFin().split(" ");
        ((Button) findViewById(R.id.date_debut)).setText(date_debut[0]);
        ((Button) findViewById(R.id.heure_debut)).setText(date_debut[1]);
        ((Button) findViewById(R.id.heure_fin)).setText(date_fin[1]);
        ((Button) findViewById(R.id.date_fin)).setText(date_fin[0]);
        ((Button) findViewById(R.id.select_type)).setText(peche.getTypePeche());
        ((Button) findViewById(R.id.select_eau)).setText(peche.getTypeEau());
        ((EditText) findViewById(R.id.comment)).setText(peche.getCommentaire());
    }

    private void createPeche() {
        final Peche peche = new Peche();
        peche.setTitre(((EditText) findViewById(R.id.title)).getText().toString());
        peche.setDateDebut(
                ((TextView) findViewById(R.id.date_debut)).getText().toString() + " " +
                ((TextView) findViewById(R.id.heure_debut)).getText().toString()
        );
        peche.setDateFin(
                ((TextView) findViewById(R.id.date_fin)).getText().toString() + " " +
                ((TextView) findViewById(R.id.heure_fin)).getText().toString()
        );
        peche.setTypePeche(((Button) findViewById(R.id.select_type)).getText().toString());
        peche.setTypeEau(((Button) findViewById(R.id.select_eau)).getText().toString());
        peche.setCommentaire(((EditText) findViewById(R.id.comment)).getText().toString());
        if (!peche.getTitre().equals("")) {
            PecheBDD pecheBdd = new PecheBDD(this);
            pecheBdd.open();
            if (editMode != -1) {
                peche.setId(editMode);
                pecheBdd.update(peche);
            } else {
                pecheBdd.insert(peche);
            }
            pecheBdd.close();
            finish();

        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.no_title)
                    .setMessage(R.string.go_back_ask_no_title)
                    .setPositiveButton(R.string.yes, null)
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PartiePecheForm.this.finish();
                        }
                    }).show();
        }


    }

    public void onUserSelectValueTime(String s) {
        if (currentViewTime != 0) {
            Button txt = (Button) findViewById(currentViewTime);
            txt.setText(s);
        }
    }

    public void onUserSelectValueDay(String s) {

        if (currentViewDay != 0) {
            Button txt = (Button) findViewById(currentViewDay);
            txt.setText(s);
        }
    }

    public void onUserSelectType(int id) {
        String str = getResources().getStringArray(R.array.type_de_peche)[id];
        Button txt = (Button) findViewById(R.id.select_type);
        txt.setText(str);
    }

    public void onUserSelectWater(int id) {
        String str = getResources().getStringArray(R.array.water)[id];
        Button txt = (Button) findViewById(R.id.select_eau);
        txt.setText(str);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.partie_peche_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_peche_save) {
            createPeche();
            return true;
        } else if (id == android.R.id.home) {
            createPeche();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
