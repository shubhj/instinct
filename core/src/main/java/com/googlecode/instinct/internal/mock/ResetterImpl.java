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
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class ResetterImpl implements Resetter {
    private final List<Resetable> resetables = new ArrayList<Resetable>();

    public void addResetable(final Resetable resetable) {
        checkNotNull(resetable);
        resetables.add(resetable);
    }

    public void reset() {
        for (final Resetable resetable : resetables) {
            resetable.reset();
        }
    }
}
