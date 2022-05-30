package helio.builder.siot.experimental.actions.module.request;

import helio.builder.siot.experimental.actions.errors.HttpRequestPerformException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class HttpRequestBuilder {
    
    private static HttpRequestBuilder singleton = null;
    private HttpClient client;
    private Duration defaultTimeout;

    private HttpRequestBuilder() {
        this.client = HttpClient.newHttpClient();
        this.defaultTimeout = Duration.of(30, ChronoUnit.SECONDS);
    }

    public static HttpRequestBuilder instance() {
        if (singleton == null) {
            singleton = new HttpRequestBuilder();
        }
        return singleton;
    }

    public Duration getDefaultTimeout() {
        return defaultTimeout;
    }

    public void setDefaultTimeout(Duration defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    public String get(String url, Map<String, String> headers) throws HttpRequestPerformException {
        HttpRequest.Builder requestBuilder = createRequestBuilder(url, headers);
        return executeRequest(requestBuilder.GET().timeout(defaultTimeout).build());
    }

    public String post(String url, String data, Map<String, String> headers) throws HttpRequestPerformException {
        HttpRequest.Builder requestBuilder = createRequestBuilder(url, headers);
        return executeRequest(requestBuilder.POST(BodyPublishers.ofString(data != null ? data : "")).timeout(defaultTimeout).build());
    }

    public String put(String url, String data, Map<String, String> headers) throws HttpRequestPerformException {
        HttpRequest.Builder requestBuilder = createRequestBuilder(url, headers);
        return executeRequest(requestBuilder.PUT(BodyPublishers.ofString(data != null ? data : "")).timeout(defaultTimeout).build());
    }

    public String delete(String url, Map<String, String> headers) throws HttpRequestPerformException {
        HttpRequest.Builder requestBuilder = createRequestBuilder(url, headers);
        return executeRequest(requestBuilder.DELETE().timeout(defaultTimeout).build());
    }

    private HttpRequest.Builder createRequestBuilder(String url, Map<String, String> headers) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(url));

        if (headers != null && !headers.isEmpty()) {
            headers.forEach((k, v) -> requestBuilder.header(k, v));
        }

        return requestBuilder;
    }

    private String executeRequest(HttpRequest request) throws HttpRequestPerformException {
    	String result = null;

        try {
        	HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        	if (response != null) {
                if (response.statusCode() >= 200 && response.statusCode() < 300) {
                    result = response.body();
                }
                // Custom error codes
                else  if (response.statusCode() >= 400 && response.statusCode() < 500) {
                    throw new HttpRequestPerformException("Bad request (" + response.statusCode() + ")");
                }
                else  if (response.statusCode() >= 500 && response.statusCode() < 600) {
                    throw new HttpRequestPerformException("Internal server error (" + response.statusCode() + ")");
                }
            }
		}
        catch (Exception e) {
        	throw new HttpRequestPerformException(e.getMessage());
		}
        
        return result;
    }

}
