package ru.lemonapes.dungler.network.endpoints

import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.lemonapes.dungler.network.ENDPOINT
import ru.lemonapes.dungler.network.HERO_ID
import ru.lemonapes.dungler.network.HttpClientProvider
import ru.lemonapes.dungler.network.models.UpgradeItemsResponse

suspend fun loadUpgradeItems(): UpgradeItemsResponse {
    val url = "$ENDPOINT/upgrade_items?hero_id=$HERO_ID"
    return HttpClientProvider.client.get(url).body()
}