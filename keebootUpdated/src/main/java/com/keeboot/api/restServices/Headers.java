package com.keeboot.api.restServices;

import static com.keeboot.utils.TestReporter.logDebug;
import static com.keeboot.utils.TestReporter.logTrace;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.keeboot.utils.Randomness;

public class Headers {
    /**
     * Used in coordination with RestService.createHeader() <br/>
     * Enums: <br/>
     * <b>AUTH</b>:<br/>
     * Content-type: application/x-www-form-urlencoded<br/>
     * <br/>
     * <b>BASIC_CONVO</b>:<br/>
     * Content-type: application/json;charset=utf-8 <br/>
     * Accept: application/json <br/>
     * username: test.user <br/>
     * messageId: Random Alphanumic string <br/>
     * Connection: keep-alive <br/>
     * ConversationId: Random Alphanumic string <br/>
     * requestedTimestamp: Current timestamp<br/>
     * <br/>
     * <b>JSON</b>:<br/>
     * Content-type: application/json<br/>
     *
     */
    public static enum HeaderType {
        BASIC_CONVO, JSON, WWWFORM, WWWFORM_TO_JSON, JSON_TO_JSON, XML, TEXT;
    }

    /**
     * Automatically populates headers based on predefined options from RestService.HeaderType
     *
     * @param type
     *            Uses options from RestService.HeaderType enum
     */
    public static Header[] createHeader(HeaderType type) {
        Header[] headers = null;
        switch (type) {
            case BASIC_CONVO:
                logDebug("Creating headers for [BASIC_CONVO]");
                headers = new Header[] {
                        new BasicHeader("Content-type", "application/json;charset=utf-8"),
                        new BasicHeader("Accept", "application/json"),
                        new BasicHeader("username", "test.user"),
                        new BasicHeader("messageId", Randomness.generateMessageId()),
                        new BasicHeader("Connection", "keep-alive"),
                        new BasicHeader("requestedTimestamp", Randomness.generateCurrentXMLDatetime() + ".000-04:00")
                };
                break;
            case JSON:
                logTrace("Creating headers for sending request in [JSON] format");
                headers = new Header[] {
                        new BasicHeader("Content-type", "application/json")
                };
                break;
            case WWWFORM:
                logTrace("Creating headers for sending request in [WWW-FORM-URL-ENCODED] format");
                headers = new Header[] {
                        new BasicHeader("Content-type", "application/x-www-form-urlencoded") };
                break;
            case WWWFORM_TO_JSON:
                logTrace("Creating headers for sending request in [WWW-FORM-URL-ENCODED] format");
                logTrace("Creating headers for accepting response in [JSON] format");
                headers = new Header[] {
                        new BasicHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8"),
                        new BasicHeader("Accept", "application/json, text/plain, */*")
                };
                break;
            case JSON_TO_JSON:
                logTrace("Creating headers for sending request in [JSON] format");
                logTrace("Creating headers for accepting response in [JSON] format");
                headers = new Header[] { new BasicHeader("Content-type", "application/json; charset=UTF-8"),
                        new BasicHeader("Accept", "application/json, text/plain, */*") };
                break;

            case XML:
                logTrace("Creating headers for sending request in [XML] format");
                headers = new Header[] { new BasicHeader("Content-type", "text/xml"),
                        new BasicHeader("Accept-Encoding", "application/xml") };
                break;
            case TEXT:
                logTrace("Creating headers for sending request in [TEXT] format");
                headers = new Header[] { new BasicHeader("Content-type", "text/plain") };
                break;
            default:
                break;
        }

        // Logging headers
        String allHeaders = "";
        for (Header header : headers) {
            allHeaders += "[" + header.getName() + ": " + header.getValue() + "] ";
        }

        logTrace("Headers added " + allHeaders);

        return headers;
    }
}