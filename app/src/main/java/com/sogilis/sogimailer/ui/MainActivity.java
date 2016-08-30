package com.sogilis.sogimailer.ui;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sogilis.sogimailer.R;
import com.sogilis.sogimailer.SogiMailerApplication;
import com.sogilis.sogimailer.dude.ProfileDude;
import com.sogilis.sogimailer.mail.Default;
import com.sogilis.sogimailer.mail.Mailer;
import com.sogilis.sogimailer.mail.NoSuchProfileException;
import com.sogilis.sogimailer.mail.Profile;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {

	private static final String TAG = "SOGIMAILER_ACTIVITY";

	private static final String SOGIMAILER_ACTION = "com.sogilis.sogimailer.ACTION_SEND";

	@Inject
	public BroadcastReceiver testReceiver;

	@Inject
	public ProfileDude profileDude;

	@Inject
	public Mailer mailer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpToolbar(R.string.app_name, true);
		Log.d(TAG, "onCreate");

		((SogiMailerApplication) getApplication()).getObjectGraph().inject(this);

        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

	}

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Profiles");
        adapter.addFragment(new DocumentationFragment(), "Documentation");
        viewPager.setAdapter(adapter);
    }

	@Override
	protected void onResume() {
		IntentFilter filter = new IntentFilter(SOGIMAILER_ACTION);
		registerReceiver(testReceiver, filter);
		super.onResume();
	}

	@Override
	protected void onPause() {
		unregisterReceiver(testReceiver);
		super.onPause();
	}

	public void setUpToolbar(int titleId, boolean displayHomeAsUp) {
		Toolbar toolbar = (Toolbar) findViewById(R.id.global_toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar == null) {
			return;
		}

		actionBar.setDisplayHomeAsUpEnabled(displayHomeAsUp);
		actionBar.setDisplayShowTitleEnabled(false);
		TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
		mTitle.setText(getString(titleId));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.main_testmail:
				Log.d(TAG, "starting test");
				TestMailDialog dlg = TestMailDialog.newInstance();
				dlg.show(getSupportFragmentManager(), TestMailDialog.TESTMAIL_DIALOG_KEY);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

/*

	private void addFragment(Fragment frag, String tag) {
		Log.d(TAG, "addFragment");




		FragmentManager fm = getSupportFragmentManager();

		fm.dump(TAG + " BEFORE !!! ", null, new PrintWriter(System.out, true), null);


		FragmentTransaction tx = fm.beginTransaction();
		tx.replace(R.id.main_fragment_holder, frag, tag);
		tx.addToBackStack(tag);
		tx.commit();
		fm.dump(TAG + " AFTER !!! ", null, new PrintWriter(System.out, true), null);
	}
*/
/*
	private void handleBackPressed() {
		Log.d(TAG, "handleBackPressed");

		FragmentManager fm = getSupportFragmentManager();
		String tag = fm.getBackStackEntryAt(fm.getBackStackEntryCount() -1).getName();
		Log.d(TAG, "last tag :" + tag);
		Fragment current = fm.findFragmentByTag(tag);

		Log.d(TAG, "current frag :" + current.getTag());

		if (current instanceof HomeFragment) {
			Log.d(TAG, "We are at home already, do nothing");
			return;
		}

		Log.d(TAG, "let's pop !");
		fm.popBackStack();
	}*/
/*

	public void edit(View view) {
		Log.d(TAG, "edit");
		goEdit();
	}

	public void saveEdit(View view) {
		Log.d(TAG, "saveEdit");

		FragmentManager fm = getSupportFragmentManager();
		EditFragment frag = (EditFragment) fm.findFragmentByTag(EditFragment.FRAGMENT_KEY);
		profileDude.saveBasic(frag.getSenderEntry(), frag.getHostEntry(), frag.getPasswordEntry());

		goHome();
	}

	public void cancelEdit(View view) {
		Log.d(TAG, "saveEdit");
		goHome();
	}

	public void discardDisclaimer(View view) {
		Log.d(TAG, "discardDisclaimer");
		removeDisclaimer();
	}

	private void goHome() {
		Log.d(TAG, "goHome");

		Profile profile = new Default();
		try {
			profile = profileDude.getBasic();
		} catch (NoSuchProfileException e) {
			Log.d(TAG, "No profile defined while getting back home");
		}

		Fragment home = HomeFragment.newInstance(profile);

		removeDisclaimer();
		addFragment(home, HomeFragment.FRAGMENT_KEY);
	}

	private void goEdit() {
		Log.d(TAG, "goEdit");

		Profile profile = null;
		try {
			profile = profileDude.getBasic();
		} catch (NoSuchProfileException e) {
			Log.d(TAG, "No profile defined while getting back home");
			showDisclaimer();
		}
		Fragment edit = EditFragment.newInstance(0, profile);
		addFragment(edit, EditFragment.FRAGMENT_KEY);
	}

	private void showDisclaimer() {
		Log.d(TAG, "showDisclaimer");
		FragmentManager fm = getSupportFragmentManager();
		Fragment disc = fm.findFragmentByTag(DisclaimerFragment.FRAGMENT_KEY);
		if (disc == null) {
			Log.d(TAG, "disclaimer is null");
			disc = new DisclaimerFragment();
		}

		FragmentTransaction tx = fm.beginTransaction();
		tx.replace(R.id.disclaimer_fragment_holder, disc, DisclaimerFragment.FRAGMENT_KEY);
		tx.addToBackStack(DisclaimerFragment.FRAGMENT_KEY);
		tx.commit();
	}

	private void removeDisclaimer() {
		Log.d(TAG, "removeDisclaimer");
		FragmentManager fm = getSupportFragmentManager();
		Fragment disc = fm.findFragmentByTag(DisclaimerFragment.FRAGMENT_KEY);
		if (disc == null) {
			Log.d(TAG, "disclaimer is null !!");
			return;
		}

		Log.d(TAG, "disclaimer is not null");
		FragmentTransaction tx = fm.beginTransaction();
		tx.remove(disc);
		tx.addToBackStack(DisclaimerFragment.FRAGMENT_KEY);
		tx.commit();
	}

	private void goDocumentation() {
		Log.d(TAG, "goDocumentation");
		Fragment docu = new DocumentationFragment();
		addFragment(docu, DocumentationFragment.FRAGMENT_KEY);
	}
*/



    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
