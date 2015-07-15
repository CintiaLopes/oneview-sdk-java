/*******************************************************************************
 * // (C) Copyright 2014-2015 Hewlett-Packard Development Company, L.P.
 *******************************************************************************/
package com.hp.ov.sdk.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MsmbMessage
{

    //TODO - this is sample properties. This can be extended as per user needs.
    private String resourceUri;
    private List<ResourceDataList> resourceDataList = new ArrayList<ResourceDataList>();

    public String getResourceUri()
    {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri)
    {
        this.resourceUri = resourceUri;
    }

    public List<ResourceDataList> getResourceDataList()
    {
        return resourceDataList;
    }

    public void setResourceDataList(List<ResourceDataList> resourceDataList)
    {
        this.resourceDataList = resourceDataList;
    }

}