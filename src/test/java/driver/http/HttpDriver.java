package driver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import driver.Config;

public class HttpDriver {

	public String get(String url) throws MalformedURLException, IOException, ProtocolException, KeyManagementException, NoSuchAlgorithmException {
		Proxy proxy = Proxy.NO_PROXY;

		if (Config.isProxyRequired()) {
			trustAllCertificates();
			allowAllHosts();

			proxy = new Proxy(Proxy.Type.HTTP,
					new InetSocketAddress(Config.getProxyHost(), Config.getProxyPort()));
		}

		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection(proxy);
        con.setRequestMethod("GET");

        StringBuilder response = new StringBuilder(1024);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line + "\n");
            }
        }

        return response.toString();
    }

	/**
	 * Create all-trusting certificate verifier.
	 * 
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	private void trustAllCertificates() throws KeyManagementException, NoSuchAlgorithmException {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] {
				new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}

					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}
				}
		};

		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	/**
	 * Create all-trusting host name verifier.
	 * 
	 * @return A self reference
	 */
	private void allowAllHosts() {
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}
}
