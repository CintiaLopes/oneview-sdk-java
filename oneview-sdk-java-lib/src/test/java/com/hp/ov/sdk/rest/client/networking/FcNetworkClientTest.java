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

import static org.mockito.BDDMockito.then;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.hp.ov.sdk.constants.ResourceUris;
import com.hp.ov.sdk.dto.networking.fcnetworks.FcNetwork;
import com.hp.ov.sdk.rest.client.BaseClient;
import com.hp.ov.sdk.rest.http.core.UrlParameter;

@RunWith(MockitoJUnitRunner.class)
public class FcNetworkClientTest {

    private static final String ANY_RESOURCE_ID = "random-UUID";
    private static final String ANY_RESOURCE_NAME = "random-Name";

    @Mock
    private BaseClient baseClient;

    @InjectMocks
    private FcNetworkClient client;

    @Test
    public void shouldGetFcNetwork() {
        client.getById(ANY_RESOURCE_ID);

        String expectedUri = ResourceUris.FC_NETWORK_URI + "/" + ANY_RESOURCE_ID;

        then(baseClient).should().getResource(expectedUri, FcNetwork.class);
    }

    @Test
    public void shouldGetAllFcNetwork() {
        client.getAll();

        then(baseClient).should().getResourceCollection(ResourceUris.FC_NETWORK_URI, FcNetwork.class);
    }

    @Test
    public void shouldGetFcNetworksByName() {
        client.getByName(ANY_RESOURCE_NAME);

        then(baseClient).should().getResourceCollection(ResourceUris.FC_NETWORK_URI,
                FcNetwork.class, UrlParameter.getFilterByNameParameter(ANY_RESOURCE_NAME));
    }

    @Test
    public void shouldCreateFcNetwork() {
        FcNetwork fcNetwork = new FcNetwork();

        client.create(fcNetwork, false);

        then(baseClient).should().createResource(ResourceUris.FC_NETWORK_URI, fcNetwork, false);
    }

    @Test
    public void shouldUpdateFcNetwork() {
        FcNetwork fcNetwork = new FcNetwork();

        client.update(ANY_RESOURCE_ID, fcNetwork, false);

        String expectedUri = ResourceUris.FC_NETWORK_URI + "/" + ANY_RESOURCE_ID;

        then(baseClient).should().updateResource(expectedUri, fcNetwork, false);
    }

    @Test
    public void shouldDeleteFcNetwork() {
        client.delete(ANY_RESOURCE_ID, false);

        String expectedUri = ResourceUris.FC_NETWORK_URI + "/" + ANY_RESOURCE_ID;

        then(baseClient).should().deleteResource(expectedUri, false);
    }

}