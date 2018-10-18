package com.everything4droid.urbandickotlin.data

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Khajiev Nizomjon on 05/10/2018.
 */
data class CoroutinesContextProvider(
        val main: CoroutineContext = UI,
        val io: CoroutineContext = CommonPool
)