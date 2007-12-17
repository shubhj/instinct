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

package com.googlecode.instinct.internal.locate;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

final class FileNameComparator implements Comparator<File>, Serializable {
    private static final long serialVersionUID = -1339989577262145782L;

    public int compare(final File o1, final File o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
