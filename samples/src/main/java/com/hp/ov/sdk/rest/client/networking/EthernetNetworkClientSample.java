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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.ov.sdk.OneViewClientSample;
import com.hp.ov.sdk.constants.ResourceCategory;
import com.hp.ov.sdk.dto.ResourceCollection;
import com.hp.ov.sdk.dto.TaskResourceV2;
import com.hp.ov.sdk.dto.networking.EthernetNetworkType;
import com.hp.ov.sdk.dto.networking.ethernet.Bandwidth;
import com.hp.ov.sdk.dto.networking.ethernet.BulkEthernetNetwork;
import com.hp.ov.sdk.dto.networking.ethernet.Network;
import com.hp.ov.sdk.dto.networking.ethernet.Purpose;
import com.hp.ov.sdk.rest.client.OneViewClient;

public class EthernetNetworkClientSample {

    private static final Logger LOGGER = LoggerFactory.getLogger(EthernetNetworkClientSample.class);

    // These are variables to be defined by the user
    // ================================
    private static final String ETHERNET_NETWORK_RESOURCE_ID = "6415d1d3-318b-404a-8737-3ec07c1982c7";
    private static final String ETHERNET_NETWORK_NAME = "Ethernet-Network_SAMPLE";
    private static final String ETHERNET_NETWORK_NAME_UPDATED = ETHERNET_NETWORK_NAME + "_Updated";
    // ================================

    private final EthernetNetworkClient client;

    private EthernetNetworkClientSample() {
        OneViewClient oneViewClient = OneViewClientSample.getOneViewClient();

        this.client = oneViewClient.ethernetNetwork();
    }

    private void createEthernetNetwork() {
        Network network = new Network();

        network.setName(ETHERNET_NETWORK_NAME);
        network.setType(ResourceCategory.RC_NETWORK); //120
        network.setType(ResourceCategory.RC_NETWORK_V200); //200
        network.setType(ResourceCategory.RC_NETWORK_V300); //300
        network.setVlanId(Integer.valueOf(400));
        network.setSmartLink(true);
        network.setPrivateNetwork(false);
        network.setConnectionTemplateUri(null);
        network.setConnectionTemplate(null);
        network.setPurpose(Purpose.General);
        network.setEthernetNetworkType(EthernetNetworkType.Tagged);

        TaskResourceV2 task = this.client.create(network, false);

        System.out.println("EthernetNetworkClientSample : createEthernetNetwork : " +
                "Task object returned to client : " + task);
    }

    private void getEthernetNetwork() {
        Network network = client.getById(ETHERNET_NETWORK_RESOURCE_ID);

        LOGGER.info("Ethernet network returned to client: {}", network.toJsonString());
    }

    private void getAllEthernetNetworks() {
        ResourceCollection<Network> networks = client.getAll();

        LOGGER.info("Ethernet networks returned to client: {}", networks.toJsonString());
    }

    private void getEthernetNetworkByName() {
        Network network = client.getByName(ETHERNET_NETWORK_NAME).get(0);

        LOGGER.info("Ethernet network returned to client: {}", network.toJsonString());
    }

    private void updateEthernetNetwork() {
        Network network = client.getByName(ETHERNET_NETWORK_NAME).get(0);

        network.setName(ETHERNET_NETWORK_NAME_UPDATED);

        TaskResourceV2 task = this.client.update(network.getResourceId(),
                network, false);

        LOGGER.info("Task object returned to client: {}", task.toJsonString());
    }

    private void deleteEthernetNetwork() {
        Network network = client.getByName(ETHERNET_NETWORK_NAME_UPDATED).get(0);

        TaskResourceV2 task = this.client.delete(network.getResourceId(), false);

        LOGGER.info("Task object returned to client: {}", task.toJsonString());
    }

    private void getAssociatedProfiles() {
        Network network = client.getByName(ETHERNET_NETWORK_NAME).get(0);

        List<String> uris = client.getAssociatedProfiles(network.getResourceId());

        LOGGER.info("Ethernet network returned to client: {}", network.toJsonString());

        System.out.println("EthernetNetworkClientSample : getAssociatedProfiles : " +
                "Associated Profile URIs returned to client : " + uris);
    }

    private void getAssociatedUplinkGroups() {
        Network network = client.getByName(ETHERNET_NETWORK_NAME).get(0);

        List<String> uris = client.getAssociatedProfiles(network.getResourceId());

        System.out.println("EthernetNetworkClientSample : getAssociatedUplinkGroups : " +
                "Associated Uplink Group URIs returned to client : " + uris);
    }

    private void createEthernetNetworkInBulk() {
        BulkEthernetNetwork network = new BulkEthernetNetwork();

        network.setVlanIdRange("401-405");
        network.setPurpose(Purpose.General);
        network.setNamePrefix("Prod");
        network.setSmartLink(false);
        network.setPrivateNetwork(false);
        network.setType(ResourceCategory.RC_BULK_NETWORK);

        Bandwidth bandwidth = new Bandwidth();
        bandwidth.setMaximumBandwidth(Double.valueOf(8000));
        bandwidth.setTypicalBandwidth(Double.valueOf(3000));

        network.setBandwidth(bandwidth);

        TaskResourceV2 task = this.client.createInBulk(network, false);

        LOGGER.info("Task object returned to client: {}", task.toJsonString());
    }

    public static void main(String[] args) {
        EthernetNetworkClientSample sample = new EthernetNetworkClientSample();

        sample.createEthernetNetwork();
        sample.getEthernetNetwork();
        sample.getEthernetNetworkByName();
        sample.getAllEthernetNetworks();

        sample.getAssociatedProfiles();
        sample.getAssociatedUplinkGroups();

        sample.updateEthernetNetwork();
        sample.deleteEthernetNetwork();

        sample.createEthernetNetworkInBulk();
    }
}