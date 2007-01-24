/*
 * Copyright 2006-2007 Tom Adams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.internal.mock;

import java.util.ArrayList;
import java.util.List;
import org.jmock.core.Verifiable;

public final class VerifierImpl implements Verifier {
    private final List<Verifiable> verifiables = new ArrayList<Verifiable>();

    public void addVerifiable(final Verifiable verifiable) {
        verifiables.add(verifiable);
    }

    public void verify() {
        for (final Verifiable verifiable : verifiables) {
            verifiable.verify();
        }
    }
}
