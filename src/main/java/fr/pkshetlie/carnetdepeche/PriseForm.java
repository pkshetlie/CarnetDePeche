package fr.pkshetlie.carnetdepeche;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import fr.pkshetlie.carnetdepeche.bdd.PecheBDD;
import fr.pkshetlie.carnetdepeche.bdd.PriseBDD;
import fr.pkshetlie.carnetdepeche.objects.Peche;
import fr.pkshetlie.carnetdepeche.objects.Prise;
import fr.pkshetlie.carnetdepeche.utils.SelectTypePoissonFragment;


public final class PriseForm extends ActionBarActivity implements View.OnClickListener {
    private static final int ACTION_REQUEST_CAMERA = 1;
    private static final int ACTION_REQUEST_GALLERY = 2;
    private int idPeche = -1;
    private int idPrise = -1;
    private Peche currentPeche;
    private Uri initialURI;

    public void askToChoose() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PriseForm.this);
        builder.setTitle("Origine de la photo");
        builder.setItems(new CharSequence[]{"Galerie", "Appareil photo"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                // GET IMAGE FROM THE GALLERY
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");

                                Intent chooser = Intent.createChooser(intent, "Choisir une image");
                                startActivityForResult(chooser, ACTION_REQUEST_GALLERY);

                                break;

                            case 1:
                                Intent getCameraImage = new Intent("android.media.action.IMAGE_CAPTURE");

                                File cameraFolder;
                                File root = Environment.getExternalStorageDirectory();

                                if (android.os.Environment.getExternalStorageState().equals
                                        (android.os.Environment.MEDIA_MOUNTED))
                                    cameraFolder = new File(root.getAbsolutePath() + PriseForm.this.getFilesDir().getPath());
                                else
                                    cameraFolder = PriseForm.this.getCacheDir();
                                if (!cameraFolder.exists())
                                    cameraFolder.mkdirs();

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                                String timeStamp = dateFormat.format(new Date());
                                String imageFileName = "poisson_" + timeStamp + ".jpg";

                                File photo = new File(root.getAbsolutePath() + PriseForm.this.getFilesDir().getPath(),
                                        imageFileName);
                                getCameraImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                                initialURI = Uri.fromFile(photo);

                                startActivityForResult(getCameraImage, ACTION_REQUEST_CAMERA);

                                break;

                            default:
                                break;
                        }
                    }
                });

        builder.show();

    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private File SaveImage(Bitmap finalBitmap) {
        File root = Environment.getExternalStorageDirectory();
        File myDir = new File(root.getAbsolutePath() + PriseForm.this.getFilesDir().getPath());

        myDir.mkdirs();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        String timeStamp = dateFormat.format(new Date());
        String fname = "poisson_" + timeStamp + ".jpg";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            ExifInterface ei =  new ExifInterface(file.getPath());

            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotateImage(finalBitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotateImage(finalBitmap, 180);
                    break;
                // etc.
            }



            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return file;
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case ACTION_REQUEST_GALLERY:
                    try {
                        Uri SelectedFile = initialURI = data.getData();
                        Uri uri = data.getData();

                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        cursor.close();

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                        Bitmap bitmap = getBitmapFromUri(SelectedFile);
                        File f = SaveImage(bitmap);
                        initialURI = Uri.fromFile(f);
                        // end save
                        ((ImageView) findViewById(R.id.take_photo)).setImageDrawable(new BitmapDrawable(getResources(), bitmap));
                        //ImageView myImg = ((ImageView) findViewById(R.id.take_photo));
                       // myImg.setImageDrawable(new BitmapDrawable(getResources(), getBitmapFromUri(SelectedFile)));//MediaStore.Images.Media.getBitmap(this.getContentResolver(), SelectedFile)));


                    } catch (Exception e) {
                        int i = 0;
                    }
                    break;

                case ACTION_REQUEST_CAMERA:
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(initialURI.getPath(), options);
                    if (bitmap != null) {
                        ((ImageView) findViewById(R.id.take_photo)).setImageDrawable(new BitmapDrawable(getResources(), bitmap));
                    }
                    break;
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prise_form);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            idPeche = b.getInt("peche_key");
            idPrise = b.getInt("prise_key");
            loadPeche();
            setTitle(R.string.new_prise);
        } else {
            finish();
        }

        findViewById(R.id.select_type_poisson).setOnClickListener(this);
        findViewById(R.id.take_photo).setOnClickListener(this);
    }

    private void loadPeche() {

        PecheBDD pecheBdd = new PecheBDD(this);
        pecheBdd.open();
        currentPeche = pecheBdd.getOneId(idPeche);
        pecheBdd.close();
        if (idPrise >= 0) {
            loadPrise();
        }
    }


    private void loadPrise() {

        PriseBDD priseBdd = new PriseBDD(this);
        priseBdd.open();
        Prise prise = priseBdd.getOneId(idPrise);
        priseBdd.close();
        ((Button) findViewById(R.id.select_type_poisson)).setText(prise.getType());
        ((EditText) findViewById(R.id.taille)).setText(String.valueOf(prise.getTaille()));
        ((EditText) findViewById(R.id.poids)).setText(String.valueOf(prise.getPoids()));
        ((EditText) findViewById(R.id.comment)).setText(prise.getCommentaire());

        if (!prise.getPhoto().equals("")) {
            if (prise.getPhoto().contains("content://")) {
                try {
                    ((ImageView) findViewById(R.id.take_photo)).setImageDrawable(new BitmapDrawable(getResources(), MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(prise.getPhoto()))));
                } catch (Exception e) {

                }
            } else {
                ((ImageView) findViewById(R.id.take_photo)).setImageDrawable(new BitmapDrawable(getResources(), prise.getPhoto()));
            }
        }
        initialURI = Uri.parse(prise.getPhoto());

    }

    @Override
    public void onBackPressed() {
        createPrise();
    }

    private void createPrise() {
        Double taille = !((EditText) findViewById(R.id.taille)).getText().toString().equals("") ? Double.valueOf(((EditText) findViewById(R.id.taille)).getText().toString()) : 0;
        Double poids = !((EditText) findViewById(R.id.poids)).getText().toString().equals("") ? Double.valueOf(((EditText) findViewById(R.id.poids)).getText().toString()) : 0;

        String photo = new String();

        photo = initialURI != null ? initialURI.getPath():"";

        Prise newprise = new Prise(
                ((Button) findViewById(R.id.select_type_poisson)).getText().toString(),
                taille,
                poids,
                ((EditText) findViewById(R.id.comment)).getText().toString(),
                photo
        );
        newprise.setIdPeche(idPeche);
        if (newprise.getType() != null && !newprise.getType().equals("")) {
            PriseBDD priseBdd = new PriseBDD(this);
            priseBdd.open();
            if (idPrise >= 0) {
                priseBdd.update(newprise);
            } else {
                priseBdd.insert(newprise);
            }
            priseBdd.close();
            //galleryAddPic();
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.no_title)
                    .setMessage(R.string.go_back_ask_no_type)
                    .setPositiveButton(R.string.yes, null)
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PriseForm.this.finish();
                        }
                    }).show();
        }


    }

    private void galleryAddPic() {
        if (initialURI != null) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = initialURI;
            mediaScanIntent.setData(initialURI);
            this.sendBroadcast(mediaScanIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.prise_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_prise_save) {
            createPrise();
            return true;
        } else if (id == android.R.id.home) {
            createPrise();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.select_type_poisson) {
            DialogFragment newFragment = new SelectTypePoissonFragment();
            newFragment.show(getSupportFragmentManager(), "select");
        } else if (id == R.id.take_photo) {
            askToChoose();
        }
    }

    public void onUserSelectTypePoisson(int id) {
        String str = getResources().getStringArray(R.array.poissons)[id];
        Button txt = (Button) findViewById(R.id.select_type_poisson);
        txt.setText(str);
    }
}
