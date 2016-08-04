package com.sogilis.sogimailer.svc;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.sogilis.sogimailer.SogiMailerApplication;
import com.sogilis.sogimailer.mail.Default;
import com.sogilis.sogimailer.mail.Mailer;

import javax.inject.Inject;

public class MailerService extends IntentService implements Mailer.Listener {
	private static final String TAG = "SOGIMAILER_SERVICE";

	static final String ACTION = "com.sogilis.sogimailer.ACTION_SEND";

	private static final String OPT_SUBJECT = "MAILER_OPT_SUBJECT";
	private static final String OPT_PASSWORD = "MAILER_OPT_PASSWORD";
	private static final String OPT_BODY = "MAILER_OPT_BODY";
	private static final String OPT_RECIPIENTS = "MAILER_OPT_RECIPIENTS";

	private static final String RESULTMSG_KEY = "MAILER_RESULTMSG";
	private static final String RETCODE_KEY = "MAILER_RETCODE";

	@Inject
	public Mailer mailer;

    public MailerService() {
        super("test-service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
	    Log.d(TAG, "onCreate");

	    ((SogiMailerApplication) getApplication()).getObjectGraph().inject(this);
    }

	/**
	 * When binding to the service, we return an interface to our messenger
	 * for sending messages to the service.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "Handle new intent");

		String subject = intent.getStringExtra(OPT_SUBJECT);
		String body = intent.getStringExtra(OPT_BODY);
		String recipients = intent.getStringExtra(OPT_RECIPIENTS);
		String password = intent.getStringExtra(OPT_PASSWORD);
		Log.d(TAG, "sub, body, reci, pawd : " + subject + " " + body + " " + recipients + " " + password);

		if (!checkKeys(subject, body, recipients, password)) {
			onMailFailure("Bad argument");
			return;
		}

		mailer.updateProfile(new Default(password));
		Log.d(TAG, "Sending mail to mailer !");
		mailer.sendSimpleMail(this, recipients, subject, body);
	}

	@Override
	public void onMailFailure(String err) {
		broadcastResult(-12, err);
	}

	@Override
	public void onMailSuccess() {
		broadcastResult(Activity.RESULT_OK, "Message sent successfully");
	}

	private void broadcastResult(int returnCode, String message) {
		Intent itt = new Intent(ACTION);
		itt.putExtra(RESULTMSG_KEY, message);
		itt.putExtra(RETCODE_KEY, returnCode);

		sendBroadcast(itt);
	}

	public static boolean checkKeys(String subject, String body, String recipients, String password) {
		if (subject == null) return false;
		if (body == null) return false;
		if (recipients == null) return false;
		if (password == null) return false;

		if (subject.isEmpty()) return false;
		if (recipients.isEmpty()) return false;
		if (password.isEmpty()) return false;

		return true;
	}
}