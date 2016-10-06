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

import static com.hp.ov.sdk.rest.client.networking.InterconnectTypeClient.INTERCONNECT_TYPE_URI;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeToken;
import com.hp.ov.sdk.dto.InterconnectType;
import com.hp.ov.sdk.dto.InterconnectTypeName;
import com.hp.ov.sdk.dto.ResourceCollection;
import com.hp.ov.sdk.rest.client.BaseClient;
import com.hp.ov.sdk.rest.http.core.HttpMethod;
import com.hp.ov.sdk.rest.http.core.UrlParameter;
import com.hp.ov.sdk.rest.http.core.client.Request;
import com.hp.ov.sdk.rest.reflect.ClientRequestHandler;

@RunWith(MockitoJUnitRunner.class)
public class InterconnectTypeClientTest {

    private static final String ANY_RESOURCE_ID = "random-UUID";
    private static final String ANY_RESOURCE_NAME = "random-name";

    private BaseClient baseClient = mock(BaseClient.class);
    private InterconnectTypeClient client = Reflection.newProxy(InterconnectTypeClient.class,
            new ClientRequestHandler<>(baseClient, InterconnectTypeClient.class));

    @Test
    public void shouldGetInterconnectType() {
        client.getById(ANY_RESOURCE_ID);

        String expectedUri = INTERCONNECT_TYPE_URI + "/" + ANY_RESOURCE_ID;
        Request expectedRequest = new Request(HttpMethod.GET, expectedUri);

        then(baseClient).should().executeRequest(expectedRequest, TypeToken.of(InterconnectType.class).getType());
    }

    @Test
    public void shouldGetAllInterconnectType() {
        client.getAll();
        Request expectedRequest = new Request(HttpMethod.GET, INTERCONNECT_TYPE_URI);

        then(baseClient).should().executeRequest(expectedRequest,
                new TypeToken<ResourceCollection<InterconnectType>>() {}.getType());
    }

    @Test
    public void shouldGetInterconnectTypeCollectionByName() {
        client.getByName(ANY_RESOURCE_NAME);

        Request expectedRequest = new Request(HttpMethod.GET, INTERCONNECT_TYPE_URI);
        expectedRequest.addQuery(UrlParameter.getFilterByNameParameter(ANY_RESOURCE_NAME));

        then(baseClient).should().executeRequest(expectedRequest,
                new TypeToken<ResourceCollection<InterconnectType>>() {}.getType());
    }

    @Test
    public void shouldGetInterconnectTypeCollectionByNameType() {
        client.getByName(InterconnectTypeName.HP_VC_8Gb_20_Port_FC_Module);

        Request expectedRequest = new Request(HttpMethod.GET, INTERCONNECT_TYPE_URI);
        expectedRequest.addQuery(UrlParameter.getFilterByNameParameter(InterconnectTypeName.HP_VC_8Gb_20_Port_FC_Module.getValue()));

        then(baseClient).should().executeRequest(expectedRequest,
                new TypeToken<ResourceCollection<InterconnectType>>() {}.getType());
    }

}
