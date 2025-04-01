package com.bps.plantseeds3.common.util

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class NetworkUtilsTest {
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkUtils: NetworkUtils

    @Before
    fun setup() {
        connectivityManager = mockk()
        networkUtils = NetworkUtils(connectivityManager)
    }

    @Test
    fun `isNetworkAvailable returns true when network is available`() {
        val network = mockk<Network>()
        val networkCapabilities = mockk<NetworkCapabilities>()
        
        every { connectivityManager.activeNetwork } returns network
        every { connectivityManager.getNetworkCapabilities(network) } returns networkCapabilities
        every { networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns true

        assert(networkUtils.isNetworkAvailable())
    }

    @Test
    fun `isNetworkAvailable returns false when network is unavailable`() {
        every { connectivityManager.activeNetwork } returns null

        assert(!networkUtils.isNetworkAvailable())
    }

    @Test
    fun `isNetworkAvailable returns false when network has no internet capability`() {
        val network = mockk<Network>()
        val networkCapabilities = mockk<NetworkCapabilities>()
        
        every { connectivityManager.activeNetwork } returns network
        every { connectivityManager.getNetworkCapabilities(network) } returns networkCapabilities
        every { networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns false

        assert(!networkUtils.isNetworkAvailable())
    }
} 