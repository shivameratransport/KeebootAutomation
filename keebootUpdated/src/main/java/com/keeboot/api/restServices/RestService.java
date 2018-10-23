package com.keeboot.api.restServices;

import static com.keeboot.utils.TestReporter.logInfo;
import static com.keeboot.utils.TestReporter.logTrace;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keeboot.api.restServices.Headers.HeaderType;
import com.keeboot.api.restServices.exceptions.RestException;
import com.keeboot.utils.exception.DataProviderInputFileNotFound;
import com.keeboot.utils.io.FileLoader;

public class RestService {
    private List<BasicHeader> customHeaders = null;

    public void addCustomHeaders(String header, String value) {
        if (customHeaders == null) {
            customHeaders = new ArrayList<>();
        } else if (isEmpty(header) || isEmpty(value)) {
            throw new RestException("Header name and value cannot be null: Header: [ " + header + " ] Value: [ " + value + " ]");
        }

        customHeaders.add(new BasicHeader(header, value));
    }

    public void removeCustomHeaders() {
        if (customHeaders != null) {
            customHeaders.clear();
        }
    }

    public void updateCustomHeaders(String header, String value) {
        if (customHeaders == null) {
            customHeaders = new ArrayList<>();
        } else if (isEmpty(header) || isEmpty(value)) {
            throw new RestException("Header name and value cannot be null: Header: [ " + header + " ] Value: [ " + value + " ]");
        } else {
            for (int i = 0; i < customHeaders.size(); i++) {
                if (customHeaders.get(i).getName().equalsIgnoreCase(header)) {
                    customHeaders.remove(i);
                    customHeaders.add(new BasicHeader(header, value));
                }
            }
        }
    }

    /**
     * Sends a GET request to a URL
     *
     * @param URL
     *            for the service you are testing
     * @return response in string format
     */
    public RestResponse sendGetRequest(String url) {
        return sendGetRequest(url, null, null);
    }

    /**
     * Sends a GET request
     *
     * @param url
     *            for the service you are testing
     * @return response in string format
     * @throws ClientProtocolException
     * @throws IOException
     */
    public RestResponse sendGetRequest(String url, HeaderType type) {
        return sendGetRequest(url, type, null);
    }

    /**
     * Sends a GET request
     *
     * @param url
     *            for the service you are testing
     * @return response in string format
     * @throws ClientProtocolException
     * @throws IOException
     */
    public RestResponse sendGetRequest(String url, HeaderType type, List<NameValuePair> params) {
        logTrace("Entering RestService#sendGetRequest");
        logTrace("Creating Http GET instance with URL of [ " + url + " ]");
        HttpGet request = new HttpGet(url);

        if (params != null) {
      
            request = new HttpGet(createQueryParamUrl(url, params));
        }

        request.setHeaders(createHeaders(type));
      
        request.setHeader("Authorization","Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImFHMHNKNHNtME1uMVhiWVhGTnZWY2ltU0ZPWWV0Q2JYMG1fN0tOSHQtMGsifQ.eyJqdGkiOiIyNzhmYzM2MS00MWVlLTQxMTYtOTFiYS05ZmJjNDZkODM4ZTQiLCJleHAiOjE1NDI4NjU5MDMsIm5iZiI6MCwiaWF0IjoxNTQwMjczOTAzLCJpc3MiOiJodHRwOi8vaW5mcmEtaW50LnN0YWdpbmcua2VlYm9vdC5jb206ODA4MC9hdXRoL3JlYWxtcy9rZWVib290IiwiYXVkIjoia2VlYm9vdF9tYWluIiwic3ViIjoiZDgxZTViNDMtMzlkNy00YTlkLWFlY2ItMDFkYTk5ZWJkYzVjIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoia2VlYm9vdF9tYWluIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiYWZmYzYwODAtZWYyZi00ODExLWFlNTItYTE0NmZkYTYxNmZkIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6W10sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImZmZmNkMTc0ZjUwNzRiOGU4ZGQ4ZmQyYTI2Y2E4NWVlIiwiS0JVSUQiOiJGRkZDRDE3NEY1MDc0QjhFOEREOEZEMkEyNkNBODVFRSIsIkFQUFRZUEUiOiJLRUVCT09ULU1BSU4tV0VCIiwiS0JUSUQiOiJFOEE0QTkwQTc4QUM0NjQ5QjFBMzk2NjVDRTU0QzcwNCIsIlRFTkFOVFRZUEUiOiJGbGVldCBPd25lciIsIklTQUNUSVZFIjp0cnVlLCJJU1ZFUklGSUVEIjp0cnVlLCJJU09OREVNQU5ETU9ERSI6dHJ1ZSwiSVNJTlRFUk5BTCI6dHJ1ZSwiS0JFSUQiOiI4RTgyRDk4QzdGRDk0N0RCQTI0Qzc2MDA2Q0MzNzJCQyJ9.lqtg_cJdCmDDOIxffDuZuTq7CbF8F7kPbKr6ovX0i9XMQ8TZ0xMpCt9osnT2DijhN4tDsdUj9DYQECxFhTPpP1CSxOc8J9fp8_hVzeettt5Nv-BhBE2d4igUoC_SMAw-XiQGgu0uvDTK8cOFX4qFgYq2fEGiCpnnOGV1XYpzcc73KnJVmMCzz6lBOwsBWF0A2FHFzzd95gk2I53sMRfK0gWnXk43GsZoN9jx0j2ZyZXhshcw33N30ufQ2S36OPEtwOkbCAMhkndC9Gtplrqg0Mwn6616MkT1pfTAJCqm8NjVfvgPaBgImCkWhkeCxNL6aZnwLV6yM9Pl1WE0NQgimw");

        RestResponse response = sendRequest(request);
        logTrace("Exiting RestService#sendGetRequest");
        return response;
    }

