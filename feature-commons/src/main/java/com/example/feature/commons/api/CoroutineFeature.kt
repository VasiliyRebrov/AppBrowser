package com.example.feature.commons.api

import com.example.commons.result.Result

interface CoroutineFeature<in Ti, out To> {

// MARK: - Methods

    suspend fun invoke(params: Ti): Result<To>
}
