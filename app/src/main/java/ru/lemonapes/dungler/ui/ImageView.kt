package ru.lemonapes.dungler.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import ru.lemonapes.dungler.Utils.Companion.log
import ru.lemonapes.dungler.navigation.ktor.IMAGES_PATH
import ru.lemonapes.dungler.navigation.ktor.IMAGES_POSTFIX

@Composable
fun ImageView(
    modifier: Modifier = Modifier,
    url: String?,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null
) {
    val fullUrl = IMAGES_PATH + url + IMAGES_POSTFIX
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(fullUrl)
                .crossfade(true)
                .build(),
            contentScale = contentScale,
            onSuccess = {
                log("Success $fullUrl")
            },
            onError = { err->
                log("ERR $fullUrl $err")
            }
        )
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            painter = painter,
            contentScale = contentScale,
            colorFilter = colorFilter,
            contentDescription = contentDescription,
        )
    }
}