    public RestResponse sendPostRequest(String url, HeaderType type, List<NameValuePair> params, String json) {
        logTrace("Entering RestService#sendPostRequest");
        logTrace("Creating Http POST instance with URL of [ " + url + " ]");
        HttpPost httppost = new HttpPost(url);

        if (params != null) {
            httppost = new HttpPost(createQueryParamUrl(url, params));
        }

        if (json != null) {
            logInfo("Adding json " + json);
            httppost.setEntity(createJsonEntity(json));
        }

        httppost.setHeaders(createHeaders(type));
        httppost.setHeader("Authorization","Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImFHMHNKNHNtME1uMVhiWVhGTnZWY2ltU0ZPWWV0Q2JYMG1fN0tOSHQtMGsifQ.eyJqdGkiOiIyNzhmYzM2MS00MWVlLTQxMTYtOTFiYS05ZmJjNDZkODM4ZTQiLCJleHAiOjE1NDI4NjU5MDMsIm5iZiI6MCwiaWF0IjoxNTQwMjczOTAzLCJpc3MiOiJodHRwOi8vaW5mcmEtaW50LnN0YWdpbmcua2VlYm9vdC5jb206ODA4MC9hdXRoL3JlYWxtcy9rZWVib290IiwiYXVkIjoia2VlYm9vdF9tYWluIiwic3ViIjoiZDgxZTViNDMtMzlkNy00YTlkLWFlY2ItMDFkYTk5ZWJkYzVjIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoia2VlYm9vdF9tYWluIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiYWZmYzYwODAtZWYyZi00ODExLWFlNTItYTE0NmZkYTYxNmZkIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6W10sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImZmZmNkMTc0ZjUwNzRiOGU4ZGQ4ZmQyYTI2Y2E4NWVlIiwiS0JVSUQiOiJGRkZDRDE3NEY1MDc0QjhFOEREOEZEMkEyNkNBODVFRSIsIkFQUFRZUEUiOiJLRUVCT09ULU1BSU4tV0VCIiwiS0JUSUQiOiJFOEE0QTkwQTc4QUM0NjQ5QjFBMzk2NjVDRTU0QzcwNCIsIlRFTkFOVFRZUEUiOiJGbGVldCBPd25lciIsIklTQUNUSVZFIjp0cnVlLCJJU1ZFUklGSUVEIjp0cnVlLCJJU09OREVNQU5ETU9ERSI6dHJ1ZSwiSVNJTlRFUk5BTCI6dHJ1ZSwiS0JFSUQiOiI4RTgyRDk4QzdGRDk0N0RCQTI0Qzc2MDA2Q0MzNzJCQyJ9.lqtg_cJdCmDDOIxffDuZuTq7CbF8F7kPbKr6ovX0i9XMQ8TZ0xMpCt9osnT2DijhN4tDsdUj9DYQECxFhTPpP1CSxOc8J9fp8_hVzeettt5Nv-BhBE2d4igUoC_SMAw-XiQGgu0uvDTK8cOFX4qFgYq2fEGiCpnnOGV1XYpzcc73KnJVmMCzz6lBOwsBWF0A2FHFzzd95gk2I53sMRfK0gWnXk43GsZoN9jx0j2ZyZXhshcw33N30ufQ2S36OPEtwOkbCAMhkndC9Gtplrqg0Mwn6616MkT1pfTAJCqm8NjVfvgPaBgImCkWhkeCxNL6aZnwLV6yM9Pl1WE0NQgimw");

        RestResponse response = sendRequest(httppost);
        logTrace("Exiting RestService#sendPostRequest");
        return response;
    }

