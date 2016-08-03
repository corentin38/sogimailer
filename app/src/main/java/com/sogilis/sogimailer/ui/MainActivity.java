package com.sogilis.sogimailer.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sogilis.sogimailer.R;
import com.sogilis.sogimailer.SogiMailerApplication;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {

	private static final String TAG = "SOGIMAILER_ACTIVITY";

	private static final String SOGIMAILER_ACTION = "com.sogilis.sogimailer.ACTION_SEND";
/*

	private static final String OPT_RECIPIENTS = "MAILER_OPT_RECIPIENTS";
	private static final String OPT_SUBJECT = "MAILER_OPT_SUBJECT";
	private static final String OPT_BODY = "MAILER_OPT_BODY";
	private static final String OPT_PASSWORD = "MAILER_OPT_PASSWORD";
*/

	@Inject
    public BroadcastReceiver testReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpToolbar(R.string.app_name, true);
		Log.d(TAG, "onCreate");

		((SogiMailerApplication) getApplication()).getObjectGraph().inject(this);

		initFragments();
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

	private void initFragments() {
		Fragment homeFragment = HomeFragment.newInstance();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();

		transaction.replace(R.id.main_fragment_holder, homeFragment, HomeFragment.FRAGMENT_KEY);
		transaction.addToBackStack(null);

		transaction.commit();
	}

/*	public void getProps() {

		SharedPreferences settings = getSharedPreferences(
				"sogimailerPrefs",
				Context.MODE_PRIVATE);

		String recip = settings.getString("recipients", "");
		String subject = settings.getString("subject", "");
		String body = settings.getString("body", "");
		String password = settings.getString("password", "");

		EditText recipientsET  = (EditText) findViewById(R.id.recipients);
		recipientsET.setText(recip);

		EditText subjectET  = (EditText) findViewById(R.id.subject);
		subjectET.setText(subject);

		EditText bodyET  = (EditText) findViewById(R.id.body);
		bodyET.setText(body);

		EditText passwordET  = (EditText) findViewById(R.id.password);
		passwordET.setText(password);
	}*/

/*	public void save() {
		SharedPreferences settings = getSharedPreferences(
				"sogimailerPrefs",
				Context.MODE_PRIVATE);

		SharedPreferences.Editor ed = settings.edit();

		ed.putString("recipients", recipients);
		ed.putString("subject", subject);
		ed.putString("body", body);
		ed.putString("password", password);

		ed.commit();
	}*/

/*	public void send() {
		Log.d(TAG, "Launching intent for service SogiMailer");
		EditText recipientsET  = (EditText) findViewById(R.id.recipients);
		EditText subjectET  = (EditText) findViewById(R.id.subject);
		EditText bodyET  = (EditText) findViewById(R.id.body);
		EditText passwordET  = (EditText) findViewById(R.id.password);

		String recipients = recipientsET.getText().toString();
		String subject = subjectET.getText().toString();
		String body = bodyET.getText().toString();
		String password = passwordET.getText().toString();

		Intent itt = new Intent(SOGIMAILER_ACTION);
		itt.setPackage("com.sogilis.sogimailer");

		itt.putExtra(OPT_RECIPIENTS, recipients);
		itt.putExtra(OPT_SUBJECT, subject);
		itt.putExtra(OPT_BODY, body);
		itt.putExtra(OPT_PASSWORD, password);

		startService(itt);
	}*/

}
