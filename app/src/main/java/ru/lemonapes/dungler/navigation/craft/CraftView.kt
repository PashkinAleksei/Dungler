package ru.lemonapes.dungler.navigation.craft

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.navigation.models.CraftItem

@Composable
fun CraftView(craftState: CraftViewState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (craftState.items.isEmpty()) {
            Text(
                text = "No craft items available.",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn {
                items(craftState.items) { item ->
                    CraftItemRow(item)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Reagents:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
        craftState.reagents.forEach { (name, quantity) ->
            Text(
                text = "$name: $quantity",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun CraftItemRow(item: CraftItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Name: ${item.name}",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Gear Type: ${item.gearType}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Stats:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
        item.stats.forEach { (stat, value) ->
            Text(
                text = "$stat: $value",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Reagents:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
        item.reagents.forEach { (name, quantity) ->
            Text(
                text = "$name: $quantity",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