    /**
     * Sends a post (update) request, pass in the parameters for the json arguments to update
     *
     * @param url
     *            for the service
     * @param params
     *            arguments to update
     * @return response in string format
     * @throws ClientProtocolException
     * @throws IOException
     */
    public RestResponse sendPostRequest(String url, List<NameValuePair> params) {
        return sendPostRequest(url, null, params, null);
    }

    /**
     * Sends a post (update) request, pass in the parameters for the json arguments to update
     *
     * @param url
     *            for the service
     * @param params
     *            arguments to update
     * @return response in string format
     * @throws ClientProtocolException
     * @throws IOException
     */
    public RestResponse sendPostRequest(String url, HeaderType headers, List<NameValuePair> params) {
        return sendPostRequest(url, headers, params, null);
    }

    public RestResponse sendPostRequest(String url, HeaderType type, String body) {
        return sendPostRequest(url, type, null, body);
    }

    public RestResponse sendPutRequest(String url, HeaderType type, List<NameValuePair> params, String json) {
        logTrace("Entering RestService#sendPutRequest");
        logTrace("Creating Http PUT instance with URL of [ " + url + " ]");
        HttpPut httpPut = new HttpPut(url);

        if (params != null) {
            httpPut = new HttpPut(createQueryParamUrl(url, params));
        }

        if (json != null) {
            logInfo("Adding json " + json);
            httpPut.setEntity(createJsonEntity(json));
        }

        httpPut.setHeaders(createHeaders(type));
        httpPut.setHeader("Authorization","Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImFHMHNKNHNtME1uMVhiWVhGTnZWY2ltU0ZPWWV0Q2JYMG1fN0tOSHQtMGsifQ.eyJqdGkiOiIyNzhmYzM2MS00MWVlLTQxMTYtOTFiYS05ZmJjNDZkODM4ZTQiLCJleHAiOjE1NDI4NjU5MDMsIm5iZiI6MCwiaWF0IjoxNTQwMjczOTAzLCJpc3MiOiJodHRwOi8vaW5mcmEtaW50LnN0YWdpbmcua2VlYm9vdC5jb206ODA4MC9hdXRoL3JlYWxtcy9rZWVib290IiwiYXVkIjoia2VlYm9vdF9tYWluIiwic3ViIjoiZDgxZTViNDMtMzlkNy00YTlkLWFlY2ItMDFkYTk5ZWJkYzVjIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoia2VlYm9vdF9tYWluIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiYWZmYzYwODAtZWYyZi00ODExLWFlNTItYTE0NmZkYTYxNmZkIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6W10sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImZmZmNkMTc0ZjUwNzRiOGU4ZGQ4ZmQyYTI2Y2E4NWVlIiwiS0JVSUQiOiJGRkZDRDE3NEY1MDc0QjhFOEREOEZEMkEyNkNBODVFRSIsIkFQUFRZUEUiOiJLRUVCT09ULU1BSU4tV0VCIiwiS0JUSUQiOiJFOEE0QTkwQTc4QUM0NjQ5QjFBMzk2NjVDRTU0QzcwNCIsIlRFTkFOVFRZUEUiOiJGbGVldCBPd25lciIsIklTQUNUSVZFIjp0cnVlLCJJU1ZFUklGSUVEIjp0cnVlLCJJU09OREVNQU5ETU9ERSI6dHJ1ZSwiSVNJTlRFUk5BTCI6dHJ1ZSwiS0JFSUQiOiI4RTgyRDk4QzdGRDk0N0RCQTI0Qzc2MDA2Q0MzNzJCQyJ9.lqtg_cJdCmDDOIxffDuZuTq7CbF8F7kPbKr6ovX0i9XMQ8TZ0xMpCt9osnT2DijhN4tDsdUj9DYQECxFhTPpP1CSxOc8J9fp8_hVzeettt5Nv-BhBE2d4igUoC_SMAw-XiQGgu0uvDTK8cOFX4qFgYq2fEGiCpnnOGV1XYpzcc73KnJVmMCzz6lBOwsBWF0A2FHFzzd95gk2I53sMRfK0gWnXk43GsZoN9jx0j2ZyZXhshcw33N30ufQ2S36OPEtwOkbCAMhkndC9Gtplrqg0Mwn6616MkT1pfTAJCqm8NjVfvgPaBgImCkWhkeCxNL6aZnwLV6yM9Pl1WE0NQgimw");

        RestResponse response = sendRequest(httpPut);
        logTrace("Exiting RestService#sendPutRequest");
        return response;
    }

