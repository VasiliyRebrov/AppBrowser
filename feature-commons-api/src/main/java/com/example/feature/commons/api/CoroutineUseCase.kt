package com.example.feature.commons.api

import com.example.commons.result.Result

interface CoroutineUseCase<in Ti, out To> {

// MARK: - Methods

    suspend fun invoke(params: Ti): Result<To>
}
