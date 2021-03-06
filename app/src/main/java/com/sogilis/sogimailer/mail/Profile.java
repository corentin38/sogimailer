package com.sogilis.sogimailer.mail;

import android.os.Parcelable;

public interface Profile extends Parcelable {

	long id();
	String name();
	String sender();
	String senderPassword();
	String transportProtocol();
	String host();
	String smtpQuitwait();
	String smtpAuth();
	String smtpPort();
	String smtpSocketFactoryPort();
	String smtpSocketFactoryClass();
	String smtpSocketFactoryFallback();

	void setSender(String sender);
	void setHost(String host);
	void setPassword(String password);

}
