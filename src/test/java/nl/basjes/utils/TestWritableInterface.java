/**
 * Copyright 2013 Niels Basjes
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.basjes.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Writable;

public final class TestWritableInterface {

    /**
     * Converts an instance of Writable into a byte[].
     * @param writable
     * @return
     * @throws IOException
     */
    public static byte[] serialize(Writable writable) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dataOut = null;
        try {
            dataOut = new DataOutputStream(out);
            writable.write(dataOut);
            return out.toByteArray();
        } finally {
            IOUtils.closeStream(dataOut);
        }
    }

    // ------------------------------------------

    /**
     * Converts a byte[] back into an instance of the specified class (assuming it was created with the above method serialize).
     * @param bytes
     * @param clazz
     * @return
     * @throws IOException
     */
    public static <T extends Writable> T asWritable(byte[] bytes, Class<T> clazz) throws IOException {
        T result = null;
        DataInputStream dataIn = null;
        try {
            result = clazz.newInstance();
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            dataIn = new DataInputStream(in);
            result.readFields(dataIn);
        } catch (InstantiationException e) {
            // should not happen
            assert false;
        } catch (IllegalAccessException e) {
            // should not happen
            assert false;
        } finally {
            IOUtils.closeStream(dataIn);
        }
        return result;
    }

    // ------------------------------------------

}