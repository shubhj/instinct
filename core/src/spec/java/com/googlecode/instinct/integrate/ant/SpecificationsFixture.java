/*
 * Copyright 2008 Jeremy Mawson
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

import java.io.File;
import org.apache.tools.ant.Project;

public final class SpecificationsFixture {
    private SpecificationsFixture() {
    }

    public static Specifications any(final Object object) {
        final String path = object.getClass().getResource("/").getPath();
        final File dir = new File(path);
        final Project project = new Project();
        final Specifications specifications = new Specifications(project);
        project.setBaseDir(dir);
        specifications.setDir(path);
        return specifications;
    }
}
