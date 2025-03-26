/*
 * Copyright 2015-2016 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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
package org.jboss.hal.testsuite.test;

import java.io.IOException;
import java.util.List;

import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.hal.resources.Ids;
import org.jboss.hal.testsuite.Console;
import org.jboss.hal.testsuite.creaper.ManagementClientProvider;
import org.jboss.hal.testsuite.fragment.HeaderFragment;
import org.jboss.hal.testsuite.page.HomePage;
import org.jboss.hal.testsuite.util.ServerEnvironmentUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.wildfly.extras.creaper.core.online.OnlineManagementClient;

import static org.jboss.hal.testsuite.Selectors.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class HomePageTest {

    private static final OnlineManagementClient client = ManagementClientProvider.createOnlineManagementClient();
    private static final ServerEnvironmentUtils serverEnvironmentUtils = new ServerEnvironmentUtils(client);

    @Page private HomePage page;
    @Inject private Console console;

    @Before
    public void setUp() {
        page.navigate();
    }

    @AfterClass
    public static void cleanUp() throws IOException {
        client.close();
    }

    @Test
    public void topLevelCategories() {
        HeaderFragment header = console.header();
        List<WebElement> topLevelCategories = header.getTopLevelCategories();
        assertEquals(serverEnvironmentUtils.isEAP() ? 6 : 5, topLevelCategories.size());
        assertEquals(Ids.TLC_HOMEPAGE, header.getSelectedTopLevelCategory().getAttribute("id"));

        assertTrue(containsTopLevelCategory(topLevelCategories, Ids.TLC_HOMEPAGE));
        assertTrue(containsTopLevelCategory(topLevelCategories, Ids.TLC_DEPLOYMENTS));
        assertTrue(containsTopLevelCategory(topLevelCategories, Ids.TLC_CONFIGURATION));
        assertTrue(containsTopLevelCategory(topLevelCategories, Ids.TLC_RUNTIME));
        assertTrue(containsTopLevelCategory(topLevelCategories, Ids.TLC_ACCESS_CONTROL));
        if (serverEnvironmentUtils.isEAP()) {
            assertTrue(containsTopLevelCategory(topLevelCategories, Ids.TLC_UPDATE_MANAGER));
        }
    }

    @Test
    public void modules() {
        assertTrue(containsModule("Deployments"));
        assertTrue(containsModule("Configuration"));
        assertTrue(containsModule("Runtime"));
        assertTrue(containsModule("Access Control"));
        if (serverEnvironmentUtils.isEAP()) {
            assertTrue(containsModule("Update Manager"));
        }

        By selector = ByJQuery.selector(".eap-home-module-header > h2" + contains("Need Help?"));
        assertTrue(page.getRootContainer().findElement(selector).isDisplayed());
    }

    private boolean containsTopLevelCategory(List<WebElement> topLevelCategories, String id) {
        return topLevelCategories.stream().anyMatch(tlc -> id.equals(tlc.getAttribute("id")));
    }

    private boolean containsModule(String name) {
        return page.getModules().stream().anyMatch(e -> e.getText().equals(name));
    }
}
