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
package com.hp.ov.sdk.rest.client.facilities;

import com.hp.ov.sdk.OneViewClientSample;
import com.hp.ov.sdk.dto.ImportPdd;
import com.hp.ov.sdk.dto.Light;
import com.hp.ov.sdk.dto.OutletState;
import com.hp.ov.sdk.dto.Power;
import com.hp.ov.sdk.dto.PowerDeliveryDevice;
import com.hp.ov.sdk.dto.PowerDeliveryDeviceRefreshRequest;
import com.hp.ov.sdk.dto.RefreshState;
import com.hp.ov.sdk.dto.ResourceCollection;
import com.hp.ov.sdk.dto.TaskResourceV2;
import com.hp.ov.sdk.dto.UtilizationData;
import com.hp.ov.sdk.rest.client.OneViewClient;
import com.hp.ov.sdk.util.JsonPrettyPrinter;

public class PowerDeliveryDeviceClientSample {

    // test values - user input
    // ================================
    private static final String RESOURCE_ID = "66a80189-d239-4505-878c-7244c8fddc9b";
    private static final String RESOURCE_NAME = "172.18.8.11, PDU 1, L6,Outlet1";
    private static final String SAMPLE_RESOURCE_NAME = "SamplePDD";
    private static final String SAMPLE_RESOURCE_NAME_UPDATED = "SamplePDD-Updated";
    private static final String HOSTNAME = "172.18.8.11";
    private static final String USERNAME = "dcs";
    private static final String PASSWORD = "dcs";
    // ================================

    private final PowerDeliveryDeviceClient client;

    private PowerDeliveryDeviceClientSample() {
        OneViewClient oneViewClient = OneViewClientSample.getOneViewClient();

        client = oneViewClient.powerDeliveryDevice();
    }

    private void getPowerDeliveryDeviceById() {
        PowerDeliveryDevice powerDeliveryDevice = this.client.getById(RESOURCE_ID);

        System.out.println("PowerDeliveryDeviceClientSample : getPowerDeliveryDeviceById : " +
                "PowerDeliveryDevice object returned to client : " + powerDeliveryDevice.toJsonString());
    }

    private void getAllPowerDeliveryDevices() {
        ResourceCollection<PowerDeliveryDevice> powerDeliveryDevices = this.client.getAll();

        System.out.println("PowerDeliveryDeviceClientSample : getAllPowerDeliveryDevices : " +
                "PowerDeliveryDevices returned to client : " + powerDeliveryDevices.toJsonString());
    }

    private void getPowerDeliveryDeviceByName() {
        PowerDeliveryDevice powerDeliveryDevice = this.client.getByName(RESOURCE_NAME).get(0);

        System.out.println("PowerDeliveryDeviceClientSample : getPowerDeliveryDeviceByName : " +
                "PowerDeliveryDevice object returned to client : " + powerDeliveryDevice.toJsonString());
    }

    private void addPowerDeliveryDevice() {
        PowerDeliveryDevice powerDeliveryDevice = new PowerDeliveryDevice();
        powerDeliveryDevice.setName(SAMPLE_RESOURCE_NAME);
        powerDeliveryDevice.setRatedCapacity(40);

        PowerDeliveryDevice addedPowerDeliveryDevice = this.client.add(powerDeliveryDevice);

        System.out.println("PowerDeliveryDeviceClientSample : addPowerDeliveryDevice : " +
                "PowerDeliveryDevice object returned to client : " + addedPowerDeliveryDevice.toJsonString());
    }

    private void addPowerDeliveryDeviceByDiscover() {
        ImportPdd importPdd = new ImportPdd();

        importPdd.setHostname(HOSTNAME);
        importPdd.setUsername(USERNAME);
        importPdd.setPassword(PASSWORD);
        importPdd.setForce(true);

        TaskResourceV2 task = this.client.add(importPdd, false);

        System.out.println("PowerDeliveryDeviceClientSample : addPowerDeliveryDeviceByDiscover : " +
                "Task object returned to client : " + task.toJsonString());
    }

    private void updatePowerDeliveryDevice() {
        PowerDeliveryDevice powerDeliveryDevice = this.client.getByName(SAMPLE_RESOURCE_NAME).get(0);
        String resourceId = powerDeliveryDevice.getResourceId();

        powerDeliveryDevice.setName(SAMPLE_RESOURCE_NAME_UPDATED);

        PowerDeliveryDevice updatedPowerDeliveryDevice = this.client.update(resourceId,
                powerDeliveryDevice);

        System.out.println("PowerDeliveryDeviceClientSample : updatePowerDeliveryDevice : " +
                "PowerDeliveryDevice object returned to client : " + updatedPowerDeliveryDevice.toJsonString());
    }

    private void removePowerDeliveryDevice() {
        PowerDeliveryDevice powerDeliveryDevice = this.client.getByName(SAMPLE_RESOURCE_NAME).get(0);

        TaskResourceV2 task = this.client.remove(powerDeliveryDevice.getResourceId(), false);

        System.out.println("PowerDeliveryDeviceClientSample : removePowerDeliveryDevice : " +
                "Task object returned to client : " + task);
    }

