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
package org.jboss.hal.testsuite.test.configuration.ee;

import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.hal.testsuite.Console;
import org.jboss.hal.testsuite.Random;
import org.jboss.hal.testsuite.creaper.ManagementClientProvider;
import org.jboss.hal.testsuite.creaper.ResourceVerifier;
import org.jboss.hal.testsuite.creaper.command.BackupAndRestoreAttributes;
import org.jboss.hal.testsuite.fragment.FormFragment;
import org.jboss.hal.testsuite.page.configuration.EEPage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.extras.creaper.core.CommandFailedException;
import org.wildfly.extras.creaper.core.online.OnlineManagementClient;

import static org.jboss.hal.dmr.ModelDescriptionConstants.CONTEXT_SERVICE;

@RunWith(Arquillian.class)
public class DefaultBindingsTest {

    private static final OnlineManagementClient client = ManagementClientProvider.createOnlineManagementClient();
    private static BackupAndRestoreAttributes backup;

    @BeforeClass
    public static void beforeClass() throws CommandFailedException {
        backup = new BackupAndRestoreAttributes.Builder(EEFixtures.DEFAULT_BINDINGS_ADDRESS).build();
        client.apply(backup.backup());
    }

    @AfterClass
    public static void afterClass() throws CommandFailedException {
        client.apply(backup.restore());
    }

    @Page private EEPage page;
    @Inject private Console console;
    private FormFragment form;

    @Before
    public void setUp() throws Exception {
        page.navigate();
        page.getDefaultBindingsItem().click();
        form = page.getDefaultBindingsForm();
    }

    @Test
    public void update() throws Exception {
        String contextService = Random.name();
        form.edit();
        form.text(CONTEXT_SERVICE, contextService);
        form.save();

        console.verifySuccess();
        new ResourceVerifier(EEFixtures.DEFAULT_BINDINGS_ADDRESS, client)
                .verifyAttribute(CONTEXT_SERVICE, contextService);
    }

    @Test
    public void reset() throws Exception {
        form.reset();

        console.verifySuccess();
        new ResourceVerifier(EEFixtures.DEFAULT_BINDINGS_ADDRESS, client)
                .verifyReset();
    }
}