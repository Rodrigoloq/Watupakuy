package com.rodrigoloq.watupakuy.feature.report.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.material.icons.outlined.TravelExplore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.rodrigoloq.watupakuy.R
import com.rodrigoloq.watupakuy.utils.Tourism
import com.rodrigoloq.watupakuy.ui.common.BrandCard
import com.rodrigoloq.watupakuy.ui.common.SectionHeader
import com.rodrigoloq.watupakuy.ui.theme.Spacing
import com.rodrigoloq.watupakuy.ui.theme.WatupakuyNavyLight
import com.rodrigoloq.watupakuy.ui.theme.WatupakuySky
import com.rodrigoloq.watupakuy.ui.theme.WatupakuyTeal

private const val COLLAPSED_DESCRIPTION_LINES = 4
private val DestinationCardWidth = 140.dp
private val DestinationImageHeight = 100.dp
private val DestinationIconSize = 36.dp

private val destinationGradients = listOf(
    Brush.linearGradient(listOf(WatupakuyTeal, WatupakuySky)),
    Brush.linearGradient(listOf(WatupakuyNavyLight, WatupakuySky)),
    Brush.linearGradient(listOf(WatupakuySky, WatupakuyTeal))
)

@Composable
fun TourismCard(modifier: Modifier = Modifier,
                tourism: Tourism) {
    val title = stringResource(R.string.section_tourism)
    BrandCard(modifier) {
        SectionHeader(title = title, icon = Icons.Outlined.TravelExplore)
        ExpandableDescription(tourism.description)
        if (tourism.bestPlaces.isNotEmpty()) {
            Text(
                text = stringResource(R.string.tourism_top_destinations),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.m)) {
                itemsIndexed(tourism.bestPlaces, key = { index, item -> "$index-${item.title}" }) { index, item ->
                    DestinationCard(tourism,
                        urlImage = item.imageUrl,
                        gradient = destinationGradients[index % destinationGradients.size],
                        title = item.title)
                }
            }
        }
    }
}

@Composable
private fun ExpandableDescription(text: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = if (expanded) Int.MAX_VALUE else COLLAPSED_DESCRIPTION_LINES,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .animateContentSize()
            .clickable { expanded = !expanded }
    )
}

/**
 * Imagen del destino con Coil (caché en memoria y disco). Mientras carga se ve el degradado
 * de marca; si falla o no hay URL se muestra no_image.png.
 */
@Composable
private fun DestinationCard(tourism: Tourism,
                            title: String,
                            urlImage: String,
                            gradient: Brush) {
    val imageDescription = stringResource(R.string.tourism_destination_image)
    Column(
        modifier = Modifier.width(DestinationCardWidth),
        verticalArrangement = Arrangement.spacedBy(Spacing.s)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(DestinationImageHeight)
                .clip(MaterialTheme.shapes.medium)
                .background(gradient)
                .semantics { contentDescription = imageDescription },
            contentAlignment = Alignment.Center
        ) {
            if (urlImage.isEmpty()) {
                Icon(
                    Icons.Outlined.Landscape,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(DestinationIconSize)
                )
            } else {
                AsyncImage(
                    model = urlImage,
                    contentDescription = null,
                    error = painterResource(R.drawable.no_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
            }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = Spacing.xxs)
        )
    }
}
