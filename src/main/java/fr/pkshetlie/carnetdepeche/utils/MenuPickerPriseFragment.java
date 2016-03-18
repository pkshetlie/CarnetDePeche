/*
 * Copyright (c) 2014. Les scripts sont la propriété exclusive de pierrick pobelle, toutes modifications ou revente est interdite.
 */

package fr.pkshetlie.carnetdepeche.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import org.json.JSONException;

import fr.pkshetlie.carnetdepeche.PartiePecheView;
import fr.pkshetlie.carnetdepeche.PriseForm;
import fr.pkshetlie.carnetdepeche.PriseView;
import fr.pkshetlie.carnetdepeche.R;
import fr.pkshetlie.carnetdepeche.bdd.PriseBDD;

/**
 * Application Carnet de Peche
 * Pour et par les membres de traditionspeche.fr
 * Created by pierrick on 28/02/14.
 */
public class MenuPickerPriseFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.options)
                .setItems(R.array.menu_long_click, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        Bundle b = new Bundle();
                        switch (which) {
                            case 0:
                                intent = new Intent(getActivity(), PriseForm.class);
                                b.putInt("peche_key", PartiePecheView.sIdPeche); //Your id
                                b.putInt("prise_key", PartiePecheView.sIdPrise); //Your id
                                intent.putExtras(b);
                                startActivity(intent);
                                break;

                            case 1:
                                final Activity t = getActivity();
                                new AlertDialog.Builder(t)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle(R.string.delete)
                                        .setMessage(R.string.you_are_going_to_delete)
                                        .setPositiveButton(R.string.no, null)
                                        .setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                PriseBDD prise = new PriseBDD(t);
                                                prise.delete(PartiePecheView.sIdPrise);
                                                ((PartiePecheView) t).updateList();
                                            }
                                        }).show();

                                break;

                            case 2:
                                intent = new Intent(getActivity(), PriseView.class);
                                b.putInt("peche_key", PartiePecheView.sIdPeche); //Your id
                                b.putInt("prise_key", PartiePecheView.sIdPrise); //Your id
                                intent.putExtras(b);
                                startActivity(intent);
                                break;
                        }


                    }
                });
        return builder.create();
    }
}
