package com.sogilis.sogimailer.mail;

import android.os.Parcel;
import android.os.Parcelable;

public class ExtendedProfile implements Profile {

	private long   mId;
	private String mName;
	private String mSender;
	private String mSenderPassword;
	private String mTransportProtocol;
	private String mHost;
	private String mSmtpQuitwait;
	private String mSmtpAuth;
	private String mSmtpPort;
	private String mSmtpSocketFactoryPort;
	private String mSmtpSocketFactoryClass;
	private String mSmtpSocketFactoryFallback;
	private boolean mIsDefault;

	public ExtendedProfile(String mHost, long mId, String mSender, String mSenderPassword,
	                       String mSmtpAuth, String mSmtpPort, String mSmtpQuitwait,
	                       String mSmtpSocketFactoryClass, String mSmtpSocketFactoryFallback,
	                       String mSmtpSocketFactoryPort, String mTransportProtocol, String mName,
	                       boolean mIsDefault) {
		this.mHost = mHost;
		this.mId = mId;
		this.mSender = mSender;
		this.mSenderPassword = mSenderPassword;
		this.mSmtpAuth = mSmtpAuth;
		this.mSmtpPort = mSmtpPort;
		this.mSmtpQuitwait = mSmtpQuitwait;
		this.mSmtpSocketFactoryClass = mSmtpSocketFactoryClass;
		this.mSmtpSocketFactoryFallback = mSmtpSocketFactoryFallback;
		this.mSmtpSocketFactoryPort = mSmtpSocketFactoryPort;
		this.mTransportProtocol = mTransportProtocol;
		this.mName = mName;
		this.mIsDefault = mIsDefault;
	}

	public ExtendedProfile(Parcel in) {
		this.mId = in.readLong();
		this.mHost = in.readString();
		this.mSender = in.readString();
		this.mSenderPassword = in.readString();
		this.mSmtpAuth = in.readString();
		this.mSmtpPort = in.readString();
		this.mSmtpQuitwait = in.readString();
		this.mSmtpSocketFactoryClass = in.readString();
		this.mSmtpSocketFactoryFallback = in.readString();
		this.mSmtpSocketFactoryPort = in.readString();
		this.mTransportProtocol = in.readString();
		this.mName = in.readString();
		this.mIsDefault = in.readByte() != 0;
	}

	@Override
	public String name() {
		return mName;
	}

	@Override
	public String host() {
		return mHost;
	}

	@Override
	public long id() {
		return mId;
	}

	@Override
	public String sender() {
		return mSender;
	}

	@Override
	public String senderPassword() {
		return mSenderPassword;
	}

	@Override
	public String transportProtocol() {
		return mTransportProtocol;
	}

	@Override
	public String smtpQuitwait() {
		return mSmtpQuitwait;
	}

	@Override
	public String smtpAuth() {
		return mSmtpAuth;
	}

	@Override
	public String smtpPort() {
		return mSmtpPort;
	}

	@Override
	public String smtpSocketFactoryPort() {
		return mSmtpSocketFactoryPort;
	}

	@Override
	public String smtpSocketFactoryClass() {
		return mSmtpSocketFactoryClass;
	}

	@Override
	public String smtpSocketFactoryFallback() {
		return mSmtpSocketFactoryFallback;
	}

	@Override
	public boolean isDefault() {
		return mIsDefault;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(mId);
		dest.writeString(mHost);
		dest.writeString(mSender);
		dest.writeString(mSenderPassword);
		dest.writeString(mSmtpAuth);
		dest.writeString(mSmtpPort);
		dest.writeString(mSmtpQuitwait);
		dest.writeString(mSmtpSocketFactoryClass);
		dest.writeString(mSmtpSocketFactoryFallback);
		dest.writeString(mSmtpSocketFactoryPort);
		dest.writeString(mTransportProtocol);
		dest.writeString(mName);
		dest.writeByte((byte) (mIsDefault ? 1 : 0));
	}

	public static final Parcelable.Creator<ExtendedProfile> CREATOR = new Parcelable.Creator<ExtendedProfile>() {
		@Override
		public ExtendedProfile createFromParcel(Parcel source) {
			return new ExtendedProfile(source);
		}

		@Override
		public ExtendedProfile[] newArray(int size) {
			return new ExtendedProfile[size];
		}
	};

}
