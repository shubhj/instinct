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
import com.googlecode.instinct.marker.annotate.Specification;
import org.junit.runner.RunWith;

//TODO: Ability to add items.(done)
//TODO: Ability to remove items. (done)

//Note: This specification has not been refactored to make it easier to read.
@RunWith(InstinctRunner.class)
@Context
public final class AShoppingCartWithItemsInIt {

    private ShoppingCart cart;
    private Item mockItem1;
    private Item mockItem2;
    private Item mockItem3;

    @BeforeSpecification
    public void setup() {
        cart = new ShoppingCartImpl();
        mockItem1 = createMockItem();
        mockItem2 = createMockItem();
        mockItem3 = createMockItem();
    }

    @Specification
    public void mustNotBeEmpty() {
        createCartWithThreeItems();
        expect.that(cart.isEmpty()).isFalse();
    }

    @Specification
    public void canHaveAnItemAddedToIt() {
        createCartWithThreeItems();
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).equalTo(3);
        expect.that(cart.contains(mockItem1)).isTrue();
        expect.that(cart.contains(mockItem2)).isTrue();
        expect.that(cart.contains(mockItem3)).isTrue();
        final Item item = createMockItem();
        expect.that(cart.contains(item)).isFalse();
        cart.addItem(item);
        expect.that(cart.size()).equalTo(4);
        expect.that(cart.contains(item)).isTrue();
        expect.that(cart.contains(mockItem1)).isTrue();
        expect.that(cart.contains(mockItem2)).isTrue();
        expect.that(cart.contains(mockItem3)).isTrue();
    }

    @Specification
    public void canHaveMultipleItemsAddedToIt() {
        createCartWithThreeItems();
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).equalTo(3);
        expect.that(cart.contains(mockItem1)).isTrue();
        expect.that(cart.contains(mockItem2)).isTrue();
        expect.that(cart.contains(mockItem3)).isTrue();
        final Item item1 = createMockItem();
        final Item item2 = createMockItem();
        cart.addItem(item1);
        expect.that(cart.size()).equalTo(4);
        expect.that(cart.contains(item1));
        expect.that(cart.contains(mockItem1)).isTrue();
        expect.that(cart.contains(mockItem2)).isTrue();
        expect.that(cart.contains(mockItem3)).isTrue();
        cart.addItem(item2);
        expect.that(cart.size()).equalTo(5);
        expect.that(cart.contains(item2)).isTrue();
        expect.that(cart.contains(item1)).isTrue();
        expect.that(cart.contains(mockItem1)).isTrue();
        expect.that(cart.contains(mockItem2)).isTrue();
        expect.that(cart.contains(mockItem3)).isTrue();
    }

    @Specification
    public void canHaveAnExistingItemRemovedFromIt() {
        createCartWithThreeItems();
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).equalTo(3);
        expect.that(cart.contains(mockItem1)).isTrue();
        expect.that(cart.contains(mockItem2)).isTrue();
        expect.that(cart.contains(mockItem3)).isTrue();
        cart.remove(mockItem1);
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).equalTo(2);
        expect.that(cart.contains(mockItem1)).isFalse();
        expect.that(cart.contains(mockItem2)).isTrue();
        expect.that(cart.contains(mockItem3)).isTrue();
    }

    @Specification
    public void canHaveMultipleExistingItemsRemovedFromIt() {
        createCartWithThreeItems();
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).equalTo(3);
        expect.that(cart.contains(mockItem1)).isTrue();
        expect.that(cart.contains(mockItem2)).isTrue();
        expect.that(cart.contains(mockItem3)).isTrue();
        cart.remove(mockItem2);
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).equalTo(2);
        expect.that(cart.contains(mockItem2)).isFalse();
        expect.that(cart.contains(mockItem1)).isTrue();
        expect.that(cart.contains(mockItem3)).isTrue();
        cart.remove(mockItem3);
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).equalTo(1);
        expect.that(cart.contains(mockItem2)).isFalse();
        expect.that(cart.contains(mockItem3)).isFalse();
        expect.that(cart.contains(mockItem1)).isTrue();
    }

    @Specification
    public void shouldNotRemoveAnItemThatIsNotInIt() {
        createCartWithThreeItems();
        final Item item = createMockItem();
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).equalTo(3);
        expect.that(cart.contains(mockItem1)).isTrue();
        expect.that(cart.contains(mockItem2)).isTrue();
        expect.that(cart.contains(mockItem3)).isTrue();
        cart.remove(item);
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).equalTo(3);
        expect.that(cart.contains(mockItem1)).isTrue();
        expect.that(cart.contains(mockItem2)).isTrue();
        expect.that(cart.contains(mockItem3)).isTrue();
    }

    private void createCartWithThreeItems() {
        cart.addItem(mockItem1);
        cart.addItem(mockItem2);
        cart.addItem(mockItem3);
    }

    private Item createMockItem() {
        return Mocker.mock(Item.class);
    }
}