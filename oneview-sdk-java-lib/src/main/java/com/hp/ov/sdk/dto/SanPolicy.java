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
package com.hp.ov.sdk.dto;

import java.io.Serializable;

public class SanPolicy implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private Boolean enableAliasing;
    private String initiatorNameFormat;
    private String targetGroupNameFormat;
    private String targetNameFormat;
    private String zoneNameFormat;
    private ZoningPolicy zoningPolicy;

    /**
     * 
     * @return The enableAliasing
     */
    public Boolean getEnableAliasing() {
        return enableAliasing;
    }

    /**
     * 
     * @param enableAliasing
     *            The enableAliasing
     */
    public void setEnableAliasing(final Boolean enableAliasing) {
        this.enableAliasing = enableAliasing;
    }

    /**
     * 
     * @return The initiatorNameFormat
     */
    public String getInitiatorNameFormat() {
        return initiatorNameFormat;
    }

    /**
     * 
     * @param initiatorNameFormat
     *            The initiatorNameFormat
     */
    public void setInitiatorNameFormat(final String initiatorNameFormat) {
        this.initiatorNameFormat = initiatorNameFormat;
    }

    /**
     * 
     * @return The targetGroupNameFormat
     */
    public String getTargetGroupNameFormat() {
        return targetGroupNameFormat;
    }

    /**
     * 
     * @param targetGroupNameFormat
     *            The targetGroupNameFormat
     */
    public void setTargetGroupNameFormat(final String targetGroupNameFormat) {
        this.targetGroupNameFormat = targetGroupNameFormat;
    }

    /**
     * 
     * @return The targetNameFormat
     */
    public String getTargetNameFormat() {
        return targetNameFormat;
    }

    /**
     * 
     * @param targetNameFormat
     *            The targetNameFormat
     */
    public void setTargetNameFormat(final String targetNameFormat) {
        this.targetNameFormat = targetNameFormat;
    }

    /**
     * 
     * @return The zoneNameFormat
     */
    public String getZoneNameFormat() {
        return zoneNameFormat;
    }

    /**
     * 
     * @param zoneNameFormat
     *            The zoneNameFormat
     */
    public void setZoneNameFormat(final String zoneNameFormat) {
        this.zoneNameFormat = zoneNameFormat;
    }

    /**
     * 
     * @return The zoningPolicy
     */
    public ZoningPolicy getZoningPolicy() {
        return zoningPolicy;
    }

    /**
     * 
     * @param zoningPolicy
     *            The zoningPolicy
     */
    public void setZoningPolicy(final ZoningPolicy zoningPolicy) {
        this.zoningPolicy = zoningPolicy;
    }

}