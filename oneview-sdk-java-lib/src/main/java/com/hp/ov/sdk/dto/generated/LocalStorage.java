/*******************************************************************************
 * (C) Copyright 2015-2016 Hewlett Packard Enterprise Development LP
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

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import com.hp.ov.sdk.dto.LocalStorageEmbeddedController;

public class LocalStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Until(199)
    private List<LogicalDrive> logicalDrives;
    @Until(199)
    private Boolean manageLocalStorage;
    @Until(199)
    private Boolean initialize;
    @Since(200)
    private List<LocalStorageEmbeddedController> controllers;

    /**
     *
     * @return The logicalDrives
     */
    public List<LogicalDrive> getLogicalDrives() {
        return logicalDrives;
    }

    /**
     *
     * @param logicalDrives
     *            The logicalDrives
     */
    public void setLogicalDrives(final List<LogicalDrive> logicalDrives) {
        this.logicalDrives = logicalDrives;
    }

    /**
     *
     * @return The manageLocalStorage
     */
    public Boolean getManageLocalStorage() {
        return manageLocalStorage;
    }

    /**
     *
     * @param manageLocalStorage
     *            The manageLocalStorage
     */
    public void setManageLocalStorage(final Boolean manageLocalStorage) {
        this.manageLocalStorage = manageLocalStorage;
    }

    /**
     *
     * @return The initialize
     */
    public Boolean getInitialize() {
        return initialize;
    }

    /**
     *
     * @param initialize
     *            The initialize
     */
    public void setInitialize(final Boolean initialize) {
        this.initialize = initialize;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

}
