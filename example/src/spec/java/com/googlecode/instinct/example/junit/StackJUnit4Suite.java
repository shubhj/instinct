/*
 * Copyright 2006-2007 Chris Myers, Workingmouse
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

package com.googlecode.instinct.example.junit;

import com.googlecode.instinct.example.stack.AGlossyMagazine;
import com.googlecode.instinct.example.stack.ANonEmptyStack;
import com.googlecode.instinct.example.stack.AnEmptyMagazineRack;
import com.googlecode.instinct.example.stack.AnEmptyStack;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.ContextClasses;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@ContextClasses(value = {AnEmptyStack.class, ANonEmptyStack.class, AnEmptyMagazineRack.class, AGlossyMagazine.class})
public final class StackJUnit4Suite {
    private StackJUnit4Suite() {
        throw new UnsupportedOperationException();
    }
}
