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

package com.googlecode.instinct.internal.util.io;

import com.googlecode.instinct.internal.edge.java.io.InputStreamEdge;
import com.googlecode.instinct.internal.edge.java.io.InputStreamEdgeImpl;
import com.googlecode.instinct.internal.edge.java.io.OutputStreamEdge;
import com.googlecode.instinct.internal.edge.java.io.OutputStreamEdgeImpl;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public final class InputStreamConvertorImpl implements InputStreamConvertor {
    private static final int BUFFER_SIZE = 4096;
    private InputStreamEdge input;

    public InputStreamConvertorImpl(final InputStream input) {
        this.input = new InputStreamEdgeImpl(input);
    }

    public byte[] read() {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        fillBuffer(new OutputStreamEdgeImpl(buffer));
        return buffer.toByteArray();
    }

    private void fillBuffer(final OutputStreamEdge output) {
        int bytesRead = 0;
        while (bytesRead >= 0) {
            bytesRead = readBytes(output);
        }
    }

    private int readBytes(final OutputStreamEdge edge) {
        final byte[] bytes = new byte[BUFFER_SIZE];
        final int bytesRead = input.read(bytes);
        if (bytesRead >= 0) {
            edge.write(bytes, 0, bytesRead);
        }
        return bytesRead;
    }
}
