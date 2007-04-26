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

package com.googlecode.instinct.internal.core;

import java.util.Set;
import java.util.HashSet;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextRunner;
import com.googlecode.instinct.internal.runner.StandardContextRunner;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class ContextClassImpl implements ContextClass {
    private ContextRunner contextRunner = new StandardContextRunner();
    private Set<ContextRunListener> contextRunListeners = new HashSet<ContextRunListener>();
    private final Class<?> contextType;

    public <T> ContextClassImpl(final Class<T> contextType) {
        checkNotNull(contextType);
        this.contextType = contextType;
    }

    @SuppressWarnings({"unchecked"})
    public <T> Class<T> getType() {
        return (Class<T>) contextType;
    }

    public String getName() {
        return contextType.getSimpleName();
    }

    public void addRunListener(final ContextRunListener contextRunListener) {
        checkNotNull(contextRunListener);
        contextRunListeners.add(contextRunListener);
    }

    public ContextResult run() {
        for (final ContextRunListener contextRunListener : contextRunListeners) {
            contextRunListener.onContext(this);
        }
        return contextRunner.run(contextType);
    }
}
