/*
 * Copyright (c) 2014. Les scripts sont la propriété exclusive de pierrick pobelle, toutes modifications ou revente est interdite.
 */

package fr.pkshetlie.carnetdepeche;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Locale;

import fr.pkshetlie.carnetdepeche.bdd.PecheBDD;
import fr.pkshetlie.carnetdepeche.bdd.PriseBDD;
import fr.pkshetlie.carnetdepeche.objects.Peche;
import fr.pkshetlie.carnetdepeche.objects.Prise;
import fr.pkshetlie.carnetdepeche.utils.ImageLoader;
import fr.pkshetlie.carnetdepeche.utils.Utils;


public class PriseView extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    Peche currentPeche = null;
    Prise currentPrise = null;
    private int idPeche = -1;
    private int idPrise = -1;
    private Button share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prise_view);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            idPeche = b.getInt("peche_key");
            idPrise = b.getInt("prise_key");
            loadPeche();
        } else {
            finish();
        }

    }
    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putInt("prise_key", idPrise);
        b.putInt("peche_key", idPeche);


        intent.putExtras(b);
        intent.putExtras(b);

        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putInt("prise_key", idPrise);
        b.putInt("peche_key", idPeche);


        intent.putExtras(b);
        intent.putExtras(b);

        setResult(RESULT_OK, intent);

        super.finish();
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
        currentPrise = priseBdd.getOneId(idPrise);
        priseBdd.close();
        ((TextView) findViewById(R.id.type_poisson)).setText(currentPrise.getType());
        if (!currentPrise.getPhoto().equals("")) {
            ImageLoader img = new ImageLoader(this);
            img.DisplayImage(currentPrise.getPhoto(),(ImageView) findViewById(R.id.photoPoisson));
            //((ImageView) findViewById(R.id.photoPoisson)).setImageDrawable(new BitmapDrawable(getResources(), currentPrise.getPhoto()));
        }
        setTitle(currentPrise.getType());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.prise_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_share) {

            final Intent MessIntent = new Intent(Intent.ACTION_SEND);
            //MessIntent.setType("text/plain");
            //MessIntent.putExtra(Intent.EXTRA_STREAM,"Text a partager");
            MessIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(currentPrise.getPhoto())));
            MessIntent.putExtra(Intent.EXTRA_TEXT, "Sortie du " + currentPeche.getDateDebut() + "\nType de poisson : " + currentPrise.getType());
            MessIntent.putExtra(Intent.EXTRA_SUBJECT, currentPrise.getType());

            MessIntent.setType("image/*");
            //PriseView.this.startActivity(Intent.createChooser(MessIntent, "Partager avec..."));
            startActivity(Intent.createChooser(MessIntent, "Partager avec..."));


            //shareIntent.setAction(Intent.ACTION_SEND);
            //startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

}