    private void removePowerDeliveryDeviceByFilter() {
        String filter = "name='" + SAMPLE_RESOURCE_NAME +"'";
        TaskResourceV2 task = this.client.removeByFilter(filter, false);

        System.out.println("PowerDeliveryDeviceClientSample : removePowerDeliveryDeviceByFilter : " +
                "Task object returned to client : " + task.toJsonString());
    }

    private void removePowerDeliveryDeviceSynchronously() {
        PowerDeliveryDevice powerDeliveryDevice = this.client.getByName(SAMPLE_RESOURCE_NAME).get(0);

        String response = this.client.remove(powerDeliveryDevice.getResourceId());

        System.out.println("PowerDeliveryDeviceClientSample : removePowerDeliveryDeviceSynchronously : " +
                "Response returned to client : " + response);
    }

    private void getPowerDeliveryDevicePowerState() {
        PowerDeliveryDevice powerDeliveryDevice = this.client.getByName(RESOURCE_NAME).get(0);

        Power power = client.getPowerState(powerDeliveryDevice.getResourceId());

        System.out.println("PowerDeliveryDeviceClientSample : getPowerDeliveryDevicePowerState : " +
                "Power object returned to client : " + power);
    }

    private void updatePowerDeliveryDevicePowerState() {
        PowerDeliveryDevice powerDeliveryDevice = this.client.getByName(RESOURCE_NAME).get(0);
        OutletState outletState = new OutletState();

        outletState.setPowerState(Power.On);

        TaskResourceV2 task = this.client.updatePowerState(powerDeliveryDevice.getResourceId(), outletState, false);

        System.out.println("PowerDeliveryDeviceClientSample : updatePowerDeliveryDevicePowerState : " +
                "Task object returned to client : " + task);
    }

    private void updatePowerDeliveryDeviceRefreshState() {
        PowerDeliveryDevice powerDeliveryDevice = this.client.getByName(RESOURCE_NAME).get(0);
        PowerDeliveryDeviceRefreshRequest refreshState = new PowerDeliveryDeviceRefreshRequest();

        refreshState.setRefreshState(RefreshState.RefreshPending);

        TaskResourceV2 task = this.client.updateRefreshState(powerDeliveryDevice.getResourceId(), refreshState, false);

        System.out.println("PowerDeliveryDeviceClientSample : updatePowerDeliveryDeviceRefreshState : " +
                "Task object returned to client : " + task);
    }

    private void getPowerDeliveryDeviceUidState() {
        PowerDeliveryDevice powerDeliveryDevice = this.client.getByName(RESOURCE_NAME).get(0);

        Light light = client.getUidState(powerDeliveryDevice.getResourceId());

        System.out.println("PowerDeliveryDeviceClientSample : getPowerDeliveryDeviceUidState : " +
                "Light object returned to client : " + light);
    }

    private void updatePowerDeliveryDeviceUidState() {
        PowerDeliveryDevice powerDeliveryDevice = this.client.getByName(RESOURCE_NAME).get(0);
        OutletState outletStateState = new OutletState();

        outletStateState.setUidState(Light.On);

        TaskResourceV2 task = this.client.updateUidState(powerDeliveryDevice.getResourceId(), outletStateState, false);

        System.out.println("PowerDeliveryDeviceClientSample : updatePowerDeliveryDeviceUidState : " +
                "Task object returned to client : " + task);
    }

    private void getPowerDeliveryDeviceUtilization() {
        PowerDeliveryDevice powerDeliveryDevice = this.client.getByName(RESOURCE_NAME).get(0);

        UtilizationData utilization = client.getUtilization(powerDeliveryDevice.getResourceId());

        System.out.println("PowerDeliveryDeviceClientSample : getPowerDeliveryDeviceUtilization : " +
                "UtilizationData object returned to client : " + JsonPrettyPrinter.print(utilization));
    }

    public static void main(String[] args) {
        PowerDeliveryDeviceClientSample client = new PowerDeliveryDeviceClientSample();

        client.getPowerDeliveryDeviceById();

        client.addPowerDeliveryDevice();
        client.addPowerDeliveryDeviceByDiscover();

        client.getAllPowerDeliveryDevices();
        client.getPowerDeliveryDeviceByName();

        client.getPowerDeliveryDevicePowerState();
        client.updatePowerDeliveryDevicePowerState();

        client.updatePowerDeliveryDeviceRefreshState();

        client.getPowerDeliveryDeviceUidState();
        client.updatePowerDeliveryDeviceUidState();

        client.getPowerDeliveryDeviceUtilization();

        client.updatePowerDeliveryDevice();

        client.removePowerDeliveryDevice();
        client.removePowerDeliveryDeviceByFilter();
        client.removePowerDeliveryDeviceSynchronously();
    }

}