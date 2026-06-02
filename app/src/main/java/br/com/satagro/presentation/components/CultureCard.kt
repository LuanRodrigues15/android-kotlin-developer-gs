package br.com.satagro.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import br.com.satagro.data.model.CultureItem
import br.com.satagro.domain.model.CultureRanking

@Composable
fun CultureCard(
    culture: CultureItem,
    isFavorited: Boolean,
    onClick: () -> Unit,
    onFavorite: () -> Unit,
    score: Int? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = getCultureIcon(culture.iconKey),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = onFavorite, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = if (isFavorited) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favoritar",
                        tint = if (isFavorited) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = culture.name,
                style = MaterialTheme.typography.titleSmall
            )
            AssistChip(
                onClick = {},
                label = { Text(culture.badge, style = MaterialTheme.typography.labelSmall) }
            )
            if (score != null) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Score: $score",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                LinearProgressIndicator(
                    progress = { score / 100f },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

fun getCultureIcon(iconKey: String): ImageVector = when (iconKey) {
    "soja"         -> Icons.Filled.Grain
    "milho"        -> Icons.Filled.Grain
    "cana"         -> Icons.Filled.LocalFlorist
    "arroz"        -> Icons.Filled.Grass
    "algodao"      -> Icons.Filled.Air
    "cafe"         -> Icons.Filled.LocalCafe
    "mandioca"     -> Icons.Filled.Eco
    "pecuaria"     -> Icons.Filled.Pets
    "piscicultura" -> Icons.Filled.WaterDrop
    "frango"       -> Icons.Filled.Egg
    else           -> Icons.Filled.Eco
}
