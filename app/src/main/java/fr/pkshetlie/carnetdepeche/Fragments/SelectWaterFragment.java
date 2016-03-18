package fr.pkshetlie.carnetdepeche.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import fr.pkshetlie.carnetdepeche.PartiePecheForm;
import fr.pkshetlie.carnetdepeche.R;


/**
 * Application Carnet de Peche
 * Pour et par les membres de traditionspeche.fr
 * Created by pierrick on 28/02/14.
 */
public class SelectWaterFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.select_eau)
                .setItems(R.array.water, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PartiePecheForm callingActivity = (PartiePecheForm) getActivity();
                        callingActivity.onUserSelectWater(which);
                    }
                });
        return builder.create();
    }
}
