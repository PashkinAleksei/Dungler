package ru.lemonapes.dungler.navigation.ktor

import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.lemonapes.dungler.navigation.models.CraftItemsResponse

suspend fun loadCraftItems(): CraftItemsResponse {
    val url = "$ENDPOINT/craft_items?hero_id=$HERO_ID"
    return HttpClientProvider.client.get(url).body()
}