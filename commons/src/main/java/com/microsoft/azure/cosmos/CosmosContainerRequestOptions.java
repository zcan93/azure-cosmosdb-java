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
package com.microsoft.azure.cosmos;

import com.microsoft.azure.cosmosdb.ConsistencyLevel;
import com.microsoft.azure.cosmosdb.RequestOptions;

/**
 * Encapsulates options that can be specified for a request issued to cosmos container.
 */
public class CosmosContainerRequestOptions extends CosmosRequestOptions {
    private Integer offerThroughput;
    private boolean populateQuotaInfo;
    private ConsistencyLevel consistencyLevel;
    private String sessionToken;

    /**
     * Gets the throughput in the form of Request Units per second when creating a cosmos container.
     *
     * @return the throughput value.
     */
    public Integer getOfferThroughput() {
        return offerThroughput;
    }

    /**
     * Sets the throughput in the form of Request Units per second when creating a cosmos container.
     *
     * @param offerThroughput the throughput value.
     */
    public CosmosContainerRequestOptions offerThroughput(Integer offerThroughput) {
        this.offerThroughput = offerThroughput;
        return this;
    }

    /**
     * Gets the PopulateQuotaInfo setting for cosmos container read requests in the Azure Cosmos DB database service.
     * PopulateQuotaInfo is used to enable/disable getting cosmos container quota related stats for document
     * collection read requests.
     *
     * @return true if PopulateQuotaInfo is enabled
     */
    public boolean isPopulateQuotaInfo() {
        return populateQuotaInfo;
    }

    /**
     * Sets the PopulateQuotaInfo setting for cosmos container read requests in the Azure Cosmos DB database service.
     * PopulateQuotaInfo is used to enable/disable getting cosmos container quota related stats for document
     * collection read requests.
     *
     * @param populateQuotaInfo a boolean value indicating whether PopulateQuotaInfo is enabled or not
     */
    public CosmosContainerRequestOptions populateQuotaInfo(boolean populateQuotaInfo) {
        this.populateQuotaInfo = populateQuotaInfo;
        return this;
    }

    /**
     * Gets the consistency level required for the request.
     *
     * @return the consistency level.
     */
    public ConsistencyLevel getConsistencyLevel() {
        return consistencyLevel;
    }

    /**
     * Sets the consistency level required for the request.
     *
     * @param consistencyLevel the consistency level.
     */
    public CosmosContainerRequestOptions consistencyLevel(ConsistencyLevel consistencyLevel) {
        this.consistencyLevel = consistencyLevel;
        return this;
    }

    /**
     * Gets the token for use with session consistency.
     *
     * @return the session token.
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * Sets the token for use with session consistency.
     *
     * @param sessionToken the session token.
     */
    public CosmosContainerRequestOptions sessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
        return this;
    }

    @Override
    protected RequestOptions toRequestOptions() {
        super.toRequestOptions();
        requestOptions.setOfferThroughput(offerThroughput);
        requestOptions.setPopulateQuotaInfo(populateQuotaInfo);
        requestOptions.setSessionToken(sessionToken);
        requestOptions.setConsistencyLevel(consistencyLevel);
        return requestOptions;
    }
}