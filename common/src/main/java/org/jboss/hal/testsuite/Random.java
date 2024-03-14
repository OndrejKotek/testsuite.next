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
package org.jboss.hal.testsuite;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.jboss.dmr.ModelNode;
import org.jboss.hal.resources.Ids;

import static org.jboss.hal.dmr.ModelDescriptionConstants.VALUE;

public class Random {

    private static final int LENGTH = 12;
    private static final String JNDI_PREFIX = "java:jboss/";
    private static final RandomStringGenerator GENERATOR = new RandomStringGenerator.Builder().withinRange('a', 'z')
            .build();

    /** Returns a random name ('a' - 'z') for resource names or attribute values. */
    public static String name() {
        return GENERATOR.generate(LENGTH);
    }

    public static String name(int length) {
        return GENERATOR.generate(length);
    }

    /** Returns a JNDI name starting with "java:jboss/" followed by a random name. */
    public static String jndiName() {
        return jndiName(name());
    }

    /** Returns a JNDI name starting with "java:jboss/" followed by the specified name. */
    public static String jndiName(String name) {
        return JNDI_PREFIX + name;
    }

    /** Returns a random integer between 1 and 99 */
    public static int number() {
        return RandomUtils.nextInt(1, 100);
    }

    /** Returns a random double between 0.001 and 99.999 */
    public static double numberDouble() {
        double v = RandomUtils.nextDouble(0.001, 99.999);
        String str = Double.toString(v);
        v = Double.parseDouble(str.substring(0, str.indexOf('.') + 4));
        return v;
    }

    /**
     * Returns a random integer within the specified range.
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endExclusive   the upper bound (not included)
     */
    public static int number(int startInclusive, int endExclusive) {
        return RandomUtils.nextInt(startInclusive, endExclusive);
    }

    public static long number(long startInclusive, long endExclusive) {
        return RandomUtils.nextLong(startInclusive, endExclusive);
    }

    /** Returns a list with three random values. */
    public static List<String> list() {
        return list(3);
    }

    public static List<String> list(int size) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(name());
        }
        return list;
    }

    /** Returns a model node with three random key / value pairs */
    public static ModelNode properties() {
        return properties(3);
    }

    public static ModelNode properties(int size) {
        ModelNode node = new ModelNode();
        for (int i = 0; i < size; i++) {
            node.get(Ids.build(VALUE, String.valueOf(i))).set(name());
        }
        return node;
    }

    public static ModelNode properties(String key, String value) {
        ModelNode node = new ModelNode();
        node.get(key).set(value);
        return node;
    }

    private Random() {
    }
}
