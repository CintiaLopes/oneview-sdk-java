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

package com.hpe.i3s.rest.client.deployment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.ov.sdk.OneViewClientSample;
import com.hpe.i3s.rest.client.ImageStreamerClient;

public class ImageStreamerClientSample extends OneViewClientSample {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageStreamerClientSample.class);

    private ImageStreamerClient client;

    public ImageStreamerClient getImageStreamerClient() {
        if (client == null) {
            try {
                client = new ImageStreamerClient(super.getSDKConfiguration(), super.getOneViewClient());
            } catch (Exception e) {
                LOGGER.error("An error occurred while creating a client instance", e);
            }
        }
        return client;
    }

    /* SDKConfiguration can also be build programmatically */
    /*public SDKConfiguration getSDKConfiguration() {
        if (config == null) {
            try {
                config = SDKConfiguration.create()
                    .withOneViewHostname("10.10.10.10")
                    .withImageStreamerHostname("10.10.10.10")
                    .withOneViewUser("administrator", "admin")
                    .withOneViewDomain("local")
                    .withOneViewApiVersion(ApiVersion.V_300)
                    .build();
            } catch (SDKPropertiesFileException e) {
                LOGGER.error("Some required properties may not have been configured!", e);
            }
        }
        return this.config;
    }*/

}
