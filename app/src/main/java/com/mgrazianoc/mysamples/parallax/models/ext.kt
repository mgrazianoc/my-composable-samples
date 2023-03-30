package com.mgrazianoc.mysamples.parallax.models

import android.util.Log
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import kotlin.math.abs

// Returns the normalized center item offset (-1,1)
fun LazyListLayoutInfo.normalizedItemPosition(key: Any): Float =
    visibleItemsInfo
        .firstOrNull { it.key == key }
        ?.let {
            val tag = "NORM_${it.key}"

            val Vs = viewportStartOffset
            val Ve = viewportEndOffset
            val Vm = (Vs + Ve) / 2f
            val H = abs(Ve - Vs)
            val vc = abs(Vm - Ve) - it.size / 2f

            /**
             * beforeContentPadding affects item offset
             * */
            val o = it.offset + beforeContentPadding
            val n = (o - vc) / (H - vc)

            Log.d(tag, "Ps: $beforeContentPadding, Pe: $afterContentPadding")
            Log.d(tag, "Vs: $viewportStartOffset, Ve: $viewportEndOffset, Vm: $Vm, H: $H")
            Log.d(tag, "h: ${it.size}, offset: ${it.offset}, o: $o Vc: $vc")
            Log.d(tag, "n: $n")
            Log.d(tag, "==============================================")

            n
        }
        ?: 0F


fun LazyListLayoutInfo.transitionToCenter(key: Any): Float =
    visibleItemsInfo
        .firstOrNull { it.key == key }
        ?.let {
            val tag = "TRANSFORM_${it.key}"

            val Vs = viewportStartOffset
            val Ve = viewportEndOffset
            val H = abs(Ve - Vs)
            val Vm = (Vs + Ve) / 2f
            val Vc = abs(Vm - Ve)

            val o = it.offset + beforeContentPadding

            val t = ((o - (Vc - it.size / 2)) * (H / 2 + it.size / 2)) / (Vc + it.size / 2)

            Log.d(tag, "Ps: $beforeContentPadding, Pe: $afterContentPadding")
            Log.d(tag, "Vs: $viewportStartOffset, Ve: $viewportEndOffset H: $H, h: ${it.size}")
            Log.d(tag, "t: $t")
            Log.d(tag, "==============================================")

            t
        }
        ?: 0F