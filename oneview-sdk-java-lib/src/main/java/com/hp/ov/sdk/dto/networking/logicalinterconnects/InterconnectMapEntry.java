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
package com.hp.ov.sdk.dto.networking.logicalinterconnects;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hp.ov.sdk.dto.Location;

public class InterconnectMapEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    private String interconnectUri;
    private Location location;
    private String logicalDownlinkUri;
    private String permittedInterconnectTypeUri;

    /**
     * @return the interconnectUri
     */
    public String getInterconnectUri() {
        return interconnectUri;
    }

    /**
     * @param interconnectUri
     *            the interconnectUri to set
     */
    public void setInterconnectUri(final String interconnectUri) {
        this.interconnectUri = interconnectUri;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(final Location location) {
        this.location = location;
    }

    /**
     * @return the logicalDownlinkUri
     */
    public String getLogicalDownlinkUri() {
        return logicalDownlinkUri;
    }

    /**
     * @param logicalDownlinkUri
     *            the logicalDownlinkUri to set
     */
    public void setLogicalDownlinkUri(final String logicalDownlinkUri) {
        this.logicalDownlinkUri = logicalDownlinkUri;
    }

    /**
     * @return the permittedInterconnectTypeUri
     */
    public String getPermittedInterconnectTypeUri() {
        return permittedInterconnectTypeUri;
    }

    /**
     * @param permittedInterconnectTypeUri
     *            the permittedInterconnectTypeUri to set
     */
    public void setPermittedInterconnectTypeUri(final String permittedInterconnectTypeUri) {
        this.permittedInterconnectTypeUri = permittedInterconnectTypeUri;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj instanceof InterconnectMapEntry) {
            InterconnectMapEntry that = (InterconnectMapEntry) obj;

            return new EqualsBuilder()
                    .append(interconnectUri, that.interconnectUri)
                    .append(location, that.location)
                    .append(logicalDownlinkUri, that.logicalDownlinkUri)
                    .append(permittedInterconnectTypeUri, that.permittedInterconnectTypeUri)
                    .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(interconnectUri)
                .append(location)
                .append(logicalDownlinkUri)
                .append(permittedInterconnectTypeUri)
                .toHashCode();
    }
}
