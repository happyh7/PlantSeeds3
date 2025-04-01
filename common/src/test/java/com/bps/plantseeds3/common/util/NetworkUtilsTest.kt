package com.bps.plantseeds3.common.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class NetworkUtilsTest {

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var connectivityManager: ConnectivityManager

    @Mock
    private lateinit var network: Network

    @Mock
    private lateinit var networkCapabilities: NetworkCapabilities

    @Before
    fun setup() {
        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
    }

    @Test
    fun `isNetworkAvailable returns true when network is available`() {
        // Given
        `when`(connectivityManager.activeNetwork).thenReturn(network)
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
        `when`(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).thenReturn(true)

        // When
        val result = NetworkUtils.isNetworkAvailable(context)

        // Then
        assertTrue(result)
    }

    @Test
    fun `isNetworkAvailable returns false when network is not available`() {
        // Given
        `when`(connectivityManager.activeNetwork).thenReturn(null)

        // When
        val result = NetworkUtils.isNetworkAvailable(context)

        // Then
        assertFalse(result)
    }

    @Test
    fun `isNetworkAvailable returns false when network has no internet capability`() {
        // Given
        `when`(connectivityManager.activeNetwork).thenReturn(network)
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
        `when`(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).thenReturn(false)

        // When
        val result = NetworkUtils.isNetworkAvailable(context)

        // Then
        assertFalse(result)
    }
} 