    public RestResponse sendPutRequest(String url, HeaderType type, String json) {
        return sendPutRequest(url, type, null, json);
    }

    /**
     * Sends a put (create) request, pass in the parameters for the json arguments to create
     *
     * @param url
     *            for the service
     * @param params
     *            arguments to update
     * @return response in string format
     * @throws ClientProtocolException
     * @throws IOException
     */
    public RestResponse sendPutRequest(String url, HeaderType type, List<NameValuePair> params) {
        return sendPutRequest(url, type, params, null);
    }

    public RestResponse sendPutRequest(String url, HeaderType type) {
        return sendPutRequest(url, type, null, null);
    }

    public RestResponse sendPutRequest(String url, List<NameValuePair> params) {
        return sendPutRequest(url, null, params, null);
    }

    public RestResponse sendPutRequest(String url, String json) {
        return sendPutRequest(url, null, json);
    }

    public RestResponse sendPatchRequest(String url, HeaderType type, List<NameValuePair> params, String json) {
        logTrace("Entering RestService#sendPatchRequest");
        logTrace("Creating Http PATCH instance with URL of [ " + url + " ]");
        HttpPatch httpPatch = new HttpPatch(url);
        if (params != null) {
            httpPatch = new HttpPatch(createQueryParamUrl(url, params));
        }

        httpPatch.setHeaders(createHeaders(type));

        if (json != null) {
            logInfo("Adding json [" + json + "]");
            httpPatch.setEntity(createJsonEntity(json));
        }

        RestResponse response = sendRequest(httpPatch);
        logTrace("Exiting RestService#sendPatchRequest");
        return response;
    }

    /**
     * Sends a patch (update) request, pass in the parameters for the json arguments to update
     *
     * @param url
     *            for the service
     * @param params
     *            arguments to update
     * @return response in string format
     * @throws ClientProtocolException
     * @throws IOException
     */
    public RestResponse sendPatchRequest(String url, HeaderType type, List<NameValuePair> params) {
        return sendPatchRequest(url, type, params, null);
    }

    public RestResponse sendPatchRequest(String url, List<NameValuePair> params, String json) {
        return sendPatchRequest(url, null, params, json);
    }

    /**
     * Sends a patch (update) request, pass in the parameters for the json arguments to update
     *
     * @param url
     *            for the service
     * @param params
     *            arguments to update
     * @return response in string format
     * @throws ClientProtocolException
     * @throws IOException
     */
    public RestResponse sendPatchRequest(String url, List<NameValuePair> params) {
        return sendPatchRequest(url, null, params, null);
    }

    public RestResponse sendPatchRequest(String url, HeaderType type, String json) {
        return sendPatchRequest(url, type, null, json);
    }

    public RestResponse sendDeleteRequest(String url, HeaderType type, List<NameValuePair> params) {
        logTrace("Entering RestService#sendDeleteRequest");
        logTrace("Creating Http DELETE instance with URL of [ " + url + " ]");
        HttpDelete httpDelete = new HttpDelete(url);

        if (params != null) {
            httpDelete = new HttpDelete(createQueryParamUrl(url, params));
        }

        httpDelete.setHeaders(createHeaders(type));

        RestResponse response = sendRequest(httpDelete);
        logTrace("Exiting RestService#sendDeleteRequest");
        return response;
    }

    /**
     * Sends a delete request. Depends on the service if a response is returned.
     * If no response is returned, will return null *
     *
     * @param url
     *            for the service
     * @return response in string format or null
     * @throws ClientProtocolException
     * @throws IOException
     */

