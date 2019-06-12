/*
 * The MIT License (MIT)
 * Copyright (c) 2018 Microsoft Corporation
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.azure.data.cosmos.rx.examples;

import com.azure.data.cosmos.AsyncDocumentClient;
import com.azure.data.cosmos.ConnectionMode;
import com.google.common.base.Strings;
import org.testng.ITest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

import static com.azure.data.cosmos.AsyncDocumentClient.Builder;

public class NamedCosmosClientTest implements ITest {

    private final Builder builder = new AsyncDocumentClient.Builder();
    private String testName;

    public final Builder builder() {
        return this.builder;
    }

    @Override
    public final String getTestName() {
        return this.testName;
    }

    @BeforeMethod(alwaysRun = true)
    public final void setTestName(Method method) {

        String connectionMode = this.builder.getConnectionPolicy().connectionMode() == ConnectionMode.DIRECT
            ? "Direct " + this.builder.getConfigs().getProtocol()
            : "Gateway";

        this.testName = Strings.lenientFormat("%s::%s[%s with %s consistency]",
            method.getDeclaringClass().getSimpleName(),
            method.getName(),
            connectionMode,
            builder.getDesiredConsistencyLevel());
    }

    @AfterMethod(alwaysRun = true)
    public final void unsetTestName() {
        this.testName = null;
    }
}
