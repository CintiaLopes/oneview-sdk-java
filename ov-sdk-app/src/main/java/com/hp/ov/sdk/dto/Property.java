/*******************************************************************************
 * // (C) Copyright 2014-2015 Hewlett-Packard Development Company, L.P.
 *******************************************************************************/
package com.hp.ov.sdk.dto;

import java.io.Serializable;

import com.hp.ov.sdk.dto.generated.ConnectionProperty.ValueType;

public class Property implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private String displayName;
    private Boolean required;
    private String value;
    private ValueType valueFormat;
    private DataFormat valueType;
    private String name;

    /**
     * 
     * @return
     *         The displayName
     */
    public String getDisplayName()
    {
        return displayName;
    }

    /**
     * 
     * @param displayName
     *        The displayName
     */
    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    /**
     * 
     * @return
     *         The required
     */
    public Boolean getRequired()
    {
        return required;
    }

    /**
     * 
     * @param required
     *        The required
     */
    public void setRequired(Boolean required)
    {
        this.required = required;
    }

    /**
     * 
     * @return
     *         The value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * 
     * @param value
     *        The value
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * 
     * @return
     *         The valueFormat
     */
    public ValueType getValueFormat()
    {
        return valueFormat;
    }

    /**
     * 
     * @param valueFormat
     *        The valueFormat
     */
    public void setValueFormat(ValueType valueFormat)
    {
        this.valueFormat = valueFormat;
    }

    /**
     * 
     * @return
     *         The valueType
     */
    public DataFormat getValueType()
    {
        return valueType;
    }

    /**
     * 
     * @param valueType
     *        The valueType
     */
    public void setValueType(DataFormat valueType)
    {
        this.valueType = valueType;
    }

    /**
     * 
     * @return
     *         The name
     */
    public String getName()
    {
        return name;
    }

    /**
     * 
     * @param name
     *        The name
     */
    public void setName(String name)
    {
        this.name = name;
    }

}