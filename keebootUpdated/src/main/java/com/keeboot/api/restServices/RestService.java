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
      
        request.setHeader("Authorization","Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImFHMHNKNHNtME1uMVhiWVhGTnZWY2ltU0ZPWWV0Q2JYMG1fN0tOSHQtMGsifQ.eyJqdGkiOiI0YmYwNjdmNi03YmIyLTQyMjctYTZlNC1hYjcwOGU2ZTVjOWUiLCJleHAiOjE1Mzk4NDE1NjQsIm5iZiI6MCwiaWF0IjoxNTM5MjM2NzY0LCJpc3MiOiJodHRwOi8vaW5mcmEtaW50LmtlZWJvb3QuY29tOjgwODAvYXV0aC9yZWFsbXMva2VlYm9vdCIsImF1ZCI6ImtlZWJvb3RfbWFpbiIsInN1YiI6IjkyNjIwODU4LWIzMTgtNGZhZi04ODliLWM1MzJmOTYxYzZlOSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImtlZWJvb3RfbWFpbiIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjRkZmE0NjhjLTUwZDMtNDNiNS1hYTVjLWFlZGI5ZDllNDMyMiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOltdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiIyOWZmNDZjNDE4OTQ0ZTgwYTg5YzM4MTM4OTg0Y2YwZiIsIktCVUlEIjoiMjlGRjQ2QzQxODk0NEU4MEE4OUMzODEzODk4NENGMEYiLCJBUFBUWVBFIjoiS0VFQk9PVC1NQUlOLVdFQiIsIktCRUlEIjoiMDc4MjdCQkJCNTA1NEJGOUJGNDVGNEY0RTBGNkQ4NDEiLCJLQlRJRCI6IkM0MDlCRDVCMkYyQjQzMkVBMjREN0NFMkRFRDVGMTY1In0.kg8GhQTD0OgN_da6S2ZAMVp8dTM1XEolhsI5AXWRQqoJemvPAnZVh1W58A1iMbseMNlt3HiPDnW5dIiGEQ3SjuyuG6k8NsuLRimz8eL8xmNw-KFLXi8yffzduSYpM4XOS1MHRxP-_AYvqr7sO42RA7EJ1T9kAKrLvjyk9am65QvbZe8JcqI7n1OzxcAqum7o0sbKI68bXK-8Z5iw02NCdsaiiijO37NAe1_2KU-PkxiefbmlviUfkx-9AqM3c9dTw29FI6BA7oWU24LFeiIK39w2JBpnXVlYf3EgwLfpn7O4zog_G7V7KHlySA5_es-yDKjyS0kKaxNtnhhQHx6KkA");

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
        httpPut.setHeader("Authorization","Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImFHMHNKNHNtME1uMVhiWVhGTnZWY2ltU0ZPWWV0Q2JYMG1fN0tOSHQtMGsifQ.eyJqdGkiOiI4MmJmMTI5OC00ZjBhLTRmNDAtOThjNC1iMjNmMTU0ZTJhYjgiLCJleHAiOjE1NDE3ODA4MjUsIm5iZiI6MCwiaWF0IjoxNTM5MTg4ODI1LCJpc3MiOiJodHRwOi8vaW5mcmEtaW50LnN0YWdpbmcua2VlYm9vdC5jb206ODA4MC9hdXRoL3JlYWxtcy9rZWVib290IiwiYXVkIjoia2VlYm9vdF9tYWluIiwic3ViIjoiOGNhM2NhMmQtZTY4Yy00OWZiLTkzNDAtODE3NjNjNzFlOGUyIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoia2VlYm9vdF9tYWluIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiNjA0MGRhZTAtYTIwZC00MGZhLWE5NzAtMjI0NjViMDVhMzA0IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6W10sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6IjU1NjFjYjUwYmU5MzRlMGVhNDljOGUwOTMyOTY4ZDc4IiwiS0JVSUQiOiI1NTYxQ0I1MEJFOTM0RTBFQTQ5QzhFMDkzMjk2OEQ3OCIsIkFQUFRZUEUiOiJLRUVCT09ULU1BSU4tV0VCIiwiS0JUSUQiOiI1NkNENTcxMDFEN0U0RDZEODMwRjk2M0MzREMyMkFCNCIsIktCRUlEIjoiN0E3OTBBMkIwQkUzNDUxNTk0MkQ3OUI2MDc0ODUwQTQifQ.04g0Uyue7avtp2FXIW1C093dMVzhDcj6PIaQ1MJqU5Vw5pfWiMzXS9um-iiqYFrpUknS3O5gwQ5ynmCfdQzZYa13Uqvzvx9kpvUAii-ZGlRJDsXqZ8eI4qpWgjDEKyCK5T0vFKeCteocWwiDaILxp4CBXitXaULjdCMV9oRHL5AWQVbbtE1lVplMWBR5WiRhYQ9_1GPmF6Y4llBnEI6gnoDcu6zfXP8Ss8Eoe9RdDJ5IMoMFFDTG-8LmJSXbCuawafNCUjQC1LnKPX7leEnGjoPSP0Y0Ar4WjVeBUvTaU8b_XnQzKXj45mBANJLLou-Nyg7f7yXA3DL3UNYsSyH5Pg");

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