package com.example.feature.commons.api

import com.example.commons.result.Result
import kotlinx.coroutines.flow.Flow

interface Feature<in Ti, out To> {

// MARK: - Methods

    fun invoke(params: Ti): Flow<Result<To>>
}
