package com.kmkole86.screen2

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
internal fun Screen2Route(
    modifier: Modifier = Modifier
) {
    Screen2(modifier = modifier)
}

@Composable
internal fun Screen2(modifier: Modifier = Modifier) {
    Text(text = "Screen 2", modifier = modifier.fillMaxSize(), textAlign = TextAlign.Center)
}