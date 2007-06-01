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

    @SuppressWarnings({"HardcodedLineSeparator"})
    public void verify() {
        final StringBuilder verificationErrors = new StringBuilder();
        for (final Verifiable verifiable : verifiables) {
            verify(verifiable, verificationErrors);
        }
        if (verificationErrors.length() != 0) {
            throw new AssertionError("One or more behaviour expectations failed.\n\n" + verificationErrors.toString());
        }
    }

    // SUPPRESS IllegalCatch {
    @SuppressWarnings({"CatchGenericClass"})
    private void verify(final Verifiable verifiable, final StringBuilder verificationErrors) {
        try {
            verifiable.verify();
        } catch (Throwable e) {
            verificationErrors.append(e.toString());
        }
    }
    // } SUPPRESS IllegalCatch
}
