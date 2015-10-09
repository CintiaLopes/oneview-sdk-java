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
package com.hp.ov.sdk.adaptors;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.hp.ov.sdk.bean.factory.ConverterFactory;
import com.hp.ov.sdk.dto.AddEnclosureV2;
import com.hp.ov.sdk.dto.EnclosureCollectionV2;
import com.hp.ov.sdk.dto.EnvironmentalConfigurationUpdate;
import com.hp.ov.sdk.dto.FwBaselineConfig;
import com.hp.ov.sdk.dto.RefreshStateConfig;
import com.hp.ov.sdk.dto.SsoUrlData;
import com.hp.ov.sdk.dto.UtilizationData;
import com.hp.ov.sdk.dto.generated.Enclosures;
import com.hp.ov.sdk.dto.generated.EnvironmentalConfiguration;
import com.hp.ov.sdk.util.ObjectToJsonConverter;
import com.hp.ov.sdk.util.StringUtil;

@Component
public class EnclosureAdaptor extends BaseAdaptor<Enclosures, Object> {

    private ObjectToJsonConverter converter;

    @Override
    public Enclosures buildDto(final Object source) {
        // TODO - exceptions
        converter = ConverterFactory.getConverter();
        // convert json Object to DTO, replace quotes and back slash in the file
        final Enclosures enclosureDto = converter.convertJsonToObject(
                StringUtil.replaceQuotesAndBackSlash(converter.convertObjectToJsonString(source)), Enclosures.class);
        return enclosureDto;
    }

    public EnvironmentalConfiguration buildEnvironmentalConfigurationDto(final Object source) {
        // TODO - exceptions
        // convert json Object to DTO, replace quotes and back slash in the file
        converter = ConverterFactory.getConverter();
        final EnvironmentalConfiguration environmentalConfigurationDto = converter
                .convertJsonToObject(StringUtil.replaceQuotesAndBackSlash(converter.convertObjectToJsonString(source)),
                        EnvironmentalConfiguration.class);
        return environmentalConfigurationDto;
    }

    public SsoUrlData buildSsoUrlData(final Object source) {
        // TODO - exceptions
        // convert json Object to DTO, replace quotes and back slash in the file
        converter = ConverterFactory.getConverter();
        final SsoUrlData ssoUrlData = converter.convertJsonToObject(
                StringUtil.replaceQuotesAndBackSlash(converter.convertObjectToJsonString(source)), SsoUrlData.class);
        return ssoUrlData;
    }

    public UtilizationData buildUtilizationData(final Object source) {
        // TODO - exceptions
        // convert json Object to DTO, replace quotes and back slash in the file
        converter = ConverterFactory.getConverter();
        final UtilizationData utilizationData = converter.convertJsonToObject(
                StringUtil.replaceQuotesAndBackSlash(converter.convertObjectToJsonString(source)), UtilizationData.class);
        return utilizationData;
    }

    public EnclosureCollectionV2 buildCollectionDto(final Object source) {
        // TODO - exceptions
        converter = ConverterFactory.getConverter();
        if (null == source || source.equals("")) {
            return null;
        }
        // convert json Object to DTO, replace quotes and back slash in the file
        final EnclosureCollectionV2 enclosureCollectionDto = converter.convertJsonToObject(
                StringUtil.replaceQuotesBackSlashWithQuote(StringUtil.replaceQuotesAndBackSlash(converter
                        .convertObjectToJsonString(source))), EnclosureCollectionV2.class);
        return enclosureCollectionDto;
    }

    public JSONObject buildJsonObjectFromDto(final Enclosures source) {
        converter = ConverterFactory.getConverter();
        return new JSONObject(converter.convertObjectToJsonString(source));
    }

    public JSONObject buildJsonObjectFromDto(final FwBaselineConfig source) {
        converter = ConverterFactory.getConverter();
        return new JSONObject(converter.convertObjectToJsonString(source));
    }

    public JSONObject buildJsonObjectFromDto(final RefreshStateConfig source) {
        converter = ConverterFactory.getConverter();
        return new JSONObject(converter.convertObjectToJsonString(source));
    }

    public JSONObject buildJsonObjectFromDto(final EnvironmentalConfigurationUpdate source) {
        converter = ConverterFactory.getConverter();
        return new JSONObject(converter.convertObjectToJsonString(source));
    }

    public JSONObject buildJsonObjectFromDto(final AddEnclosureV2 source) {
        converter = ConverterFactory.getConverter();
        return new JSONObject(converter.convertObjectToJsonString(source));
    }
}