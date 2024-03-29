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

import java.util.ArrayList;
import java.util.List;

public final class ShoppingCartImpl implements ShoppingCart {

    private final List<Item> cart = new ArrayList<Item>();

    public void addItem(final Item item) {
        cart.add(item);
    }

    public int size() {
        return cart.size();
    }

    public Item getItem(final int itemNum) {
        return cart.get(itemNum);
    }

    public void remove(final Item item) {
        cart.remove(item);
    }

    public boolean contains(final Item item) {
        return cart.contains(item);
    }

    public boolean isEmpty() {
        return cart.isEmpty();
    }
}
