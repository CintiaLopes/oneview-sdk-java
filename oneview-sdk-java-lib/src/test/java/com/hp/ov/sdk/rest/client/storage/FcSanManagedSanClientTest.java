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

package com.hp.ov.sdk.rest.client.storage;

import static org.mockito.BDDMockito.then;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.hp.ov.sdk.constants.ResourceUris;
import com.hp.ov.sdk.dto.EndpointResponse;
import com.hp.ov.sdk.dto.EndpointsCsvFileResponse;
import com.hp.ov.sdk.dto.HttpMethodType;
import com.hp.ov.sdk.dto.fcsans.SanRequest;
import com.hp.ov.sdk.dto.fcsans.SanResponse;
import com.hp.ov.sdk.rest.client.BaseClient;
import com.hp.ov.sdk.rest.http.core.UrlParameter;
import com.hp.ov.sdk.rest.http.core.client.Request;

@RunWith(MockitoJUnitRunner.class)
public class FcSanManagedSanClientTest {

    private static final String ANY_RESOURCE_ID = "random-UUID";
    private static final String ANY_RESOURCE_NAME = "random-Name";

    @Mock
    private BaseClient baseClient;

    @InjectMocks
    private FcSanManagedSanClient client;

    @Test
    public void shouldGetFcSanManagedSanById() {
        client.getById(ANY_RESOURCE_ID);

        String expectedUri = ResourceUris.FC_SANS_MANAGED_SAN_URI + "/" + ANY_RESOURCE_ID;

        then(baseClient).should().getResource(expectedUri, SanResponse.class);
    }

    @Test
    public void shouldGetAllFcSanManagedSan() {
        client.getAll();

        then(baseClient).should().getResourceCollection(ResourceUris.FC_SANS_MANAGED_SAN_URI,
                SanResponse.class);
    }

    @Test
    public void shouldGetFcSanManagedSanByName() {
        client.getByName(ANY_RESOURCE_NAME);

        then(baseClient).should().getResourceCollection(ResourceUris.FC_SANS_MANAGED_SAN_URI,
                SanResponse.class, UrlParameter.getFilterByNameParameter(ANY_RESOURCE_NAME));
    }

    @Test
    public void shouldUpdateFcSanManagedSan() {
        SanRequest sanRequest = new SanRequest();

        client.update(ANY_RESOURCE_ID, sanRequest);

        String expectedUri = ResourceUris.FC_SANS_MANAGED_SAN_URI + "/" + ANY_RESOURCE_ID;
        Request expectedRequest = new Request(HttpMethodType.PUT, expectedUri, sanRequest);

        then(baseClient).should().executeRequest(expectedRequest, SanResponse.class);
    }

    @Test
    public void shouldGetFcSanManagedSanEndpoints() {
        client.getEndpoints(ANY_RESOURCE_ID);

        String expectedUri = ResourceUris.FC_SANS_MANAGED_SAN_URI
                + "/" + ANY_RESOURCE_ID
                + "/" + ResourceUris.FC_SANS_MANAGED_SAN_ENDPOINTS;

        then(baseClient).should().getResourceCollection(expectedUri, EndpointResponse.class);
    }

    @Test
    public void shouldCreateFcSanManagedSanIssuesReport() {
        client.createIssuesReport(ANY_RESOURCE_ID, false);

        String expectedUri = ResourceUris.FC_SANS_MANAGED_SAN_URI
                + "/" + ANY_RESOURCE_ID
                + "/" + ResourceUris.FC_SANS_MANAGED_SAN_ISSUES;
        Request expectedRequest = new Request(HttpMethodType.POST, expectedUri);

        then(baseClient).should().executeMonitorableRequest(expectedRequest, false);
    }

    @Test
    public void shouldCreateFcSanManagedSanEndpointsCsv() {
        client.createEndpointsCsv(ANY_RESOURCE_ID);

        String expectedUri = ResourceUris.FC_SANS_MANAGED_SAN_URI
                + "/" + ANY_RESOURCE_ID
                + "/" + ResourceUris.FC_SANS_MANAGED_SAN_ENDPOINTS;
        Request expectedRequest = new Request(HttpMethodType.POST, expectedUri);

        then(baseClient).should().executeRequest(expectedRequest, EndpointsCsvFileResponse.class);
    }
}