    public RestResponse sendDeleteRequest(String url, HeaderType type) {
        return sendDeleteRequest(url, type, null);
    }

    public RestResponse sendDeleteRequest(String url) {
        return sendDeleteRequest(url, null, null);
    }

    /**
     * Sends an options request. Options should give what the acceptable methods are for
     * the service (GET, HEAD, PUT, POST, etc). There should be some sort of an ALLOW
     * header that will give you the allowed methods. May or may not be a body to the response,
     * depending on the service.
     *
     * This method will return all the headers and the test should parse through and find the header
     * it needs, that will give the allowed methods, as the naming convention will be different for each service.
     *
     * @param URL
     *            for the service
     * @return returns an array of headers
     * @throws ClientProtocolException
     * @throws IOException
     */
    public Header[] sendOptionsRequest(String url) {
        HttpOptions httpOptions = new HttpOptions(url);
        return sendRequest(httpOptions).getHeaders();
    }

    public static String getJsonFromObject(Object request) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RestException("Failed to convert object to json", e);
        }
    }

    private RestResponse sendRequest(HttpUriRequest request) {
        logTrace("Entering RestService#sendRequest");
        RestResponse response = null;

        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            });

            @SuppressWarnings("deprecation")
            SSLConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(builder.build(),
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            // try (CloseableHttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build()) {

            try (CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslSF).build()) {
                logTrace("Sending request");
                StopWatch execution = StopWatch.createStarted();

                HttpResponse httpResponse = httpClient.execute(request);
                execution.stop();
                String executionTime = execution.toString();
                response = new RestResponse(request, httpResponse, executionTime);
                // httpClient.getConnectionManager().shutdown();
            } catch (IOException e) {
                System.out.println(e);
                throw new RestException("Failed to send request to " + request.getURI().toString(), e);
            }
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            System.out.println(e);
            throw new RestException("Failed to send request to " + request.getURI().toString(), e);
        }
        logTrace("Returning RestResponse to calling method");
        logTrace("Exiting RestService#sendRequest");
        return response;
    }

    public static <T> T readJsonFromFile(String filePath, Class<T> clazz) {
        logTrace("Entering RestService#readJsonFromFile");
        logTrace("Loading resource [ " + clazz.getClass().getResourceAsStream(filePath) + " ]");
        String json = null;

        try {
            json = FileLoader.loadFileFromProjectAsString(filePath);
        } catch (FileNotFoundException fnfe) {
            throw new DataProviderInputFileNotFound("Failed to locate json file in path [ " + filePath + " ]", fnfe);
        } catch (IOException ioe) {
            throw new RestException("Failed to read json file", ioe);
        }
        return mapJSONToObject(json, clazz);
    }

    /**
     * Can pass in any json as a string and map to object
     *
     * @param clazz
     * @return
     * @throws IOException
     */
    private static <T> T mapJSONToObject(String stringResponse, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T map = null;
        try {
            map = mapper.readValue(stringResponse, clazz);
        } catch (JsonParseException e) {
            throw new RestException("Failed to parse JSON", e);
        } catch (JsonMappingException e) {
            throw new RestException("Failed to Map JSON", e);
        } catch (IOException e) {
            throw new RestException("Failed to output JSON", e);
        }
        return map;
    }

    private Header[] createHeaders(HeaderType type) {
        Header[] headers = null;

        if (type != null) {
            headers = Headers.createHeader(type);
        }

        if (customHeaders != null && !customHeaders.isEmpty()) {

            int start = 0;
            if (headers == null) {
                headers = new Header[customHeaders.size()];
            } else {
                start = headers.length;
                headers = Arrays.copyOf(headers, headers.length + customHeaders.size());
            }

            int x = 0;
            for (BasicHeader header : customHeaders) {
                headers[start + x] = header;
                x++;
            }
        }

        return headers;
    }

    private String createQueryParamUrl(String url, List<NameValuePair> params) {
        String allParams = "";
        for (NameValuePair param : params) {
            allParams += "[" + param.getName() + ": " + param.getValue() + "] ";
        }
        logInfo("Adding Parameters " + allParams);
        url = url + "?" + URLEncodedUtils.format(params, "utf-8");
        logInfo("URL with params: " + url);
        return url;
    }

    private ByteArrayEntity createJsonEntity(String json) {
        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(json.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // This literally cannot be reached, but has to be checked anyway
            throw new RestException(e.getMessage(), e);
        }

        return entity;
    }
}