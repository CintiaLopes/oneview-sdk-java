/*
 * (C) Copyright 2016 Hewlett Packard Enterprise Development LP
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
 */

package com.hp.ov.sdk.rest.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.google.common.reflect.AbstractInvocationHandler;
import com.google.common.reflect.TypeToken;
import com.hp.ov.sdk.dto.TaskResource;
import com.hp.ov.sdk.rest.client.BaseClient;
import com.hp.ov.sdk.rest.http.core.UrlParameter;
import com.hp.ov.sdk.rest.http.core.client.Request;
import com.hp.ov.sdk.rest.http.core.client.RequestOption;

public class ClientRequestHandler<T> extends AbstractInvocationHandler {

    private final BaseClient baseClient;
    private final String baseUri;
    private final TypeToken<T> token;

    public ClientRequestHandler(BaseClient baseClient, Class<T> clientClass) {
        this.baseClient = baseClient;
        this.baseUri = clientClass.getAnnotation(Api.class).value();
        this.token = TypeToken.of(clientClass);
    }

    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = this.buildRequest(method, args);

        if (TaskResource.class.equals(method.getReturnType())) {
            return this.baseClient.executeMonitorableRequest(request);
        }
        return this.baseClient.executeRequest(request, this.token.method(method).getReturnType().getType());
    }

    private Request buildRequest(Method method, Object[] args) {
        Endpoint endpoint = method.getDeclaredAnnotation(Endpoint.class);

        Request request = new Request(endpoint.method(), this.baseUri + endpoint.uri());

        this.fillRequestAccordingParams(request, method.getParameters(), args);
        this.fillRequestAccordingOptions(request, args);

        return request;
    }

    private void fillRequestAccordingParams(Request request, Parameter[] params, Object[] args) {
        for (int i = 0; i < params.length; i++) {
            PathParam pathParam = params[i].getAnnotation(PathParam.class);
            QueryParam queryParam = params[i].getAnnotation(QueryParam.class);
            BodyParam bodyParam = params[i].getAnnotation(BodyParam.class);

            if (pathParam != null) {
                request.setUri(request.getUri().replaceFirst("\\{" + pathParam.value() + "\\}", args[i].toString()));
            } else if (queryParam != null) {
                request.addQuery(new UrlParameter(queryParam.key(),
                        queryParam.value().replaceFirst("\\{.*?\\}", args[i].toString())));
            } else if (bodyParam != null) {
                request.setEntity(args[i]);
                request.setContentType(bodyParam.type());
            }
        }
    }

    private void fillRequestAccordingOptions(Request request, Object[] args) {
        boolean isLastArgumentAnArray = (args != null)
                && (args.length > 0)
                && (args[args.length - 1].getClass().isArray());

        if (isLastArgumentAnArray) {
            for (RequestOption option : (RequestOption[]) args[args.length - 1]) {
                option.apply(request);
            }
        }
    }

}