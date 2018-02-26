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
package org.jboss.hal.testsuite.test.configuration.datasource;

import org.jboss.hal.resources.Ids;
import org.jboss.hal.testsuite.CrudConstants;
import org.jboss.hal.testsuite.Random;
import org.wildfly.extras.creaper.core.online.operations.Address;

import static org.jboss.hal.dmr.ModelDescriptionConstants.DATASOURCES;
import static org.jboss.hal.dmr.ModelDescriptionConstants.DATA_SOURCE;

public final class DataSourceFixtures {

    private static final String DATA_SOURCE_PREFIX = "ds";

    static String H2_NAME = "H2DS";
    static String H2_JNDI_NAME = "java:/H2DS";
    static String H2_DRIVER = "h2";
    static String H2_CONNECTION_URL = h2ConnectionUrl("test");
    static String H2_USER_NAME = "sa";
    static String H2_PASSWORD = "sa";

    static String DATA_SOURCE_CREATE_CUSTOM = Ids.build(DATA_SOURCE_PREFIX, "create-custom", Random.name());
    static String DATA_SOURCE_CREATE_EXISTING = Ids.build(DATA_SOURCE_PREFIX, "create-existing", Random.name());
    static String DATA_SOURCE_CREATE_H2 = H2_NAME;
    static String DATA_SOURCE_CREATE_TEST_CANCEL = Ids.build(DATA_SOURCE_PREFIX, "create-test-cancel", Random.name());
    static String DATA_SOURCE_CREATE_TEST_CHANGE = Ids.build(DATA_SOURCE_PREFIX, "create-test-change", Random.name());
    static String DATA_SOURCE_CREATE_TEST_FINISH = Ids.build(DATA_SOURCE_PREFIX, "create-test-finish", Random.name());
    static String DATA_SOURCE_DELETE = Ids.build(DATA_SOURCE_PREFIX, CrudConstants.DELETE, Random.name());
    static String DATA_SOURCE_DISABLE = Ids.build(DATA_SOURCE_PREFIX, "disable", Random.name());
    static String DATA_SOURCE_ENABLE = Ids.build(DATA_SOURCE_PREFIX, "enable", Random.name());
    static String DATA_SOURCE_READ = Ids.build(DATA_SOURCE_PREFIX, CrudConstants.READ, Random.name());
    static String DATA_SOURCE_TEST = Ids.build(DATA_SOURCE_PREFIX, "test", Random.name());
    static String DATA_SOURCE_UPDATE = Ids.build(DATA_SOURCE_PREFIX, CrudConstants.UPDATE, Random.name());

    static String URL_DELIMITER = "url-delimiter";

    static Address SUBSYSTEM_ADDRESS = Address.subsystem(DATASOURCES);
    static String VALID_CONNECTION_CHECKER_CLASS_NAME = "valid-connection-checker-class-name";
    static String VALID_CONNECTION_CHECKER_PROPERTIES = "valid-connection-checker-properties";
    static String BACKGROUND_VALIDATION = "background-validation";
    static String BACKGROUND_VALIDATION_MILLIS = "background-validation-millis";
    static String USE_TRY_LOCK = "use-try-lock";
    static String BLOCKING_TIMEOUT_WAIT_MILLIS = "blocking-timeout-wait-millis";
    static String SPY = "spy";
    static String TRACKING = "tracking";

    static Address dataSourceAddress(String name) {
        return SUBSYSTEM_ADDRESS.and(DATA_SOURCE, name);
    }

    static String h2ConnectionUrl(String name) {
        return "jdbc:h2:mem:" + name + ";DB_CLOSE_DELAY=-1";
    }

    private DataSourceFixtures() {
    }
}
