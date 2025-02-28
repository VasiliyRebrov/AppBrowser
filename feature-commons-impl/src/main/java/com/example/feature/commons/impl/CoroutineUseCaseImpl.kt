package com.example.feature.commons.impl

import com.example.commons.result.Result
import com.example.feature.commons.api.CoroutineUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class CoroutineUseCaseImpl<in Ti, out To>: CoroutineUseCase<Ti, To> {

// MARK: - Properties

    protected open val coroutineDispatcher = Dispatchers.IO

// MARK: - Methods

    override suspend fun invoke(params: Ti): Result<To> {
        return try {
            withContext(this.coroutineDispatcher) {
                val data = execute(params)
                Result.success(data)
            }
        }
        catch (throwable: Throwable) {
            Result.error(throwable)
        }
    }

    protected abstract suspend fun execute(params: Ti): To
}
