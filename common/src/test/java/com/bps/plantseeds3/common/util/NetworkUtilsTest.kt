package com.bps.plantseeds3.common.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class NetworkUtilsTest {
    private lateinit var context: Context
    private lateinit var connectivityManager: ConnectivityManager

    @Before
    fun setup() {
        context = mockk()
        connectivityManager = mockk()
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
    }

    @Test
    fun `isNetworkAvailable returns true when network is available`() {
        val network = mockk<Network>()
        val networkCapabilities = mockk<NetworkCapabilities>()
        
        every { connectivityManager.activeNetwork } returns network
        every { connectivityManager.getNetworkCapabilities(network) } returns networkCapabilities
        every { networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns true

        assert(NetworkUtils.isNetworkAvailable(context))
    }

    @Test
    fun `isNetworkAvailable returns false when network is unavailable`() {
        every { connectivityManager.activeNetwork } returns null

        assert(!NetworkUtils.isNetworkAvailable(context))
    }

    @Test
    fun `isNetworkAvailable returns false when network has no internet capability`() {
        val network = mockk<Network>()
        val networkCapabilities = mockk<NetworkCapabilities>()
        
        every { connectivityManager.activeNetwork } returns network
        every { connectivityManager.getNetworkCapabilities(network) } returns networkCapabilities
        every { networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns false

        assert(!NetworkUtils.isNetworkAvailable(context))
    }
} 