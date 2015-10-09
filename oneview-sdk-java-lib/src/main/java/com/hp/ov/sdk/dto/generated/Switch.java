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
package com.hp.ov.sdk.dto.generated;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Switch data transfer object (DTO) contains the information used to
 * represent a switch in the system. It is passed in to the add/update switch
 * REST api, as well as the add/update server hardware through java client api.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "serialNumber", "environmentalConfiguration", "switchManagementConnection", "modelName", "chassisId",
        "firmwareVersion", "roles", "description", "status", "name", "state", "eTag", "created", "modified", "category", "uri" })
public class Switch {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("serialNumber")
    private String serialNumber;
    @JsonProperty("environmentalConfiguration")
    private EnvironmentalConfiguration environmentalConfiguration;
    @JsonProperty("switchManagementConnection")
    private SwitchManagementConnection switchManagementConnection;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("modelName")
    private String modelName;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("chassisId")
    private String chassisId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("firmwareVersion")
    private String firmwareVersion;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("roles")
    private List<String> roles = new ArrayList<String>();
    @JsonProperty("description")
    private String description;
    @JsonProperty("status")
    private String status;
    @JsonProperty("name")
    private String name;
    @JsonProperty("state")
    private String state;
    @JsonProperty("eTag")
    private String eTag;
    @JsonProperty("created")
    private String created;
    @JsonProperty("modified")
    private String modified;
    @JsonProperty("category")
    private String category;
    @JsonProperty("uri")
    private String uri;

    /**
     * 
     * (Required)
     * 
     * @return The serialNumber
     */
    @JsonProperty("serialNumber")
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * 
     * (Required)
     * 
     * @param serialNumber
     *            The serialNumber
     */
    @JsonProperty("serialNumber")
    public void setSerialNumber(final String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * 
     * @return The environmentalConfiguration
     */
    @JsonProperty("environmentalConfiguration")
    public EnvironmentalConfiguration getEnvironmentalConfiguration() {
        return environmentalConfiguration;
    }

    /**
     * 
     * @param environmentalConfiguration
     *            The environmentalConfiguration
     */
    @JsonProperty("environmentalConfiguration")
    public void setEnvironmentalConfiguration(final EnvironmentalConfiguration environmentalConfiguration) {
        this.environmentalConfiguration = environmentalConfiguration;
    }

    /**
     * 
     * @return The switchManagementConnection
     */
    @JsonProperty("switchManagementConnection")
    public SwitchManagementConnection getSwitchManagementConnection() {
        return switchManagementConnection;
    }

    /**
     * 
     * @param switchManagementConnection
     *            The switchManagementConnection
     */
    @JsonProperty("switchManagementConnection")
    public void setSwitchManagementConnection(final SwitchManagementConnection switchManagementConnection) {
        this.switchManagementConnection = switchManagementConnection;
    }

    /**
     * 
     * (Required)
     * 
     * @return The modelName
     */
    @JsonProperty("modelName")
    public String getModelName() {
        return modelName;
    }

    /**
     * 
     * (Required)
     * 
     * @param modelName
     *            The modelName
     */
    @JsonProperty("modelName")
    public void setModelName(final String modelName) {
        this.modelName = modelName;
    }

    /**
     * 
     * (Required)
     * 
     * @return The chassisId
     */
    @JsonProperty("chassisId")
    public String getChassisId() {
        return chassisId;
    }

    /**
     * 
     * (Required)
     * 
     * @param chassisId
     *            The chassisId
     */
    @JsonProperty("chassisId")
    public void setChassisId(final String chassisId) {
        this.chassisId = chassisId;
    }

    /**
     * 
     * (Required)
     * 
     * @return The firmwareVersion
     */
    @JsonProperty("firmwareVersion")
    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    /**
     * 
     * (Required)
     * 
     * @param firmwareVersion
     *            The firmwareVersion
     */
    @JsonProperty("firmwareVersion")
    public void setFirmwareVersion(final String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    /**
     * 
     * (Required)
     * 
     * @return The roles
     */
    @JsonProperty("roles")
    public List<String> getRoles() {
        return roles;
    }

    /**
     * 
     * (Required)
     * 
     * @param roles
     *            The roles
     */
    @JsonProperty("roles")
    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }

    /**
     * 
     * @return The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *            The description
     */
    @JsonProperty("description")
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * 
     * @return The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *            The status
     */
    @JsonProperty("status")
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * 
     * @return The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *            The name
     */
    @JsonProperty("name")
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * 
     * @return The state
     */
    @JsonProperty("state")
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state
     *            The state
     */
    @JsonProperty("state")
    public void setState(final String state) {
        this.state = state;
    }

    /**
     * 
     * @return The eTag
     */
    @JsonProperty("eTag")
    public String getETag() {
        return eTag;
    }

    /**
     * 
     * @param eTag
     *            The eTag
     */
    @JsonProperty("eTag")
    public void setETag(final String eTag) {
        this.eTag = eTag;
    }

    /**
     * 
     * @return The created
     */
    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    /**
     * 
     * @param created
     *            The created
     */
    @JsonProperty("created")
    public void setCreated(final String created) {
        this.created = created;
    }

    /**
     * 
     * @return The modified
     */
    @JsonProperty("modified")
    public String getModified() {
        return modified;
    }

    /**
     * 
     * @param modified
     *            The modified
     */
    @JsonProperty("modified")
    public void setModified(final String modified) {
        this.modified = modified;
    }

    /**
     * 
     * @return The category
     */
    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *            The category
     */
    @JsonProperty("category")
    public void setCategory(final String category) {
        this.category = category;
    }

    /**
     * 
     * @return The uri
     */
    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    /**
     * 
     * @param uri
     *            The uri
     */
    @JsonProperty("uri")
    public void setUri(final String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(serialNumber).append(environmentalConfiguration).append(switchManagementConnection)
                .append(modelName).append(chassisId).append(firmwareVersion).append(roles).append(description).append(status)
                .append(name).append(state).append(eTag).append(created).append(modified).append(category).append(uri).toHashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Switch) == false) {
            return false;
        }
        final Switch rhs = ((Switch) other);
        return new EqualsBuilder().append(serialNumber, rhs.serialNumber)
                .append(environmentalConfiguration, rhs.environmentalConfiguration)
                .append(switchManagementConnection, rhs.switchManagementConnection).append(modelName, rhs.modelName)
                .append(chassisId, rhs.chassisId).append(firmwareVersion, rhs.firmwareVersion).append(roles, rhs.roles)
                .append(description, rhs.description).append(status, rhs.status).append(name, rhs.name).append(state, rhs.state)
                .append(eTag, rhs.eTag).append(created, rhs.created).append(modified, rhs.modified).append(category, rhs.category)
                .append(uri, rhs.uri).isEquals();
    }

}