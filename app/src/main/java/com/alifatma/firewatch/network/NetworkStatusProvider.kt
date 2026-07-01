package com.alifatma.firewatch.network

import kotlinx.coroutines.flow.Flow

interface NetworkStatusProvider {
    fun isOnline(): Boolean
    fun observeNetworkStatus(): Flow<Boolean>
}
