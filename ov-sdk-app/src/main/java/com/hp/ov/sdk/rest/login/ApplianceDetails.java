/*******************************************************************************
 * // (C) Copyright 2014-2015 Hewlett-Packard Development Company, L.P.
 *******************************************************************************/
package com.hp.ov.sdk.rest.login;

import com.hp.ov.sdk.dto.ApplianceDetailsDto;
import com.hp.ov.sdk.rest.http.core.client.RestParams;

public interface ApplianceDetails
{

    public ApplianceDetailsDto getVersion(RestParams params);
}