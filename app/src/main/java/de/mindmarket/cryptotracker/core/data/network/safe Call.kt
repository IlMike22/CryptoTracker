package de.mindmarket.cryptotracker.core.data.network

import de.mindmarket.cryptotracker.core.domain.util.NetworkError
import de.mindmarket.cryptotracker.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (exc: UnresolvedAddressException) {  // e.g. airplane mode enabled..
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (exc: SerializationException) {
        return Result.Error(NetworkError.SERIALIZATION)
    } catch (exc: Exception) {
        coroutineContext.ensureActive() // rethrow cancellation exception from coroutines..
        return Result.Error(NetworkError.UNKNOWN)
    }
    
    return responseToResult(response)
}