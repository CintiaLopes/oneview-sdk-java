/*******************************************************************************
 * (C) Copyright 2015 Hewlett Packard Enterprise Development LP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.hp.ov.sdk.rest.http.core.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.ProtocolException;
import java.net.HttpURLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLException;

import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hp.ov.sdk.constants.SdkConstants;
import com.hp.ov.sdk.dto.HttpMethodType;
import com.hp.ov.sdk.exceptions.SdkRuntimeException;
import com.hp.ov.sdk.exceptions.SDKApplianceNotReachableException;
import com.hp.ov.sdk.exceptions.SDKBadRequestException;
import com.hp.ov.sdk.exceptions.SDKCertificateException;
import com.hp.ov.sdk.exceptions.SDKErrorEnum;
import com.hp.ov.sdk.exceptions.SDKForbiddenException;
import com.hp.ov.sdk.exceptions.SDKInternalServerErrorException;
import com.hp.ov.sdk.exceptions.SDKMethodNotAllowed;
import com.hp.ov.sdk.exceptions.SDKNoSuchUrlException;
import com.hp.ov.sdk.exceptions.SDKResourceNotFoundException;
import com.hp.ov.sdk.exceptions.SDKSSLHandshakeException;
import com.hp.ov.sdk.exceptions.SDKSocketException;
import com.hp.ov.sdk.exceptions.SDKUnauthorizedException;
import com.hp.ov.sdk.util.StringUtil;

@Component
public class HttpRestClientImpl implements HttpRestClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpRestClientImpl.class);
    private static final String ACCEPT = "application/json";
    private static final String ACCEPT_LANGAUGE = "en_US";
    private static final String CHAR_SET = "UTF-8";
    private static final String CONTENT_TYPE =
        "application/json; charset="+CHAR_SET;
    private static final String CONTENT_TYPE_STRING =
        "text/plain; charset="+CHAR_SET;
    private static final int TIMEOUT = 0;

    /**
     * this method mainly used to get sync response from REST API
     * 
     * @throws SDKResourceNotFoundException
     * @throws SDKNoSuchUrlException
     */

    /**
     * Send the request to OV and read the response.
     * @return a string representing the response data.
     * @throws SDKBadRequestException on unsupported method (PUT, GET..)
     **/
    public String sendRequestToHPOV(final RestParams params,
                                    final JSONObject jsonObject) {

        logger.debug("Rest params passed, params=: " + params
                     + " jsonObject = :" + jsonObject);
        if (jsonObject == null) {
           return sendRequestToHPOV(params, null, CONTENT_TYPE);
        }
        return sendRequestToHPOV(params, jsonObject.toString(), CONTENT_TYPE);
    }

    /**
     * Send the request to OV and read the response.
     * @return a string representing the response data.
     * @throws SDKBadRequestException on unsupported method (PUT, GET..)
     **/
    public String sendRequestToHPOV(RestParams params, String scriptObject) {
        logger.debug("Rest params passed, params=: " + params
                     + " scriptObject = :" + scriptObject);
        return sendRequestToHPOV(params, scriptObject, CONTENT_TYPE_STRING);
    }

    @Deprecated
    public String sendStringRequestToHPOV(RestParams params,
                                          String scriptObject) {
        return sendRequestToHPOV(params, scriptObject);
    }

    /**
     * Send the request to OV and read the response.
     * @return a string representing the response data.
     * @throws SDKBadRequestException on unsupported method (PUT, GET..)
     **/
    private String sendRequestToHPOV(final RestParams params,
                                     final String scriptObject,
                                     final String contentType) 
                                     throws SDKBadRequestException {

        logger.debug("Rest params passed, params= " + params 
                     + "object = " + scriptObject
                     + "contentType = " + contentType);

        BufferedReader bufferedReader = null;
        final StringBuilder sb = new StringBuilder();
        int responseCode = 0;
        HttpsURLConnection connection = null;
        try {
            connection = getConnection(params, contentType);
        } catch (ProtocolException e) {
            throw new SDKBadRequestException(SDKErrorEnum.badRequestError,
                                             null, null, null,
                                             SdkConstants.APPLIANCE, e);
        }
        try {
            // TODO- finally
            if ((params.getType() == HttpMethodType.POST 
                 || params.getType() == HttpMethodType.PUT)
                && null != scriptObject) {
                // May cause IOException as it tries to connect
                final OutputStream os = connection.getOutputStream();
                os.write(scriptObject.getBytes(CHAR_SET));
                os.close();
            }
            // Try to get the response code
            // May cause IOException as it tries to connect
            responseCode = connection.getResponseCode();
            // Handle the response code checks
            // Throws exception if not in 200 family.
            responseCode = checkResponse(responseCode);

            if (responseCode == HttpsURLConnection.HTTP_NO_CONTENT) {
                sb.append("{}");
            } else {
                final DataInputStream inputStream =
                    new DataInputStream(connection.getInputStream());
                final InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
                String string = null;
                while ((string = bufferedReader.readLine()) != null) {
                    sb.append(string);
                }
            }
            // TODO - for responseCode == 202
            // Implement review comment from Geoff
            // Async APIs should return Task URI in header, eg response = 202,
            // check location header vs returned object.  SDK should check 202,
            // get URI from location header then GET task as needed.
            logger.trace("string returned from server = " + sb);

        // TODO - exceptions
        } catch (SSLHandshakeException e) {
            throw new SDKSSLHandshakeException(SDKErrorEnum.sslHandshakeException,
                                              null, null, null,
                                              SdkConstants.APPLIANCE, e);
        } catch (SSLException e) {
            throw new SDKCertificateException(SDKErrorEnum.certificateError,
                                              null, null, null,
                                              SdkConstants.APPLIANCE, e);
        } catch (final IOException e) {
            try {
                checkResponse(responseCode, e);
            } catch (SdkRuntimeException ex) {
                if (ex.getCause() == null) {
                    ex.initCause(e);
                    throw ex;
                }
            }
            logger.error("Unhandled HTTP error " + responseCode, e);
            throw new SDKApplianceNotReachableException(SDKErrorEnum.applicanceNotReachable,
                                                        null, null, null,
                                                        SdkConstants.APPLIANCE,
                                                        e);
        } finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) { 
                    logger.debug("Error closing reader", ex);
                }
            }
            connection.disconnect();
        }

        return sb.toString();
    }

    /**
     * Gets a connection and configures it, does NOT open the connection.
     * @see URLConnection#openConnection
     * @return a connection to the URL
     * @throws ProtocolException if method (GET/PUT/...) not supported
     * @throws SDKNoSuchUrlException on invalid URL
     * @throws SDKApplianceNotReachableException on IO error
     **/
    private HttpsURLConnection getConnection(final RestParams params,
                                              final String contentType) 
                                              throws SDKNoSuchUrlException,
                                                     ProtocolException,
                                            SDKApplianceNotReachableException {
       return getConnection(params, contentType, true, true);
    }

    /**
     * Gets a connection and configures it, does NOT open the connection.
     * @see URLConnection#openConnection
     * @return a connection to the URL
     * @throws ProtocolException if method (GET/PUT/...) not supported
     * @throws SDKNoSuchUrlException on invalid URL
     * @throws SDKApplianceNotReachableException on IO error
     **/
    private HttpsURLConnection getConnection(final RestParams params,
                                             final String contentType,
                                             final boolean doInput,
                                             final boolean doOutput)
                                             throws SDKNoSuchUrlException,
                                                    ProtocolException,
                                            SDKApplianceNotReachableException {
        URL url_one = null;
        HttpsURLConnection connection = null;

        try {
            logger.debug("Using URL: " + params.getUrl());
            url_one = new URL(params.getUrl());
        } catch (final MalformedURLException e) {
            throw new SDKNoSuchUrlException(SDKErrorEnum.invalidUrl,
                                            null, null, null,
                                            SdkConstants.APPLIANCE, e);
        }
        try {
            connection = (HttpsURLConnection) url_one.openConnection();
        } catch (IOException e) {
            // This makes no sense.
            // What other than NotReachable would a NotReachable exception be?
            SDKErrorEnum eType = SDKErrorEnum.applicanceNotReachable;
            throw new SDKApplianceNotReachableException(eType,
                                                        null, null, null,
                                                        SdkConstants.APPLIANCE,
                                                        e);
        }

        connection.setDoInput(doInput);
        connection.setDoOutput(doOutput);

        // TODO - move the headers to KeyValuePair
        if (params.getSessionId() != null) {
            connection.setRequestProperty("auth", params.getSessionId());
        }
        connection.setRequestProperty("Accept", ACCEPT);
        connection.setRequestProperty("accept-language", ACCEPT_LANGAUGE);

        connection.setRequestProperty("X-API-Version",
                                       String.valueOf(params.getApiVersion()));
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestMethod(params.getType().toString());
        connection.setConnectTimeout(TIMEOUT);

        connection = configureSSL(connection, params);

        return connection;
    }

    /**
     * Checks the HTTP response codes, on error throws the correct exception.
     * @return the response code
     */
    private int checkResponse(final int responseCode) {
       return checkResponse(responseCode, null);
    }

    /**
     * Checks the HTTP response codes, on error throws the correct exception.
     * Sets the exception cause as e if it throws one.
     * @return the response code
     */
    private int checkResponse(final int responseCode, Throwable e) {
        logger.debug("Response code = " + responseCode);
        switch (responseCode) {
            case HttpsURLConnection.HTTP_OK:
            case HttpsURLConnection.HTTP_CREATED:
            case HttpsURLConnection.HTTP_ACCEPTED:
            case HttpsURLConnection.HTTP_NOT_AUTHORITATIVE:
            case HttpsURLConnection.HTTP_NO_CONTENT:
            case HttpsURLConnection.HTTP_RESET:
            case HttpsURLConnection.HTTP_PARTIAL:
                return responseCode;

            case HttpsURLConnection.HTTP_NOT_FOUND:
                throw new SDKResourceNotFoundException(SDKErrorEnum.resourceNotFound,
                                                       null, null, null,
                                                       SdkConstants.APPLIANCE,
                                                       e);
                // TODO - check for error code 400 - bad request ( duplicate
                // resource, wrongId, incorrect data)
                // throw SDKDuplicateResourceException
            case HttpsURLConnection.HTTP_BAD_REQUEST:
                throw new SDKBadRequestException(SDKErrorEnum.badRequestError,
                                                 null, null, null,
                                                 SdkConstants.APPLIANCE, e);
            case HttpsURLConnection.HTTP_FORBIDDEN:
                throw new SDKForbiddenException(SDKErrorEnum.forbiddenRequestError,
                                                null, null, null,
                                                SdkConstants.APPLIANCE, e);
            case HttpsURLConnection.HTTP_BAD_METHOD:
                throw new SDKMethodNotAllowed(SDKErrorEnum.methodNotFound,
                                              null, null, null,
                                              SdkConstants.APPLIANCE, e);
            case HttpsURLConnection.HTTP_UNAUTHORIZED:
                throw new SDKUnauthorizedException(SDKErrorEnum.unauthorized,
                                                   null, null, null,
                                                   SdkConstants.APPLIANCE, e);
            case HttpsURLConnection.HTTP_CONFLICT:
            case HttpsURLConnection.HTTP_PRECON_FAILED:
            case HttpsURLConnection.HTTP_UNSUPPORTED_TYPE:
            case HttpsURLConnection.HTTP_INTERNAL_ERROR:
            case HttpsURLConnection.HTTP_UNAVAILABLE:
                throw new SDKInternalServerErrorException(SDKErrorEnum.internalServerError,
                                                          null, null, null,
                                                          SdkConstants.APPLIANCE, e);
            case HttpsURLConnection.HTTP_BAD_GATEWAY:
            case HttpsURLConnection.HTTP_CLIENT_TIMEOUT:
            case HttpsURLConnection.HTTP_ENTITY_TOO_LARGE:
            case HttpsURLConnection.HTTP_GATEWAY_TIMEOUT:
            case HttpsURLConnection.HTTP_GONE:
            case HttpsURLConnection.HTTP_LENGTH_REQUIRED:
            case HttpsURLConnection.HTTP_MOVED_PERM:
            case HttpsURLConnection.HTTP_MOVED_TEMP:
            case HttpsURLConnection.HTTP_MULT_CHOICE:
            case HttpsURLConnection.HTTP_NOT_ACCEPTABLE:
            case HttpsURLConnection.HTTP_NOT_IMPLEMENTED:
            case HttpsURLConnection.HTTP_NOT_MODIFIED:
            case HttpsURLConnection.HTTP_PAYMENT_REQUIRED:
            case HttpsURLConnection.HTTP_PROXY_AUTH:
            case HttpsURLConnection.HTTP_REQ_TOO_LONG:
            case HttpsURLConnection.HTTP_SEE_OTHER:
            case HttpsURLConnection.HTTP_USE_PROXY:
            case HttpsURLConnection.HTTP_VERSION:
            default:
                throw new SDKApplianceNotReachableException(SDKErrorEnum.applicanceNotReachable,
                                                            null, null, null,
                                                            SdkConstants.APPLIANCE, e);
        }
    }

    /**
     * Configure all the SSL handlers for the connection.
     * @return the connection
     */
    private HttpsURLConnection configureSSL(final HttpsURLConnection conn,
                                            final RestParams params) {
        if (params == null || conn == null) {
            logger.error("Unable to configure SSL, null input.");
            return conn;
        }

        // We dont need to set it the best available is used.
        // See SSLContext.init()
        TrustManager[] trustManagers = null;

        // If they want a different manager we need to use that.
        if (params.hasTrustManager()) {
           logger.debug("Using user supplied trust manager: "
                        + params.getTrustManager().getClass().getName());
           trustManagers = new TrustManager[] {params.getTrustManager()};
        } else {
           logger.debug("Using system trust manager");
        }

        // Reinit the SSL context, if any of the system properties were set
        // this will pick them up, otherwise an existing context will be
        // used and they may be ignored.
        // See Java technotes/guides/security/StandardNames.html#SSLContext
        try {
            // SSL, SSLv2, SSLv3, TLS, TLSv1, TLSv1.1, TLSv1.2
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, // Use the best available key manager
                    trustManagers, // If null best available is used
                    new java.security.SecureRandom());
            conn.setSSLSocketFactory(sc.getSocketFactory());
        } catch (NoSuchAlgorithmException ex) {
            logger.error("Unable to set TrustManager", ex);
        } catch (KeyManagementException ex) {
            logger.error("Unable to set TrustManager", ex);
        }
  
        // Use a different host verifier?
        if (params.hasHostnameVerifier()) {
            logger.debug("Using user supplied host verifier: " + 
                         params.getHostnameVerifier().getClass().getName());
            conn.setHostnameVerifier(params.getHostnameVerifier());
        }

        return conn;
    }
}