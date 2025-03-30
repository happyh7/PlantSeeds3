package com.bps.plantseeds3.common.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

object NetworkUtils {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
               capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): com.bps.plantseeds3.common.util.Result<T> = withContext(Dispatchers.IO) {
        try {
            com.bps.plantseeds3.common.util.Result.Success(apiCall())
        } catch (e: IOException) {
            com.bps.plantseeds3.common.util.Result.Error(Exception("Nätverksfel: ${e.localizedMessage}"))
        } catch (e: Exception) {
            com.bps.plantseeds3.common.util.Result.Error(e)
        }
    }

    fun getErrorMessage(e: Exception): String {
        return when (e) {
            is IOException -> "Nätverksfel: Kontrollera din internetanslutning"
            else -> e.localizedMessage ?: "Ett okänt fel har inträffat"
        }
    }
} 