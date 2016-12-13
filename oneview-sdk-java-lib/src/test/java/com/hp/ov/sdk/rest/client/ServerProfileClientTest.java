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
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.Gson;
import com.hp.ov.sdk.adaptors.ServerProfileAdaptor;
import com.hp.ov.sdk.adaptors.TaskAdaptor;
import com.hp.ov.sdk.constants.ResourceUris;
import com.hp.ov.sdk.constants.SdkConstants;
import com.hp.ov.sdk.dto.AvailableStorageSystem;
import com.hp.ov.sdk.dto.AvailableStorageSystems;
import com.hp.ov.sdk.dto.AvailableTargets;
import com.hp.ov.sdk.dto.HttpMethodType;
import com.hp.ov.sdk.dto.Patch;
import com.hp.ov.sdk.dto.Patch.PatchOperation;
import com.hp.ov.sdk.dto.ServerProfileCollection;
import com.hp.ov.sdk.dto.ServerProfileCompliancePreview;
import com.hp.ov.sdk.dto.ServerProfileHealth;
import com.hp.ov.sdk.dto.TaskResourceV2;
import com.hp.ov.sdk.dto.TaskState;
import com.hp.ov.sdk.dto.generated.AvailableNetworks;
import com.hp.ov.sdk.dto.generated.AvailableServers;
import com.hp.ov.sdk.dto.generated.ProfilePorts;
import com.hp.ov.sdk.dto.generated.ServerProfile;
import com.hp.ov.sdk.exceptions.SDKInvalidArgumentException;
import com.hp.ov.sdk.exceptions.SDKNoResponseException;
import com.hp.ov.sdk.exceptions.SDKResourceNotFoundException;
import com.hp.ov.sdk.rest.http.core.client.HttpRestClient;
import com.hp.ov.sdk.rest.http.core.client.RestParams;
import com.hp.ov.sdk.tasks.TaskMonitorManager;
import com.hp.ov.sdk.util.UrlUtils;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ HttpRestClient.class, TaskMonitorManager.class })
public class ServerProfileClientTest {

    private RestParams params;
    private ServerProfileAdaptor adaptor;
    private ServerProfileClient client;

    private static String resourceId = "random-UUID";
    private static String resourceName = "random-name";
    private String spJson = "";

    @Mock
    private TaskMonitorManager taskMonitorManager;
    private TaskAdaptor taskAdaptor;

    @Before
    public void setUp() throws Exception {
        params = new RestParams();
        taskAdaptor = TaskAdaptor.getInstance();
        adaptor = new ServerProfileAdaptor();

        PowerMockito.mockStatic(HttpRestClient.class);
        PowerMockito.mockStatic(TaskMonitorManager.class);
        PowerMockito.when(TaskMonitorManager.getInstance()).thenReturn(taskMonitorManager);

        this.client = ServerProfileClientImpl.getClient();
    }

