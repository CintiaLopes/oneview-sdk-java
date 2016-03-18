/*******************************************************************************
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
 *******************************************************************************/
package com.hp.ov.sdk.rest.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.Gson;
import com.hp.ov.sdk.adaptors.StorageSystemAdaptor;
import com.hp.ov.sdk.constants.ResourceUris;
import com.hp.ov.sdk.dto.AddStorageSystemCredentials;
import com.hp.ov.sdk.dto.HttpMethodType;
import com.hp.ov.sdk.dto.StoragePoolCollection;
import com.hp.ov.sdk.dto.StorageSystemCollection;
import com.hp.ov.sdk.dto.StorageSystemV2;
import com.hp.ov.sdk.dto.StorageTargetPortCollection;
import com.hp.ov.sdk.dto.StorageTargetPortV2;
import com.hp.ov.sdk.exceptions.SDKInvalidArgumentException;
import com.hp.ov.sdk.exceptions.SDKNoResponseException;
import com.hp.ov.sdk.exceptions.SDKResourceNotFoundException;
import com.hp.ov.sdk.rest.http.core.client.HttpRestClient;
import com.hp.ov.sdk.rest.http.core.client.RestParams;
import com.hp.ov.sdk.util.UrlUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpRestClient.class})
public class StorageSystemClientTest {

    private RestParams params;
    private StorageSystemAdaptor adaptor;
    private StorageSystemClient client;

    private static String resourceId = "random-UUID";
    private static String resourceName = "random-name";
    private static String portId = "random-UUID";
    private String storageSystemJson = "";

    @Before
    public void setUp() throws Exception {
        params = new RestParams();
        adaptor = new StorageSystemAdaptor();

        PowerMockito.mockStatic(HttpRestClient.class);

        this.client = StorageSystemClientImpl.getClient();
    }

