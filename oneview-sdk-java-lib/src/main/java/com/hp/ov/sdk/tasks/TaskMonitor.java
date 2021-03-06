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

package com.hp.ov.sdk.tasks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.ov.sdk.constants.SdkConstants;
import com.hp.ov.sdk.dto.ErrorMessage;
import com.hp.ov.sdk.dto.TaskResource;
import com.hp.ov.sdk.dto.TaskState;
import com.hp.ov.sdk.exceptions.SDKErrorEnum;
import com.hp.ov.sdk.exceptions.SDKTasksException;
import com.hp.ov.sdk.rest.client.BaseClient;
import com.hp.ov.sdk.rest.http.core.HttpMethod;
import com.hp.ov.sdk.rest.http.core.client.Request;

public class TaskMonitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskMonitor.class);

    //TODO externalize this value to make it configurable
    private static final int TASK_MONITORING_INTERVAL = 5000; //milliseconds

    public TaskResource execute(BaseClient client, TaskResource task, int taskTimeoutMillis) {
        TaskResource taskResult;

        if (taskTimeoutMillis > SdkConstants.NO_TASK_TIMEOUT) {
            taskResult = this.checkTaskTimeout(client, task, taskTimeoutMillis);
        } else {
            taskResult = this.checkTaskPercentage(client, task);
        }

        if (task.getTaskState() == (TaskState.Error)) {
            List<String> errorMessages = new ArrayList<>();
            List<String> errorRecommendations = new ArrayList<>();

            for (ErrorMessage errorMessage : task.getTaskErrors()) {
                errorMessages.add(errorMessage.getMessage());
                errorRecommendations.addAll(errorMessage.getRecommendedActions());
            }
            throw new SDKTasksException(SDKErrorEnum.tasksError, errorMessages.toArray(),
                    errorRecommendations.toArray(), "Error occurred while monitoring task");
        }
        return taskResult;
    }

    private TaskResource checkTaskTimeout(BaseClient client, TaskResource task, int taskTimeoutMillis) {
        Calendar dateToLive = Calendar.getInstance();

        dateToLive.add(Calendar.MILLISECOND, taskTimeoutMillis);

        while (dateToLive.after(Calendar.getInstance())) {
            Request request = new Request(HttpMethod.GET, task.getUri());

            task = client.executeRequest(request, TaskResource.class);

            LOGGER.info("Task completed percentage {} and status {}", task.getPercentComplete(), task.getTaskState());

            if (task.getPercentComplete() == SdkConstants.PERCENTAGE_100) {
                return task;
            }
            this.waitTaskMonitorInterval(task);
        }
        LOGGER.warn("Task timeout exceeded: " + dateToLive.getTime() + " < " + Calendar.getInstance().getTime());

        throw new SDKTasksException(SDKErrorEnum.tasksError,
                new String[] {"Task monitoring exceeded the timeout limit of " + taskTimeoutMillis + " milliseconds."},
                new String[] {"Increase the task timeout."}, "Error occurred while monitoring task");
    }

    private TaskResource checkTaskPercentage(BaseClient client, TaskResource task) {
        while (task.getPercentComplete() < SdkConstants.PERCENTAGE_100) {
            Request request = new Request(HttpMethod.GET, task.getUri());

            task = client.executeRequest(request, TaskResource.class);

            LOGGER.info("Task completed percentage {} and status {}", task.getPercentComplete(), task.getTaskState());

            if (task.getPercentComplete() == SdkConstants.PERCENTAGE_100) break;

            this.waitTaskMonitorInterval(task);
        }
        return task;
    }

    private void waitTaskMonitorInterval(TaskResource task) {
        try {
            Thread.sleep(TASK_MONITORING_INTERVAL);
        } catch (final InterruptedException e) {
            LOGGER.warn("An interruption occurred while monitoring the task {}", task.getResourceId(), e);
        }
    }

}