    @Test
    public void testGetServerProfile() {
        spJson = this.getJsonFromFile("ServerProfileGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(Mockito.any(RestParams.class))).thenReturn(spJson);

        ServerProfile serverProfileDto = client.getServerProfile(params, resourceId);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(params.getHostname(), ResourceUris.SERVER_PROFILE_URI, resourceId));
        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(serverProfileDto);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetServerProfileWithNullParams() {
        client.getServerProfile(null, resourceId);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetServerProfileWithNullResponse() {
        spJson = this.getJsonFromFile("ServerProfileGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getServerProfile(params, resourceId);
    }

    @Test
    public void testGetAllServerProfile() {
        spJson = this.getJsonFromFile("ServerProfileGetAll.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(spJson);

        ServerProfileCollection serverProfileList = client.getAllServerProfile(params);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(params.getHostname(), ResourceUris.SERVER_PROFILE_URI));
        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(serverProfileList);
        assertEquals("Based on the JSON file, the return object must have 1 element",
                serverProfileList.getCount(), 1);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAllServerProfileWithNullParams() {
        client.getAllServerProfile(null);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetAllServerProfileWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getAllServerProfile(params);
    }

    @Test
    public void testGetServerProfileByName() {
        spJson = this.getJsonFromFile("ServerProfileGetByName.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(spJson);

        ServerProfile serverProfileDto = client.getServerProfileByName(params, resourceName);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.SERVER_PROFILE_URI,
                UrlUtils.createFilterString(resourceName)));
        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(serverProfileDto);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetServerProfileByNameWithNullParams() {
        client.getServerProfileByName(null, resourceName);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetServerProfileByNameWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getServerProfileByName(params, resourceName);
    }

    @Test (expected = SDKResourceNotFoundException.class)
    public void testGetServerProfileByNameWithNoMembers() {
        ServerProfileCollection serverProfileList = adaptor.buildCollectionDto(this.getJsonFromFile("ServerProfileGetByName.json"));
        serverProfileList.setCount(0);
        spJson = new Gson().toJson(serverProfileList);

        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(spJson);

        client.getServerProfileByName(params, resourceName);
    }

    @Test
    public void testGetServerProfileCompliancePreview() {
        spJson = this.getJsonFromFile("ServerProfileCompliancePreviewGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(Mockito.any(RestParams.class))).thenReturn(spJson);

        ServerProfileCompliancePreview compliancePreviewDto = client.getServerProfileCompliancePreview(params, resourceId);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(
                params.getHostname(),
                ResourceUris.SERVER_PROFILE_URI,
                resourceId,
                SdkConstants.COMPLIANCE_PREVIEW));
        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(compliancePreviewDto);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetServerProfileCompliancePreviewWithNullParams() {
        client.getServerProfileCompliancePreview(null, resourceId);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetServerProfileCompliancePreviewWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getServerProfileCompliancePreview(params, resourceId);
    }

    @Test
    public void testGetServerProfileMessages() {
        spJson = this.getJsonFromFile("ServerProfileMessagesGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(Mockito.any(RestParams.class))).thenReturn(spJson);

        ServerProfileHealth healthDto = client.getServerProfileMessages(params, resourceId);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(
                params.getHostname(),
                ResourceUris.SERVER_PROFILE_URI,
                resourceId,
                SdkConstants.MESSAGES));
        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(healthDto);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetServerProfileMessagesWithNullParams() {
        client.getServerProfileMessages(null, resourceId);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetServerProfileMessagesWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getServerProfileMessages(params, resourceId);
    }

    @Test
    public void testGetServerProfileTransformation() {
        spJson = this.getJsonFromFile("ServerProfileGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(Mockito.any(RestParams.class))).thenReturn(spJson);

        ServerProfile serverProfileDto = client.getServerProfileTransformation(params, resourceId, "uri", "uri");

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(
                params.getHostname(),
                ResourceUris.SERVER_PROFILE_URI,
                resourceId,
                SdkConstants.TRANSFORMATION,
                "?serverHardwareTypeUri=uri&enclosureGroupUri=uri"));
        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(serverProfileDto);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetServerProfileTransformationWithNullParams() {
        client.getServerProfileTransformation(null, resourceId, "uri", "uri");
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetServerProfileTransformationWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getServerProfileTransformation(params, resourceId, "uri", "uri");
    }

    @Test
    public void testGetAvailableNetworksForServerProfile() {
        spJson = this.getJsonFromFile("ServerProfileAvailableNetworksGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(Mockito.any(RestParams.class))).thenReturn(spJson);

        AvailableNetworks networksDto = client.getAvailableNetworksForServerProfile(params, "uri", "uri");

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.AVAILABLE_NETWORKS_URI,
                "serverHardwareTypeUri=uri&enclosureGroupUri=uri"));
        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(networksDto);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAvailableNetworksForServerProfileWithNullParams() {
        client.getAvailableNetworksForServerProfile(null, "uri", "uri");
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetAvailableNetworksForServerProfileWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getAvailableNetworksForServerProfile(params, "uri", "uri");
    }

    @Test
    public void testGetAvailableServersForServerProfileRestParams() {
        spJson = this.getJsonFromFile("ServerProfileAvailableServersGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(Mockito.any(RestParams.class))).thenReturn(spJson);

        List<AvailableServers> servers = client.getAvailableServersForServerProfile(params);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(
                params.getHostname(),
                ResourceUris.AVAILABLE_SERVERS_URI));

        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(servers);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAvailableServersForServerProfileRestParamsWithNullParams() {
        client.getAvailableServersForServerProfile(null);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetAvailableServersForServerProfileRestParamsWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getAvailableServersForServerProfile(params);
    }

    @Test
    public void testGetAvailableServersForServerProfileRestParamsStringString() {
        spJson = this.getJsonFromFile("ServerProfileAvailableServersGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(Mockito.any(RestParams.class))).thenReturn(spJson);

        List<AvailableServers> servers = client.getAvailableServersForServerProfile(params, "uri", "uri");

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.AVAILABLE_SERVERS_URI,
                "serverHardwareTypeUri=uri&enclosureGroupUri=uri"));

        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(servers);
    }

    @Test
    public void testGetAvailableServersForServerProfileRestParamsStringStringWithEmptyList() {
        Mockito.when(
                HttpRestClient.sendRequestToHPOV(
                        Mockito.any(RestParams.class)))
        .thenReturn("[]");

        List<AvailableServers> servers = client.getAvailableServersForServerProfile(params, "uri", "uri");

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.AVAILABLE_SERVERS_URI,
                "serverHardwareTypeUri=uri&enclosureGroupUri=uri"));

        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(servers);
        assertEquals("The servers list is expected to be empty", 0, servers.size());
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAvailableServersForServerProfileRestParamsStringStringWithNullParams() {
        client.getAvailableServersForServerProfile(null, "uri", "uri");
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetAvailableServersForServerProfileRestParamsStringStringWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getAvailableServersForServerProfile(params, "uri", "uri");
    }

    @Test
    public void testGetAvailableServersForServerProfileRestParamsString() {
        spJson = this.getJsonFromFile("ServerProfileAvailableServersGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(Mockito.any(RestParams.class))).thenReturn(spJson);

        List<AvailableServers> servers = client.getAvailableServersForServerProfile(params, "uri");

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(params.getHostname(),
                ResourceUris.AVAILABLE_SERVERS_URI,
                "profileUri=uri"));

        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(servers);
    }
    @Test
    public void testGetAvailableServersForServerProfileRestParamsStringWithEmptyList() {
        Mockito.when(
                HttpRestClient.sendRequestToHPOV(
                        Mockito.any(RestParams.class)))
        .thenReturn("[]");

        List<AvailableServers> servers = client.getAvailableServersForServerProfile(params, "uri");

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(params.getHostname(),
                ResourceUris.AVAILABLE_SERVERS_URI,
                "profileUri=uri"));

        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(servers);
        assertEquals("The servers list is expected to be empty", 0, servers.size());
    }
    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAvailableServersForServerProfileRestParamsStringWithNullParams() {
        client.getAvailableServersForServerProfile(null, "uri");
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetAvailableServersForServerProfileRestParamsStringWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getAvailableServersForServerProfile(params, "uri");
    }

    @Test
    public void testGetAvailableStorageSystemForServerProfile() {
        spJson = this.getJsonFromFile("ServerProfileAvailableStorageSystemGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(Mockito.any(RestParams.class))).thenReturn(spJson);

        AvailableStorageSystem storageSystemDto = client.getAvailableStorageSystemForServerProfile(params, "uri", "uri", "id");

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.AVAILABLE_STORAGE_SYSTEM,
                "serverHardwareTypeUri=uri&enclosureGroupUri=uri&storageSystemId=id"));

        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(storageSystemDto);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAvailableStorageSystemForServerProfileWithEmptyEnclosureGroupUri() {
        client.getAvailableStorageSystemForServerProfile(params, "", "uri", "uri");
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAvailableStorageSystemForServerProfileWithEmptyServerHardwareTypeUri() {
        client.getAvailableStorageSystemForServerProfile(params, "uri", "", "uri");
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAvailableStorageSystemForServerProfileWithEmptyStorageSystemId() {
        client.getAvailableStorageSystemForServerProfile(params, "uri", "uri", "");
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAvailableStorageSystemForServerProfileWithNullParams() {
        client.getAvailableStorageSystemForServerProfile(null, "uri", "uri", "id");
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetAvailableStorageSystemForServerProfileWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getAvailableStorageSystemForServerProfile(params, "uri", "uri", "id");
    }

    @Test
    public void testGetAvailableStorageSystemsForServerProfile() {
        spJson = this.getJsonFromFile("ServerProfileAvailableStorageSystemsGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(Mockito.any(RestParams.class))).thenReturn(spJson);

        AvailableStorageSystems storageSystemsDto = client.getAvailableStorageSystemsForServerProfile(params, "uri", "uri");

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.AVAILABLE_STORAGE_SYSTEMS,
                "serverHardwareTypeUri=uri&enclosureGroupUri=uri"));

        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(storageSystemsDto);
    }

    @Test
    public void testGetAvailableStorageSystemsForServerProfileWithEmptyList() {
        Mockito.when(
                HttpRestClient.sendRequestToHPOV(
                        Mockito.any(RestParams.class)))
        .thenReturn("{\"type\":\"AvailableStorageSystemsV2\",\"members\":[],\"start\":0,\"total\":0,\"count\":0}");

        AvailableStorageSystems storageSystemsDto = client.getAvailableStorageSystemsForServerProfile(params, "uri", "uri");

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.AVAILABLE_STORAGE_SYSTEMS,
                "serverHardwareTypeUri=uri&enclosureGroupUri=uri"));

        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(storageSystemsDto);
        assertEquals("The storage system list must be empty", 0, storageSystemsDto.getCount());
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAvailableStorageSystemsForServerProfileWithNullParams() {
        client.getAvailableStorageSystemsForServerProfile(null, "uri", "uri");
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAvailableStorageSystemsForServerProfileWithNullEnclosureGroupUri() {
        client.getAvailableStorageSystemsForServerProfile(params, null, "uri");
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAvailableStorageSystemsForServerProfileWithNullServerHardwareTypeUri() {
        client.getAvailableStorageSystemsForServerProfile(params, "uri", null);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetAvailableStorageSystemsForServerProfileWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getAvailableStorageSystemsForServerProfile(params, "uri", "uri");
    }

    @Test
    public void testGetAvailableTargetsForServerProfileRestParams() {
        spJson = this.getJsonFromFile("ServerProfileAvailableTargetsGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(Mockito.any(RestParams.class))).thenReturn(spJson);

        AvailableTargets targetsDto = client.getAvailableTargetsForServerProfile(params);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.AVAILABLE_TARGETS,
                ""));

        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(targetsDto);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAvailableTargetsForServerProfileRestParamsWithNullParams() {
        client.getAvailableTargetsForServerProfile(null);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetAvailableTargetsForServerProfileRestParamsWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getAvailableTargetsForServerProfile(params);
    }

    @Test
    public void testGetAvailableTargetsForServerProfileRestParamsStringStringString() {
        spJson = this.getJsonFromFile("ServerProfileAvailableTargetsGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(Mockito.any(RestParams.class))).thenReturn(spJson);

        AvailableTargets targetsDto = client.getAvailableTargetsForServerProfile(params, "uri", "uri", "uri");

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.AVAILABLE_TARGETS,
                "enclosureGroupUri=uri&serverHardwareTypeUri=uri&profileUri=uri"));

        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(targetsDto);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetAvailableTargetsForServerProfileRestParamsStringStringStringWithNullParams() {
        client.getAvailableTargetsForServerProfile(null, "uri", "uri", "uri");
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetAvailableTargetsForServerProfileRestParamsStringStringStringWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getAvailableTargetsForServerProfile(params, "uri", "uri", "uri");
    }

    @Test
    public void testGetProfilePortsForServerProfile() {
        spJson = this.getJsonFromFile("ServerProfilePortsGet.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(Mockito.any(RestParams.class))).thenReturn(spJson);

        ProfilePorts portsDto = client.getProfilePortsForServerProfile(params, "id", "id");

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.PROFILE_PORTS_URI,
                "serverHardwareTypeUri=id&enclosureGroupUri=id"));

        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(portsDto);
    }

    @Test
    public void testGetProfilePortsForServerProfileWithEmptyList() {
        Mockito.when(
                HttpRestClient.sendRequestToHPOV(
                        Mockito.any(RestParams.class)))
        .thenReturn("{\"type\":\"ServerProfilePortV3\",\"ports\":[],\"category\":\"profile-ports\"}");

        ProfilePorts portsDto = client.getProfilePortsForServerProfile(params, "id", "id");

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.PROFILE_PORTS_URI,
                "serverHardwareTypeUri=id&enclosureGroupUri=id"));

        rp.setType(HttpMethodType.GET);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertNotNull(portsDto);
        assertEquals("The ports list must be empty", 0, portsDto.getPorts().size());
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testGetProfilePortsForServerProfileWithNullParams() {
        client.getProfilePortsForServerProfile(null, "id", "id");
    }

    @Test (expected = SDKNoResponseException.class)
    public void testGetProfilePortsForServerProfileWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.getProfilePortsForServerProfile(params, "id", "id");
    }

    @Test
    public void testCreateServerProfile() {
        spJson = this.getJsonFromFile("ServerProfileGet.json");
        ServerProfile serverProfileDto = adaptor.buildDto(spJson);

        String jsonCreateTaskCompleted = this.getJsonFromFile("ServerProfileTaskCompleted.json");
        TaskResourceV2 taskResourceV2 = taskAdaptor.buildDto(jsonCreateTaskCompleted);

        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class),
                Mockito.any(JSONObject.class)))
        .thenReturn(jsonCreateTaskCompleted);

        Mockito.when(taskMonitorManager.checkStatus(
                Mockito.any(RestParams.class),
                Mockito.anyString(),
                Mockito.anyInt()))
        .thenReturn(taskResourceV2);

        TaskResourceV2 result = client.createServerProfile(
                params,
                serverProfileDto,
                false,
                false);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(params.getHostname(), ResourceUris.SERVER_PROFILE_URI));
        rp.setType(HttpMethodType.POST);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp), Mockito.any(JSONObject.class));

