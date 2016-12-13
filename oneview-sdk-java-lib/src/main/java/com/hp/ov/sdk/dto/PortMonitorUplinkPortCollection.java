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
package com.hp.ov.sdk.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PortMonitorUplinkPortCollection extends BaseCollectionResource<PortMonitorUplinkPort> {

    private static final long serialVersionUID = 1L;

    private final List<PortMonitorUplinkPort> members = new ArrayList<PortMonitorUplinkPort>();

    @Override
    public List<PortMonitorUplinkPort> getMembers() {
        return new ArrayList<PortMonitorUplinkPort>(this.members);
    }

    @Override
    public void setMembers(final List<PortMonitorUplinkPort> members) {
        this.members.clear();

        if (members != null ) {
            this.members.addAll(members);

            this.setCount(this.members.size());
        }
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}