package com.kmkole86.data.result

import java.util.concurrent.CancellationException
import kotlin.Result

inline fun <T, R> T.runCatchingCancelable(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}