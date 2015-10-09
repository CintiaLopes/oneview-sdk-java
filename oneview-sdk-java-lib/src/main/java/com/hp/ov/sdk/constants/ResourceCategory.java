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
package com.hp.ov.sdk.constants;

import org.springframework.stereotype.Component;

@Component
public class ResourceCategory {

    public static final String RC_NETWORK = "ethernet-networkV2";
    public static final String RC_BULK_NETWORK = "bulk-ethernet-network";
    public static final String RC_FCNETWORK = "fc-networkV2";
    public static final String RC_SERVERHARDWARE = "server-hardware";
    public static final String RC_NETWORKSET = "network-set";
    public static final String RC_LOGICALINTERCONNECTGROUP = "logical-interconnect-groupV2";
    public static final String RC_UPLINKSET = "uplink-sets";
    public static final String RC_ENCLOSURE_GROUP = "EnclosureGroupV2";
    public static final String RC_STORAGE_VOLUME_TEMPLATE = "StorageVolumeTemplate";
    public static final String RC_ADD_STORAGE_VOLUME = "AddStorageVolumeV2";
    public static final String RC_SERVER_PROFILE = "ServerProfileV4";

}