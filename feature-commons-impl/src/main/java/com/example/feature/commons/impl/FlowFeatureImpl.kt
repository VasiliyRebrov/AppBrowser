package com.example.feature.commons.impl

import com.example.commons.result.Result
import com.example.feature.commons.api.FlowFeature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

@Deprecated("Consider to merge with 'FlowFeature'.")
abstract class FlowFeatureImpl<in Ti, out To>: FlowFeature<Ti, To> {

// MARK: - Properties

    protected open val coroutineDispatcher = Dispatchers.IO

// MARK: - Methods

    override fun invoke(params: Ti): Flow<Result<To>> {
        return execute(params)
            .catch { throwable -> emit(value = Result.error(throwable)) }
            .flowOn(this.coroutineDispatcher)
    }

    protected abstract fun execute(params: Ti): Flow<Result<To>>
}
