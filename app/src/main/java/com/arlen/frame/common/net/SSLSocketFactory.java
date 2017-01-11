package com.arlen.frame.common.net;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;


public class SSLSocketFactory {

	private static final String KEY_STORE_TYPE_P12 = "PKCS12";
	private static final String keyStoreFileName = "client.p12";
	private static final String keyStorePassword = "10Y@QQcom";
	private static final String trustStoreFileName = "ca.cer";
	private static final String trustStorePassword = "10Y@QQcom";
	private static final String alias = null;//"client";
	

	public static javax.net.ssl.SSLSocketFactory getSSLSocketFactory (Context activity)
			throws NoSuchAlgorithmException, KeyManagementException {

		SSLContext context = SSLContext.getInstance("TLS");
		//TODO investigate: could also be "SSLContext context = SSLContext.getInstance("TLS");" Why?
		try{
			//create key and trust managers
			KeyManager[] keyManagers = createKeyManagers(activity,keyStoreFileName, keyStorePassword, alias);
			TrustManager[] trustManagers = createTrustManagers(activity,trustStoreFileName, trustStorePassword);
			context.init(keyManagers, trustManagers, null);
			
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		javax.net.ssl.SSLSocketFactory socketFactory = context.getSocketFactory();
		return socketFactory;
	}
	private static KeyManager[] createKeyManagers(Context activity, String keyStoreFileName, String keyStorePassword, String alias)
			throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
		InputStream inputStream = activity.getAssets().open(keyStoreFileName);
		
		KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
		keyStore.load(inputStream, keyStorePassword.toCharArray());
		
		printKeystoreInfo(keyStore);//for debug

		KeyManager[] managers;
		if (alias != null) {
			managers =
					new KeyManager[] {
					new SSLSocketFactory().new AliasKeyManager(keyStore, alias, keyStorePassword)};
		} else {
			KeyManagerFactory keyManagerFactory =
					KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keyManagerFactory.init(keyStore, keyStorePassword == null ? null : keyStorePassword.toCharArray());
			managers = keyManagerFactory.getKeyManagers();
		}
		return managers;
	}

	private static TrustManager[] createTrustManagers(Context activity, String trustStoreFileName, String trustStorePassword)
			throws IOException, GeneralSecurityException {

		InputStream inputStream = activity.getAssets().open(trustStoreFileName);

	      CertificateFactory cerFactory = CertificateFactory
                  .getInstance("X.509");
          Certificate cer = cerFactory.generateCertificate(inputStream);
          KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
          keyStore.load(null, null);
          keyStore.setCertificateEntry("ca", cer);

	      String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
	      TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);

	      // Wrap it up in an SSL context.
	
	      tmf.init(keyStore);
		
		return tmf.getTrustManagers();
	}
	private static KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
		    try {
		      KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		      InputStream in = null; // By convention, 'null' creates an empty key store.
		      keyStore.load(in, password);
		      return keyStore;
		    } catch (IOException e) {
		      throw new AssertionError(e);
		    }
	}

	private static void printKeystoreInfo(KeyStore keystore) throws KeyStoreException {

		Enumeration en = keystore.aliases();
		while (en.hasMoreElements()) {
			System.out.println("Alias: " + en.nextElement());
		}
	}

	private class AliasKeyManager implements X509KeyManager {

		private KeyStore _ks;
		private String _alias;
		private String _password;

		public AliasKeyManager(KeyStore ks, String alias, String password) {
			_ks = ks;
			_alias = alias;
			_password = password;
		}

		public String chooseClientAlias(String[] str, Principal[] principal, Socket socket) {
			return _alias;
		}

		public String chooseServerAlias(String str, Principal[] principal, Socket socket) {
			return _alias;
		}

		public X509Certificate[] getCertificateChain(String alias) {
			try {
				Certificate[] certificates = this._ks.getCertificateChain(alias);
				if(certificates == null){throw new FileNotFoundException("no certificate found for alias:" + alias);}
				X509Certificate[] x509Certificates = new X509Certificate[certificates.length];
				System.arraycopy(certificates, 0, x509Certificates, 0, certificates.length);
				return x509Certificates;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		public String[] getClientAliases(String str, Principal[] principal) {
			return new String[] { _alias };
		}

		public PrivateKey getPrivateKey(String alias) {
			try {
				return (PrivateKey) _ks.getKey(alias, _password == null ? null : _password.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		public String[] getServerAliases(String str, Principal[] principal) {
			return new String[] { _alias };
		}
	}
	

}
