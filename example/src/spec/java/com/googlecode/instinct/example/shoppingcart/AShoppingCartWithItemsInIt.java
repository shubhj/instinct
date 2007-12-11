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
import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AShoppingCartWithItemsInIt {
    @Subject private ShoppingCart cart;
    @Mock private Item initialItem1;
    @Mock private Item initialItem2;
    @Mock private Item initialItem3;
    @Dummy private Item addedItem1;
    @Dummy private Item addedItem2;

    @BeforeSpecification
    public void addInitialItemsToCart() {
        reset();
        cart = new ShoppingCartImpl();
        cart.addItem(initialItem1);
        cart.addItem(initialItem2);
        cart.addItem(initialItem3);
    }

    @AfterSpecification
    public void after() {
        verify();
    }

    @Specification
    public void mustNotBeEmpty() {
        expect.that(cart.isEmpty()).isFalse();
    }

    @Specification
    public void canHaveAnItemAddedToIt() {
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).isEqualTo(3);
        expect.that(cart.contains(initialItem1)).isTrue();
        expect.that(cart.contains(initialItem2)).isTrue();
        expect.that(cart.contains(initialItem3)).isTrue();
        expect.that(cart.contains(addedItem1)).isFalse();
        cart.addItem(addedItem1);
        expect.that(cart.size()).isEqualTo(4);
        expect.that(cart.contains(addedItem1)).isTrue();
        expect.that(cart.contains(initialItem1)).isTrue();
        expect.that(cart.contains(initialItem2)).isTrue();
        expect.that(cart.contains(initialItem3)).isTrue();
    }

    @Specification
    public void canHaveMultipleItemsAddedToIt() {
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).isEqualTo(3);
        expect.that(cart.contains(initialItem1)).isTrue();
        expect.that(cart.contains(initialItem2)).isTrue();
        expect.that(cart.contains(initialItem3)).isTrue();
        cart.addItem(addedItem1);
        expect.that(cart.size()).isEqualTo(4);
        expect.that(cart.contains(addedItem1));
        expect.that(cart.contains(initialItem1)).isTrue();
        expect.that(cart.contains(initialItem2)).isTrue();
        expect.that(cart.contains(initialItem3)).isTrue();
        cart.addItem(addedItem2);
        expect.that(cart.size()).isEqualTo(5);
        expect.that(cart.contains(addedItem1)).isTrue();
        expect.that(cart.contains(addedItem2)).isTrue();
        expect.that(cart.contains(initialItem1)).isTrue();
        expect.that(cart.contains(initialItem2)).isTrue();
        expect.that(cart.contains(initialItem3)).isTrue();
    }

    @Specification
    public void canHaveAnExistingItemRemovedFromIt() {
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).isEqualTo(3);
        expect.that(cart.contains(initialItem1)).isTrue();
        expect.that(cart.contains(initialItem2)).isTrue();
        expect.that(cart.contains(initialItem3)).isTrue();
        cart.remove(initialItem1);
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).isEqualTo(2);
        expect.that(cart.contains(initialItem1)).isFalse();
        expect.that(cart.contains(initialItem2)).isTrue();
        expect.that(cart.contains(initialItem3)).isTrue();
    }

    @Specification
    public void canHaveMultipleExistingItemsRemovedFromIt() {
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).isEqualTo(3);
        expect.that(cart.contains(initialItem1)).isTrue();
        expect.that(cart.contains(initialItem2)).isTrue();
        expect.that(cart.contains(initialItem3)).isTrue();
        cart.remove(initialItem2);
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).isEqualTo(2);
        expect.that(cart.contains(initialItem2)).isFalse();
        expect.that(cart.contains(initialItem1)).isTrue();
        expect.that(cart.contains(initialItem3)).isTrue();
        cart.remove(initialItem3);
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).isEqualTo(1);
        expect.that(cart.contains(initialItem2)).isFalse();
        expect.that(cart.contains(initialItem3)).isFalse();
        expect.that(cart.contains(initialItem1)).isTrue();
    }

    @Specification
    public void shouldNotRemoveAnItemThatIsNotInIt() {
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).isEqualTo(3);
        expect.that(cart.contains(initialItem1)).isTrue();
        expect.that(cart.contains(initialItem2)).isTrue();
        expect.that(cart.contains(initialItem3)).isTrue();
        cart.remove(addedItem1);
        expect.that(cart.isEmpty()).isFalse();
        expect.that(cart.size()).isEqualTo(3);
        expect.that(cart.contains(initialItem1)).isTrue();
        expect.that(cart.contains(initialItem2)).isTrue();
        expect.that(cart.contains(initialItem3)).isTrue();
    }
}
