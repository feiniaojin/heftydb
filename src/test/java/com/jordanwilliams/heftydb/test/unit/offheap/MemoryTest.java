/*
 * Copyright (c) 2013. Jordan Williams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jordanwilliams.heftydb.test.unit.offheap;

import com.jordanwilliams.heftydb.offheap.Memory;
import com.jordanwilliams.heftydb.util.ByteBuffers;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class MemoryTest {

    @Test
    public void intTest() {
        Memory memory = Memory.allocate(256);

        memory.putInt(120, 4);
        Assert.assertEquals("Read back int", 4, memory.getInt(120));
    }

    @Test
    public void byteTest() {
        Memory memory = Memory.allocate(256);

        memory.putByte(120, (byte) 8);
        Assert.assertEquals("Read back byte", (byte) 8, memory.getByte(120));
    }

    @Test
    public void longTest() {
        Memory memory = Memory.allocate(256);

        memory.putLong(120, 1234567890L);
        Assert.assertEquals("Read back long", 1234567890L, memory.getLong(120));
    }

    @Test
    public void fromBufferTest() {
        ByteBuffer testBuffer = ByteBuffer.allocateDirect(1024);

        for (int i = 0; i < testBuffer.capacity(); i++){
            testBuffer.put((byte) 255);
        }

        Memory memory = Memory.copyFromByteBuffer(testBuffer);

        for (int i = 0; i < testBuffer.capacity(); i++){
            Assert.assertEquals("Read back long", (byte) 255, memory.getByte(i));
        }
    }

    @Test
    public void byteRangeTest() {
        Memory memory = Memory.allocate(256);

        ByteBuffer testBuffer = ByteBuffers.fromString("hello");
        memory.putBytes(0, testBuffer);

        ByteBuffer readBuffer = ByteBuffer.allocate(testBuffer.capacity());
        memory.getBytes(0, readBuffer);

        Assert.assertEquals("Read back bytes", testBuffer, readBuffer);
    }

    @Test
    public void retainReleaseTest() {
        Memory memory = Memory.allocate(256);

        memory.retain();
        memory.retain();

        memory.release();
        memory.release();
        memory.release();

        Assert.assertTrue("Memory has been freed", memory.isFree());
    }
}
