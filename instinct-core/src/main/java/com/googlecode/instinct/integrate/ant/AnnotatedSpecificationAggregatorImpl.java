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

package com.googlecode.instinct.integrate.ant;

import com.googlecode.instinct.internal.aggregate.BehaviourContextAggregator;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotWhitespace;

public final class AnnotatedSpecificationAggregatorImpl implements AnnotatedSpecificationAggregator {
    private String root;

    public BehaviourContextAggregator getAggregator() {
        return null;
    }

    public void setRoot(final String root) {
        checkNotWhitespace(root);
        this.root = root;
    }

    public JavaClassName[] getContextNames() {
//        final FileFilter filter = objectFactory.create(AnnotationFileFilter.class, packageRoot, BehaviourContext.class);
//        return classLocator.locate(packageRoot, filter);
        return null;
    }
}
