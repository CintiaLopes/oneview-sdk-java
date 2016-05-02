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

import java.io.Serializable;

public class PowerSupply implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private Integer bayNumber;
    private DevicePresence devicePresence;
    private String model;
    private String partNumber;
    private String serialNumber;
    private String sparePartNumber;
    private HealthStatus status;

    /**
     * @return the bayNumber
     */
    public Integer getBayNumber() {
        return bayNumber;
    }

    /**
     * @param bayNumber
     *            the bayNumber to set
     */
    public void setBayNumber(final Integer bayNumber) {
        this.bayNumber = bayNumber;
    }

    /**
     * @return the devicePresence
     */
    public DevicePresence getDevicePresence() {
        return devicePresence;
    }

    /**
     * @param devicePresence
     *            the devicePresence to set
     */
    public void setDevicePresence(final DevicePresence devicePresence) {
        this.devicePresence = devicePresence;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model
     *            the model to set
     */
    public void setModel(final String model) {
        this.model = model;
    }

    /**
     * @return the partNumber
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * @param partNumber
     *            the partNumber to set
     */
    public void setPartNumber(final String partNumber) {
        this.partNumber = partNumber;
    }

    /**
     * @return the serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param serialNumber
     *            the serialNumber to set
     */
    public void setSerialNumber(final String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * @return the sparePartNumber
     */
    public String getSparePartNumber() {
        return sparePartNumber;
    }

    /**
     * @param sparePartNumber
     *            the sparePartNumber to set
     */
    public void setSparePartNumber(final String sparePartNumber) {
        this.sparePartNumber = sparePartNumber;
    }

    /**
     * @return the status
     */
    public HealthStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(final HealthStatus status) {
        this.status = status;
    }

}
