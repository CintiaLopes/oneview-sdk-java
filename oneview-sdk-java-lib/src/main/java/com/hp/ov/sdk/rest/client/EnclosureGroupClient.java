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
package com.hp.ov.sdk.rest.client;

import com.hp.ov.sdk.dto.EnclosureGroupCollectionV2;
import com.hp.ov.sdk.dto.generated.EnclosureGroups;
import com.hp.ov.sdk.rest.http.core.client.RestParams;

public interface EnclosureGroupClient {

    /**
     * The module aids in fetching the enclosure group details for the specified
     * enclosure group resourceId
     * 
     * @param params
     *            The RestParams is a structure containing the connection
     *            details.
     * @param resourceId
     *            The resourceId for enclosure group as seen in HP OneView.
     * @return enclosureGroupsDto, which is a object containing the enclosure
     *         group details.
     */
    public EnclosureGroups getEnclosureGroup(final RestParams params, final String resourceId);

    /**
     * The module aids in fetching the enclosure group details for all enclosure
     * group registered under current HP OneView
     * 
     * @param params
     *            The RestParams is a structure containing the connection
     *            details.
     * @return enclosureGroupsCollectionDto, which is a object containing a
     *         collection of enclosure group details.
     */
    public EnclosureGroupCollectionV2 getAllEnclosureGroups(final RestParams params);

    /**
     * The module aids in fetching the enclosure group details for the specified
     * enclosure group name
     * 
     * @param params
     *            The RestParams is a structure containing the connection
     *            details.
     * @param name
     *            The resourceName is the enclosure group name as seen in HP
     *            OneView.
     * @return enclosureGroupsDto, which is a object containing the enclosure
     *         group details.
     */
    public EnclosureGroups getEnclosureGroupByName(final RestParams params, final String name);

    /**
     * The module aids in creation of enclosure group when provided with the
     * enclosure group details as enclosure group object or JsonRequest.
     * 
     * @param params
     *            The RestParams is a structure containing the connection
     *            details.
     * @param enclosureGroupsDto
     *            This is a object containing the EnclosureGroup details, used
     *            to create a Enclosure group.
     * @param useJsonRequest
     *            The JsonRequest body is part of EnclosureGroup Object which
     *            takes in a String containing the new EnclosureGroup details,
     *            which is converted to EnclosureGroup Object using adaptor and
     *            processed.
     * @return enclosureGroupsDto, which is a object containing the created
     *         enclosure group details.
     */
    public EnclosureGroups createEnclosureGroup(final RestParams params, final EnclosureGroups enclosureGroupsDto,
            final boolean useJsonRequest);

    /**
     * The module takes in an Enclosure group object or JsonRequest and updates
     * the existing enclosure group based on resource Id.
     * 
     * @param params
     *            The RestParams is a structure containing the connection
     *            details.
     * @param resourceId
     *            The resourceId for enclosure group as seen in HP OneView.
     * @param enclosureGroup
     *            This is a object containing the update to be made to existing
     *            EnclosureGroup pointed to by the above mentioned resourceId
     * @param useJsonRequest
     *            The JsonRequest body is part of EnclosureGroup Object which
     *            takes in a String containing the update to be made, which is
     *            converted to EnclosureGroup Object using adaptor and
     *            processed.
     * @return enclosureGroupsDto, which is a object containing the updated
     *         enclosure group details.
     */
    public EnclosureGroups updateEnclosureGroup(final RestParams params, final String resourceId,
            final EnclosureGroups enclosureGroup, final boolean useJsonRequest);

    /**
     * The module aids in deleting a enclosure group for the specified enclosure
     * group resourceId.
     * 
     * @param params
     *            The RestParams is a structure containing the connection
     *            details.
     * @param resourceId
     *            The resourceId for enclosure group as seen in HP OneView.
     * @return a empty String on successful deletion and error otherwise.
     */
    public String deleteEnclosureGroup(final RestParams params, final String resourceId);

    /**
     * The module aids in fetching the configuration script for the specified
     * enclosure group resourceId.
     * 
     * @param params
     *            The RestParams is a structure containing the connection
     *            details.
     * @param resourceId
     *            The resourceId for enclosure group as seen in HP OneView.
     * @return String, the configuration script for the specified enclosure
     *         group
     */
    public String getConfigurationScript(final RestParams params, final String resourceId);

    /**
     * The module aids in updating the configuration script for the specified
     * enclosure group resourceId.
     * 
     * @param params
     *            The RestParams is a structure containing the connection
     *            details.
     * @param resourceId
     *            The resourceId for enclosure group as seen in HP OneView.
     * @param scriptData
     *            THe script data to be updated for enclosure group
     * @return String, the configuration script for the specified enclosure
     *         group
     */
    public String updateConfigurationScript(final RestParams params, final String resourceId, final String scriptData);

    /**
     * The module aids in fetching the EnclosureGroup details for the
     * EnclosureGroup name as specified in HP OneView.
     * 
     * @param creds
     *            The RestParams is a structure containing the connection
     *            details.
     * @param name
     *            The resourceName is the EnclosureGroup name as seen in HP
     *            OneView.
     * @return String, which is a resource Id for the EnclosureGroup name as
     *         seen in HPOneView.
     */
    public String getId(final RestParams creds, final String name);
}