package fr.pkshetlie.carnetdepeche.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import fr.pkshetlie.carnetdepeche.PriseForm;
import fr.pkshetlie.carnetdepeche.R;

/**
 * Application Carnet de Peche
 * Pour et par les membres de traditionspeche.fr
 * Created by pierrick on 28/02/14.
 */
public class SelectTypePoissonFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.select_type_poisson)
                .setItems(R.array.poissons, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PriseForm callingActivity = (PriseForm) getActivity();
                        callingActivity.onUserSelectTypePoisson(which);
                    }
                });
        return builder.create();
    }
}