    @Test
    public void testGetStorageSystem() {
        storageSystemJson = this.getJsonFromFile("StorageSystemGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(storageSystemJson);

        StorageSystemV2 storageSystemDto = client.getStorageSystem(params, resourceId);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(params.getHostname(), ResourceUris.STORAGE_SYSTEM_URI, resourceId));
        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(storageSystemDto);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetStorageSystemWithNullParams() {
        client.getStorageSystem(null, resourceId);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetStorageSystemWithNullResponse() {
        storageSystemJson = this.getJsonFromFile("StorageSystemGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getStorageSystem(params, resourceId);
    }

    @Test
    public void testGetStoragePoolsForStorageSystem() {
        storageSystemJson = this.getJsonFromFile("StorageSystemPoolsGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(storageSystemJson);

        StoragePoolCollection storagePoolsDto = client.getStoragePoolsForStorageSystem(params, resourceId);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(params.getHostname(), ResourceUris.STORAGE_SYSTEM_URI, resourceId, ResourceUris.STORAGE_POOL_STORAGE_SYSTEM_URI));
        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(storagePoolsDto);
        assertEquals("Based on the JSON file, the total number of storage pools must be \"" + 1 + "\"", 1, storagePoolsDto.getCount());
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetStoragePoolsForStorageSystemWithNullParams() {
        client.getStoragePoolsForStorageSystem(null, resourceId);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetStoragePoolsForStorageSystemWithNullResponse() {
        storageSystemJson = this.getJsonFromFile("StorageSystemPoolsGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getStoragePoolsForStorageSystem(params, resourceId);
    }

    @Test
    public void testGetAllManagedPortsForStorageSystem() {
        storageSystemJson = this.getJsonFromFile("StorageSystemManagedPortsGetAll.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(storageSystemJson);

        StorageTargetPortCollection storageTargetPortCollectionDto = client.getAllManagedPortsForStorageSystem(params, resourceId);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(params.getHostname(), ResourceUris.STORAGE_SYSTEM_URI, resourceId, ResourceUris.MANANGED_PORTS_STORAGE_SYSTEM_URI));
        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(storageTargetPortCollectionDto);
        assertEquals("Based on the JSON file, the total number of managed ports must be \"" + 1 + "\"", 1, storageTargetPortCollectionDto.getCount());
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAllManagedPortsForStorageSystemWithNullParams() {
        client.getAllManagedPortsForStorageSystem(null, resourceName);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetAllManagedPortsForStorageSystemWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getAllManagedPortsForStorageSystem(params, resourceName);
    }

    @Test
    public void testGetManagedPortsForStorageSystem() {
        storageSystemJson = this.getJsonFromFile("StorageSystemManagedPortsGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(storageSystemJson);

        StorageTargetPortV2 storageTargetPortDto = client.getManagedPortsForStorageSystem(params, resourceId, portId);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(
                params.getHostname(),
                ResourceUris.STORAGE_SYSTEM_URI,
                resourceId,
                ResourceUris.MANANGED_PORTS_STORAGE_SYSTEM_URI,
                portId));
        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(storageTargetPortDto);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetManagedPortsForStorageSystemWithNullParams() {
        client.getManagedPortsForStorageSystem(null, resourceId, portId);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetManagedPortsForStorageSystemWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getManagedPortsForStorageSystem(params, resourceId, portId);
    }

    @Test
    public void testGetStorageSystemHostTypes() {
        storageSystemJson = this.getJsonFromFile("StorageSystemHostTypesGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(storageSystemJson);

        List<String> hostTypes = client.getStorageSystemHostTypes(params);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(params.getHostname(), ResourceUris.STORAGE_SYSTEM_URI, ResourceUris.STORAGE_SYSTEM_HOST_TYPES_URI));
        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(hostTypes);
        assertEquals("Based on the JSON file, the return object must have 22 elements",
                hostTypes.size(), 22);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetStorageSystemHostTypesWithNullParams() {
        client.getStorageSystemHostTypes(null);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetStorageSystemHostTypesWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getStorageSystemHostTypes(params);
    }

    @Test
    public void testGetAllStorageSystems() {
        storageSystemJson = this.getJsonFromFile("StorageSystemGetAll.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(storageSystemJson);

        StorageSystemCollection storageSystemCollectionDto = client.getAllStorageSystems(params);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(params.getHostname(), ResourceUris.STORAGE_SYSTEM_URI));
        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(storageSystemCollectionDto);
        assertEquals("Based on the JSON file, the return object must have 2 elements",
                storageSystemCollectionDto.getCount(), 2);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAllStorageSystemsWithNullParams() {
        client.getAllStorageSystems(null);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetAllStorageSystemsWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getAllStorageSystems(params);
    }

    @Test
    public void testGetStorageSystemByName() {
        storageSystemJson = this.getJsonFromFile("StorageSystemGetByName.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(storageSystemJson);

        StorageSystemV2 storageSystemDto = client.getStorageSystemByName(params, resourceName);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.STORAGE_SYSTEM_URI,
                UrlUtils.createFilterString(resourceName)));
        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(storageSystemDto);
    }

    @Test (expected = SDKResourceNotFoundException.class)
    public void testGetStorageSystemByNameWithNoMembers() {
        StorageSystemCollection storageSystemCollectionDto = adaptor.buildCollectionDto(this.getJsonFromFile("StorageSystemGetByName.json"));
        storageSystemCollectionDto.setCount(0);
        storageSystemJson = new Gson().toJson(storageSystemCollectionDto);

        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(storageSystemJson);

        client.getStorageSystemByName(params, resourceName);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetStorageSystemByNameWithNullParams() {
        client.getStorageSystemByName(null, resourceName);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetStorageSystemByNameWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getStorageSystemByName(params, resourceName);
    }

    @Test
    public void testCreateStorageSystem() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class),
                Mockito.any(JSONObject.class)))
        .thenReturn("{}");

        String result = client.createStorageSystem(
                params,
                new AddStorageSystemCredentials(),
                false);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(params.getHostname(), ResourceUris.STORAGE_SYSTEM_URI));
        rp.setType(HttpMethodType.POST);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp), Mockito.any(JSONObject.class));

        assertEquals("A success create storage system call returns the string \"Created\"", "Created", result);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testCreateStorageSystemWithNullParams() {
        client.createStorageSystem(null, new AddStorageSystemCredentials(), false);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testCreateStorageSystemWithNullDto() {
        client.createStorageSystem(params, null, false);
    }

    @Test
    public void testUpdateStorageSystem() {
        storageSystemJson = this.getJsonFromFile("StorageSystemGet.json");
        StorageSystemV2 storageSystemDto = adaptor.buildDto(storageSystemJson);

        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class),
                Mockito.any(JSONObject.class)))
        .thenReturn("{}");

        String result = client.updateStorageSystem(
                params,
                resourceId,
                storageSystemDto,
                false);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(params.getHostname(), ResourceUris.STORAGE_SYSTEM_URI, resourceId));
        rp.setType(HttpMethodType.PUT);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp), Mockito.any(JSONObject.class));

        assertEquals("A success update storage system call returns \"Updated\"", "Updated", result);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testUpdateEnclosureWithNullParams() {
        client.updateStorageSystem(null, resourceId, new StorageSystemV2(), false);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testUpdateEnclosureWithNullDto() {
        client.updateStorageSystem(params, resourceId, null, false);
    }

    @Test
    public void testDeleteStorageSystem() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn("{}");

        String result = client.deleteStorageSystem(params, resourceId);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(params.getHostname(), ResourceUris.STORAGE_SYSTEM_URI, resourceId));
        rp.setType(HttpMethodType.DELETE);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertEquals("A success delete storage system call returns \"Deleted\"", "Deleted", result);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testDeleteEnclosureGroupWithNullParams() {
        client.deleteStorageSystem(null, resourceId);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testDeleteStorageSystemWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.deleteStorageSystem(params, resourceId);
    }

    @Test
    public void testGetId() {
        storageSystemJson = this.getJsonFromFile("StorageSystemGetByName.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(storageSystemJson);

        String id = client.getId(params, resourceName);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.STORAGE_SYSTEM_URI,
                UrlUtils.createFilterString(resourceName)));
        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(id);
        assertEquals("Based on the JSON file, the return ID must be \"" + resourceId + "\"", resourceId, id);
    }

    private String getJsonFromFile(String filename) {
        String json = "";
        try {
            json = IOUtils.toString(
                    this.getClass().getResourceAsStream(filename),
                    "UTF-8"
                  );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

}