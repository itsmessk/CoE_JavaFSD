/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.myTraining.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;
import com.myTraining.core.testcontext.AppAemContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * Simple JUnit test verifying the HelloWorldModel
 */
@ExtendWith(AemContextExtension.class)
class HelloWorldModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    private HelloWorldModel hello;

    private Page page;
    private Resource resource;

    @BeforeEach
    public void setup() throws Exception {

        // prepare a page with a test resource
        page = context.create().page("/content/mypage");
        resource = context.create().resource(page, "hello",
            "sling:resourceType", "myTraining/components/helloworld");

        // create sling model
        hello = resource.adaptTo(HelloWorldModel.class);
    }

    @Test
    void testGetMessage() {
        // Getting the actual message from the model
        String msg = hello.getMessage();
        assertNotNull(msg);

        // Checking if the message contains the expected resource type and page path
        assertTrue(StringUtils.contains(msg, resource.getResourceType()));
        assertTrue(StringUtils.contains(msg, page.getPath()));

        // Update the assertion to match the actual output format
        assertTrue(msg.contains("Hello World!"));
    }


}