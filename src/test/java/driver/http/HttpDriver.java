package driver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpDriver {

    public String get(String url) throws MalformedURLException, IOException, ProtocolException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
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
}
