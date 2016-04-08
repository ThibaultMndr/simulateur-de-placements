package org.gipilab.simulateurdeplacements;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NouveauPlacementFragment.OnFragmentInteractionListener, ListePlacementsFragment.OnFragmentInteractionListener {
    static final int REQUEST_CODE_PLACEMENT_SAVED = 1;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPlacementValidated(Placement placement) {
        Intent intent = new Intent(this, AffichePlacementActivity.class);
        intent.putExtra("placement", placement);
        startActivityForResult(intent, REQUEST_CODE_PLACEMENT_SAVED);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //TODO : actions sur le fragment d'affichage de la liste des placements

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PLACEMENT_SAVED) {
            if (resultCode == RESULT_OK) {
                //TODO : mettre à jour le fragment de liste

                Log.d("PLACEMENT", "Liste placements maj");
                ListePlacementsFragment frag = (ListePlacementsFragment) mSectionsPagerAdapter.getRegisteredFragment(SectionsPagerAdapter.FRAGMENT_LISTE_PLACEMENTS_ID);
                frag.updateListView(frag.getView());


            }
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends SmartFragmentStatePagerAdapter {

        public static final int FRAGMENT_NOUVEAU_PLACEMENT_ID = 0;
        public static final int FRAGMENT_LISTE_PLACEMENTS_ID = 1;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case FRAGMENT_NOUVEAU_PLACEMENT_ID:
                    return NouveauPlacementFragment.newInstance();
                case FRAGMENT_LISTE_PLACEMENTS_ID:
                    return ListePlacementsFragment.newInstance("titi", "toto");
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case FRAGMENT_NOUVEAU_PLACEMENT_ID:
                    return getString(R.string.ongletNouveauPlacement);
                case FRAGMENT_LISTE_PLACEMENTS_ID:
                    return getString(R.string.ongletPlacementsEnregistres);
            }
            return null;
        }
    }
}
