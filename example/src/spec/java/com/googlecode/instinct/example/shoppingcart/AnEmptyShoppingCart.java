/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.example.shoppingcart;

import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.behaviour.Mocker.reset;
import static com.googlecode.instinct.expect.behaviour.Mocker.verify;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.AfterSpecification;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AnEmptyShoppingCart {
    @Subject private ShoppingCart cart;
    @Mock private Item item1;
    @Mock private Item item2;

    @BeforeSpecification
    public void addInitialItemsToCart() {
        reset();
        cart = new ShoppingCartImpl();
    }

    @AfterSpecification
    public void after() {
        verify();
    }

    @Specification
    public void shouldBeEmpty() {
        expect.that(cart.isEmpty()).isTrue();
    }

    @Specification
    public void canHaveAnItemAddedToIt() {
        expect.that(cart.isEmpty()).isTrue();
        cart.addItem(item1);
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).isEqualTo(1);
        expect.that(cart.contains(item1)).isTrue();
    }

    @Specification
    public void canHaveMultipleItemsAddedToIt() {
        expect.that(cart.isEmpty()).isTrue();
        cart.addItem(item1);
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).isEqualTo(1);
        expect.that(cart.contains(item1)).isTrue();
        cart.addItem(item2);
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).isEqualTo(2);
        expect.that(cart.contains(item2)).isTrue();
        expect.that(cart.contains(item1)).isTrue();
    }

    @Specification
    public void doesNotFailWhenAnItemIsRemovedFromIt() {
        expect.that(cart.isEmpty()).isTrue();
        cart.remove(item1);
        expect.that(cart.isEmpty()).isTrue();
    }
}
