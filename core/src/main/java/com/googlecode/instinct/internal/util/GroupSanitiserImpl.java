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

package com.googlecode.instinct.internal.util;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.AnnotationAttribute;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import java.util.ArrayList;
import java.util.List;

public final class GroupSanitiserImpl implements GroupSanitiser {
    public AnnotationAttribute sanitise(final String groups) {
        checkNotNull(groups);
        if (groups.trim().length() == 0) {
            return IGNORE;
        } else {
            return new AnnotationAttribute("group", splitGroupsAndCleanUpNames(groups));
        }
    }

    private String[] splitGroupsAndCleanUpNames(final String unsplitGroups) {
        final List<String> cleanGroupNames = new ArrayList<String>();
        final String[] splitGroups = unsplitGroups.split(",");
        for (final String group : splitGroups) {
            final String trimmedGroup = trimLeadingAndTralingSpacesAndComma(group);
            if (trimmedGroup.length() != 0) {
                cleanGroupNames.add(trimmedGroup);
            }
        }
        return cleanGroupNames.toArray(new String[cleanGroupNames.size()]);
    }

    private String trimLeadingAndTralingSpacesAndComma(final String group) {
        return group.replaceAll(",", "").trim();
    }
}
