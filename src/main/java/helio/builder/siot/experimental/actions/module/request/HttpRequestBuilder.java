package helio.builder.siot.experimental.actions.module.request;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.apache.http.HttpStatus;
import org.apache.http.protocol.ResponseContent;


public class HttpRequestBuilder {
    
    private static HttpRequestBuilder singleton = null;
    private HttpClient client;

    private HttpRequestBuilder() {
        this.client = HttpClient.newHttpClient();
    }

    public static HttpRequestBuilder instance() {
        if (singleton == null) {
            singleton = new HttpRequestBuilder();
        }
        return singleton;
    }

    public String get(String url, String... headers) {
        return executeRequest(HttpRequest
                .newBuilder(URI.create(url))
                .headers(headers)
                .GET().build());
    }

    public String post(String url, String data, String... headers) {
        return executeRequest(HttpRequest
                .newBuilder(URI.create(url))
                .headers(headers)
                .POST(BodyPublishers.ofString(data)).build());
    }

    public String put(String url, String data, String... headers) {
        return executeRequest(HttpRequest
                .newBuilder(URI.create(url))
                .headers(headers)
                .PUT(BodyPublishers.ofString(data)).build());
    }

    public String delete(String url, String... headers) {
        return executeRequest(HttpRequest
                .newBuilder(URI.create(url))
                .headers(headers)
                .DELETE().build());
    }

    private String executeRequest(HttpRequest request) {
    	String result = null;

        try {
        	HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        	if (response != null && response.statusCode() >= 200 && response.statusCode() < 300) {
        		result = response.body();
        	}
		}
        catch (InterruptedException | IOException e) {
        	e.printStackTrace();
		}
        
        return result;
    }

}