        assertEquals("A success create server profile call returns task state \"Completed\"", TaskState.Completed, result.getTaskState());
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testCreateServerProfileWithNullParams() {
        client.createServerProfile(
                null,
                new ServerProfile(),
                false,
                false);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testCreateServerProfileWithNullDto() {
        client.createServerProfile(
                params,
                null,
                false,
                false);
    }

    @Test
    public void testUpdateServerProfile() {
        spJson = this.getJsonFromFile("ServerProfileGet.json");
        ServerProfile serverProfileDto = adaptor.buildDto(spJson);

        String jsonUpdateTaskCompleted = this.getJsonFromFile("ServerProfileTaskCompleted.json");
        TaskResourceV2 taskResourceV2 = taskAdaptor.buildDto(jsonUpdateTaskCompleted);

        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class),
                Mockito.any(JSONObject.class)))
        .thenReturn(jsonUpdateTaskCompleted);

        Mockito.when(taskMonitorManager.checkStatus(
                Mockito.any(RestParams.class),
                Mockito.anyString(),
                Mockito.anyInt()))
        .thenReturn(taskResourceV2);

        TaskResourceV2 result = client.updateServerProfile(
                params,
                resourceId,
                serverProfileDto,
                false,
                false);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(params.getHostname(), ResourceUris.SERVER_PROFILE_URI, resourceId));
        rp.setType(HttpMethodType.PUT);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp), Mockito.any(JSONObject.class));

        assertEquals("A success update server profile call returns task state \"Completed\"", TaskState.Completed, result.getTaskState());
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testUpdateServerProfileWithNullParams() {
        client.updateServerProfile( null, resourceId, new ServerProfile(), false, false);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testUpdateServerProfileWithNullDto() {
        client.updateServerProfile( params, resourceId, null, false, false);
    }

    @Test
    public void testPatchServerProfile() {
        String jsonPatchTaskCompleted = this.getJsonFromFile("ServerProfileTaskCompleted.json");
        TaskResourceV2 taskResourceV2 = taskAdaptor.buildDto(jsonPatchTaskCompleted);

        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class),
                Mockito.any(JSONArray.class)))
        .thenReturn(jsonPatchTaskCompleted);

        Mockito.when(taskMonitorManager.checkStatus(
                Mockito.any(RestParams.class),
                Mockito.anyString(),
                Mockito.anyInt()))
        .thenReturn(taskResourceV2);

        Patch patchDto = new Patch();
        patchDto.setOp(PatchOperation.replace);
        patchDto.setPath("/templateCompliance");
        patchDto.setValue("Compliant");

        TaskResourceV2 result = client.patchServerProfile(params, resourceId, patchDto , false);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(params.getHostname(), ResourceUris.SERVER_PROFILE_URI, resourceId));
        rp.setType(HttpMethodType.PATCH);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp), Mockito.any(JSONArray.class));

        assertEquals("A success patch server profile call returns task state \"Completed\"", TaskState.Completed, result.getTaskState());
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testPatchServerProfileWithNullParams() {
        client.patchServerProfile(null, resourceId, new Patch() , false);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testPatchServerProfileWithNullDto() {
        client.patchServerProfile(params, resourceId, null , false);
    }

    @Test
    public void testDeleteServerProfile() {
        String jsonDeleteTaskCompleted = this.getJsonFromFile("ServerProfileTaskCompleted.json");
        TaskResourceV2 taskResourceV2 = taskAdaptor.buildDto(jsonDeleteTaskCompleted);

        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(jsonDeleteTaskCompleted);

        Mockito.when(taskMonitorManager.checkStatus(
                Mockito.any(RestParams.class),
                Mockito.anyString(),
                Mockito.anyInt()))
        .thenReturn(taskResourceV2);

        taskResourceV2 = client.deleteServerProfile(params, resourceId, false);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestUrl(
                params.getHostname(),
                ResourceUris.SERVER_PROFILE_URI,
                resourceId));
        rp.setType(HttpMethodType.DELETE);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertEquals("A success delete server profile call returns \"Completed\"", "Completed", taskResourceV2.getTaskState().toString());
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testDeleteServerProfileWithNullParams() {
        client.deleteServerProfile(null, resourceId, false);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testDeleteServerProfileWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.deleteServerProfile(params, resourceId, false);
    }

    @Test
    public void testDeleteServerProfileByFilter() {
        String jsonDeleteTaskCompleted = this.getJsonFromFile("ServerProfileTaskCompleted.json");
        TaskResourceV2 taskResourceV2 = taskAdaptor.buildDto(jsonDeleteTaskCompleted);

        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(jsonDeleteTaskCompleted);

        Mockito.when(taskMonitorManager.checkStatus(
                Mockito.any(RestParams.class),
                Mockito.anyString(),
                Mockito.anyInt()))
        .thenReturn(taskResourceV2);

        taskResourceV2 = client.deleteServerProfileByFilter(params, "filter", true, false);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.SERVER_PROFILE_URI,
                "filter=\"name%20matches%20'%25filter%25'\""));
        rp.setType(HttpMethodType.DELETE);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertEquals("A success delete server profile call returns \"Completed\"", "Completed", taskResourceV2.getTaskState().toString());
    }


    @Test
    public void testDeleteServerProfileByFilterWithNoMatch() {
        String jsonDeleteTaskCompleted = this.getJsonFromFile("ServerProfileTaskCompleted.json");
        TaskResourceV2 taskResourceV2 = taskAdaptor.buildDto(jsonDeleteTaskCompleted);

        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(jsonDeleteTaskCompleted);

        Mockito.when(taskMonitorManager.checkStatus(
                Mockito.any(RestParams.class),
                Mockito.anyString(),
                Mockito.anyInt()))
        .thenReturn(taskResourceV2);

        taskResourceV2 = client.deleteServerProfileByFilter(params, "filter", false, false);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.SERVER_PROFILE_URI,
                UrlUtils.createFilterString("filter")));
        rp.setType(HttpMethodType.DELETE);

        PowerMockito.verifyStatic();
        HttpRestClient.sendRequestToHPOV(Mockito.eq(rp));

        assertEquals("A success delete server profile call returns \"Completed\"", "Completed", taskResourceV2.getTaskState().toString());
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testDeleteServerProfileByFilterWithNullFilter() {
        client.deleteServerProfileByFilter(params, null, true, false);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testDeleteServerProfileByFilterWithNullMatch() {
        client.deleteServerProfileByFilter(params, "filter", null, false);
    }

    @Test (expected = SDKInvalidArgumentException.class)
    public void testDeleteServerProfileByFilterWithNullParams() {
        client.deleteServerProfileByFilter(null, "filter", true, false);
    }

    @Test (expected = SDKNoResponseException.class)
    public void testDeleteServerProfileByFilterWithNullResponse() {
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(null);

        client.deleteServerProfileByFilter(params, "filter", true, false);
    }

    @Test
    public void testGetId() {
        spJson = this.getJsonFromFile("ServerProfileGetByName.json");
        Mockito.when(HttpRestClient.sendRequestToHPOV(
                Mockito.any(RestParams.class)))
        .thenReturn(spJson);

        String id = client.getId(params, resourceName);

        RestParams rp = new RestParams();
        rp.setUrl(UrlUtils.createRestQueryUrl(
                params.getHostname(),
                ResourceUris.SERVER_PROFILE_URI,
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
            json = IOUtils.toString(this.getClass().getResourceAsStream(filename), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

}