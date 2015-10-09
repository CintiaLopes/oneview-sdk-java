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
import com.hp.ov.sdk.dto.ConnectionTemplateCollection;
import com.hp.ov.sdk.dto.generated.ConnectionTemplate;
import com.hp.ov.sdk.util.ObjectToJsonConverter;
import com.hp.ov.sdk.util.StringUtil;

@Component
public class ConnectionTemplateAdaptor extends BaseAdaptor<ConnectionTemplate, Object> {

    private ObjectToJsonConverter converter;

    @Override
    public ConnectionTemplate buildDto(final Object source) {
        converter = ConverterFactory.getConverter();
        // TODO - exceptions
        // convert json Object to DTO, replace quotes and back slash in the file
        final ConnectionTemplate connectionTemplateDto = converter.convertJsonToObject(
                StringUtil.replaceQuotesAndBackSlash(converter.convertObjectToJsonString(source)), ConnectionTemplate.class);
        return connectionTemplateDto;
    }

    public ConnectionTemplateCollection buildCollectionDto(final Object source) {
        converter = ConverterFactory.getConverter();
        // TODO - exceptions
        if (null == source || source.equals("")) {
            return null;
        }
        // convert json Object to DTO, replace quotes and back slash in the file
        final ConnectionTemplateCollection connectionTemplateCollectionDto = converter.convertJsonToObject(
                StringUtil.replaceQuotesBackSlashWithQuote(StringUtil.replaceQuotesAndBackSlash(converter
                        .convertObjectToJsonString(source))), ConnectionTemplateCollection.class);
        return connectionTemplateCollectionDto;
    }

    public JSONObject buildJsonObjectFromDto(final ConnectionTemplate source) {
        converter = ConverterFactory.getConverter();
        return new JSONObject(converter.convertObjectToJsonString(source));
    }
}