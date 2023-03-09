package com.kmkole86.screen3

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
internal fun Screen3Route(
    modifier: Modifier = Modifier
) {
    Screen3(modifier = modifier)
}

@Composable
internal fun Screen3(modifier: Modifier = Modifier) {
    Text(text = "Screen 3", modifier = modifier.fillMaxSize(), textAlign = TextAlign.Center)
}