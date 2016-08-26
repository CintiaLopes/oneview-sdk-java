/*
 * (C) Copyright 2016 Hewlett Packard Enterprise Development LP
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
 */
package com.hp.ov.sdk.rest.client.networking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.ov.sdk.dto.HttpMethodType;
import com.hp.ov.sdk.dto.Patch;
import com.hp.ov.sdk.dto.ResourceCollection;
import com.hp.ov.sdk.dto.TaskResourceV2;
import com.hp.ov.sdk.dto.networking.sasinterconnects.SasInterConnectRefreshRequest;
import com.hp.ov.sdk.dto.networking.sasinterconnects.SasInterconnect;
import com.hp.ov.sdk.rest.client.BaseClient;
import com.hp.ov.sdk.rest.http.core.UrlParameter;
import com.hp.ov.sdk.rest.http.core.client.Request;
import com.hp.ov.sdk.util.UrlUtils;

public class SasInterconnectClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SasInterconnectClient.class);

    protected static final String SAS_INTERCONNECT_URI = "/rest/sas-interconnects";
    protected static final String SAS_INTERCONNECT_URI_REFRESH = "refreshState";

    private final BaseClient baseClient;

    public SasInterconnectClient(BaseClient baseClient) {
        this.baseClient = baseClient;
    }

    /**
     * Retrieves the {@link SasInterconnect} details for the specified SAS interconnect.
     *
     * @param resourceId SAS interconnect resource identifier as seen in HPE OneView.
     *
     * @return {@link SasInterconnect} object containing the details.
     */
    public SasInterconnect getById(String resourceId) {
        LOGGER.info("SasInterconnectClient : getById : Start");

        SasInterconnect fcNetwork = baseClient.getResource(
                UrlUtils.createUrl(SAS_INTERCONNECT_URI, resourceId), SasInterconnect.class);

        LOGGER.info("SasInterconnectClient : getById : End");

        return fcNetwork;
    }

    /**
     * Retrieves a {@link ResourceCollection}&lt;{@link SasInterconnect}&gt; containing the details
     * for all the available SAS interconnects found under the current HPE OneView.
     *
     * @return {@link ResourceCollection}&lt;{@link SasInterconnect}&gt; containing
     * the details for all found SAS interconnects.
     */
    public ResourceCollection<SasInterconnect> getAll() {
        LOGGER.info("SasInterconnectClient : getAll : Start");

        ResourceCollection<SasInterconnect> fcNetworks = baseClient.getResourceCollection(
                SAS_INTERCONNECT_URI, SasInterconnect.class);

        LOGGER.info("SasInterconnectClient : getAll : End");

        return fcNetworks;
    }

    /**
     * Retrieves a {@link ResourceCollection}&lt;{@link SasInterconnect}&gt; containing details
     * for the available SAS interconnects found under the current HPE OneView that match the name.
     *
     * @param name SAS interconnect name as seen in HPE OneView.
     *
     * @return {@link ResourceCollection}&lt;{@link SasInterconnect}&gt; containing
     * the details for the found SAS interconnects.
     */
    public ResourceCollection<SasInterconnect> getByName(String name) {
        LOGGER.info("SasInterconnectClient : getByName : Start");

        ResourceCollection<SasInterconnect> fcNetworks = baseClient.getResourceCollection(
                SAS_INTERCONNECT_URI, SasInterconnect.class, UrlParameter.getFilterByNameParameter(name));

        LOGGER.info("SasInterconnectClient : getByName : End");

        return fcNetworks;
    }

    /**
     * Performs a specific patch operation for the given SAS interconnect. There are a limited
     * set of SAS interconnect properties which may be changed. They are: 'powerState',
     * 'uidState', 'softResetState', 'hardResetState'. If the SAS interconnect supports the operation,
     * the operation is performed and a task is returned through which the results are reported.
     *
     * @param resourceId resource identifier for SAS interconnect as seen in HPE OneView.
     * @param patch containing the update to be made to existing SAS interconnect.
     * @param aSync flag to indicate whether the request should be processed
     * synchronously or asynchronously.
     *
     * @return {@link TaskResourceV2} containing the task status for the process.
     */
    public TaskResourceV2 patch(String resourceId, Patch patch, boolean aSync) {
        LOGGER.info("SasInterconnectClient : patch : Start");

        Request request = new Request(HttpMethodType.PATCH,
                UrlUtils.createUrl(SAS_INTERCONNECT_URI, resourceId), patch);
        request.setTimeout(300000);

        TaskResourceV2 taskResource = baseClient.executeMonitorableRequest(request, aSync);

        LOGGER.info("SasInterconnectClient : patch : End");

        return taskResource;
    }

    /**
     * Refresh a {@link SasInterconnect} identified by the given resource identifier.
     *
     * @param resourceId SAS interconnect resource identifier as seen in HPE OneView.
     * @param requestBody Initial SAS interconnect refresh state. At the moment refreshPending is permitted.
     * @param aSync flag to indicate whether the request should be processed
     * synchronously or asynchronously.
     *
     * @return {@link TaskResourceV2} containing the task status for the process.
     */
    public TaskResourceV2 refreshState(String resourceId, SasInterConnectRefreshRequest requestBody, boolean aSync) {
        LOGGER.info("SasInterconnectClient : refreshState : Start");

        Request request = new Request(HttpMethodType.PUT, UrlUtils.createUrl(
                SAS_INTERCONNECT_URI,
                resourceId,
                SAS_INTERCONNECT_URI_REFRESH),
                requestBody);

        request.setForceTaskReturn(true);
        request.setTimeout(120000);

        TaskResourceV2 taskResource = baseClient.executeMonitorableRequest(request, aSync);

        LOGGER.info("SasInterconnectClient : refreshState : End");

        return taskResource;
    }

}