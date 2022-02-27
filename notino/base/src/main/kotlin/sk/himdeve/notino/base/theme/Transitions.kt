package sk.himdeve.notino.base.theme

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

@ExperimentalAnimationApi
fun defaultTween() = tween<Float>(durationMillis = 500)

@ExperimentalAnimationApi
fun defaultFadeIn() = fadeIn(animationSpec = defaultTween())

@ExperimentalAnimationApi
fun defaultFadeOut() = fadeOut(animationSpec = defaultTween())
