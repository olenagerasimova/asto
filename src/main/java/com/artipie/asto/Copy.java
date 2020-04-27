/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 artipie.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.artipie.asto;

import com.artipie.asto.rx.RxCopy;
import com.artipie.asto.rx.RxStorageWrapper;
import hu.akarnokd.rxjava2.interop.CompletableInterop;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Storage synchronization.
 * @since 0.19
 * @checkstyle UnusedPrivateField (500 lines)
 * @checkstyle SingularField (500 lines)
 * @checkstyle NonStaticMethodCheck (500 lines)
 * @checkstyle MemberNameCheck (500 lines)
 * @checkstyle ParameterNameCheck (500 lines)
 * @todo #160:30min Implement Copy class.
 *  This class currently is not implemented. This class should be implemented in a way to storage
 *  synchronization would work properly.
 */
@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "PMD.AvoidDuplicateLiterals"})
public class Copy {

    /**
     * The storage to copy from.
     */
    private final Storage from;

    /**
     * The keys to transfer.
     */
    private final List<Key> keys;

    /**
     * Ctor.
     * @param from The storage to copy to.
     * @param keys The keys to copy.
     */
    public Copy(final Storage from, final List<Key> keys) {
        this.from = from;
        this.keys = keys;
    }

    /**
     * Copy keys to the specified storage.
     * @param to The storage to copy to.
     * @return When copy operation completes
     */
    public CompletableFuture<Void> copy(final Storage to) {
        return new RxCopy(new RxStorageWrapper(this.from), this.keys).copy(new RxStorageWrapper(to))
            .to(CompletableInterop.await())
            .<Void>thenApply(o -> null)
            .toCompletableFuture();
    }
}
