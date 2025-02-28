package com.example.feature.commons.impl

import com.example.commons.result.Result
import com.example.feature.commons.api.Feature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

@Deprecated("Consider to merge with 'Feature'.")
abstract class FeatureImpl<in Ti, out To>: Feature<Ti, To> {

// MARK: - Properties

    protected open val coroutineDispatcher = Dispatchers.IO

// MARK: - Methods

    override fun invoke(params: Ti): Flow<Result<To>> {
        return execute(params)
            .catch { ex -> emit(value = Result.Error(ex)) }
            .flowOn(coroutineDispatcher)
    }

    protected abstract fun execute(params: Ti): Flow<Result<To>>
}
