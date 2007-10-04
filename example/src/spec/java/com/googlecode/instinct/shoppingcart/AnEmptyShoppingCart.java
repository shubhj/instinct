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

package com.googlecode.instinct.shoppingcart;

import com.googlecode.instinct.example.shoppingcart.Item;
import com.googlecode.instinct.example.shoppingcart.ShoppingCart;
import com.googlecode.instinct.example.shoppingcart.ShoppingCartImpl;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.expect.behaviour.Mocker;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context
public final class AnEmptyShoppingCart {
    private ShoppingCart cart;
    @Mock private Item mockItem1;
    @Mock private Item mockItem2;

    @BeforeSpecification
    public void setup() {
        cart = new ShoppingCartImpl();
        mockItem1 = createMockItem();
        mockItem2 = createMockItem();
    }

    @Specification
    public void shouldBeEmpty() {
        expect.that(cart.isEmpty()).isTrue();
    }

    @Specification
    public void canHaveAnItemAddedToIt() {
        expect.that(cart.isEmpty()).isTrue();

        cart.addItem(mockItem1);

        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).equalTo(1);
        expect.that(cart.contains(mockItem1)).isTrue();
    }

    @Specification
    public void canHaveMultipleItemsAddedToIt() {
        expect.that(cart.isEmpty()).isTrue();

        cart.addItem(mockItem1);

        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).equalTo(1);
        expect.that(cart.contains(mockItem1)).isTrue();

        cart.addItem(mockItem2);

        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).equalTo(2);
        expect.that(cart.contains(mockItem2)).isTrue();
        expect.that(cart.contains(mockItem1)).isTrue();
    }

    @Specification
    public void doesNotFailWhenAnItemIsRemovedFromIt() {
        final Item item = createMockItem();

        expect.that(cart.isEmpty()).isTrue();
        cart.remove(item);
        expect.that(cart.isEmpty()).isTrue();
    }

    private Item createMockItem() {
        return Mocker.mock(Item.class);
    }
}
