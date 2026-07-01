package com.alifatma.firewatch.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeNetworkStatusProvider(
    private val online: Boolean
) : NetworkStatusProvider {
    override fun isOnline(): Boolean = online
    override fun observeNetworkStatus(): Flow<Boolean> = flowOf(online)
}
