package com.rodrigoloq.watupakuy.feature.report.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Hotel
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rodrigoloq.watupakuy.R
import com.rodrigoloq.watupakuy.utils.Occupancy
import com.rodrigoloq.watupakuy.ui.common.BrandCard
import com.rodrigoloq.watupakuy.ui.common.SectionHeader
import com.rodrigoloq.watupakuy.ui.common.StatusPill
import com.rodrigoloq.watupakuy.ui.common.TealGradient
import com.rodrigoloq.watupakuy.ui.theme.Spacing
import com.rodrigoloq.watupakuy.ui.theme.WatupakuySkySoft
import com.rodrigoloq.watupakuy.ui.theme.WatupakuyWarning
import com.rodrigoloq.watupakuy.ui.theme.WatupakuyWarningContainer
import com.rodrigoloq.watupakuy.utils.formatOneDecimal
import com.rodrigoloq.watupakuy.utils.toSpanishText
import com.rodrigoloq.watupakuy.utils.toSpanishTitle

private const val MAX_PERCENT = 100f
private const val MISSING_VALUE = "—"
private val TotalRingSize = 104.dp
private val TotalRingStroke = 10.dp
private val ClassBarHeight = 8.dp

@Composable
fun OccupancyCard(
    occupancy: Occupancy,
    modifier: Modifier = Modifier
) {
    val title = stringResource(R.string.section_occupancy)
    BrandCard(modifier) {
        SectionHeader(title = title, icon = Icons.Outlined.Hotel, gradient = TealGradient)
        OccupancyContent(occupancy)
    }
}

@Composable
private fun OccupancyContent(occupancy: Occupancy) {
    StatusPill(text = occupancy.date.toSpanishTitle())

    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(
        Spacing.l)) {
        Box(
            contentAlignment = Alignment.Center,
            // Tamaño fijo: el porcentaje se ajusta dentro del anillo en lugar de desbordarlo.
            modifier = Modifier
                .size(TotalRingSize)
                .semantics(mergeDescendants = true) {}
        ) {
            CircularProgressIndicator(
                progress = { (occupancy.occupancyPercentage.toFloat() / MAX_PERCENT).coerceIn(0f, 1f) },
                modifier = Modifier.size(TotalRingSize),
                strokeWidth = TotalRingStroke,
                color = MaterialTheme.colorScheme.secondary,
                trackColor = WatupakuySkySoft,
                strokeCap = StrokeCap.Round
            )
            // Dentro del anillo: margen para no tocar el trazo aunque el texto sea grande.
            AutoSizeValueText(
                text = stringResource(R.string.occupancy_percent, formatOneDecimal(occupancy.occupancyPercentage) ?: MISSING_VALUE),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = TotalRingStroke + Spacing.xs)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
            Text(
                text = stringResource(R.string.occupancy_total),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (occupancy.clampedToRange) ClampedPill()
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(Spacing.m)) {
        ClassRow(stringResource(R.string.occupancy_hotel), occupancy.occupancyPercentageHotel)
        ClassRow(stringResource(R.string.occupancy_hostal), occupancy.occupancyPercentageHostal)
        ClassRow(stringResource(R.string.occupancy_uncategorized), occupancy.occupancyPercentageUnclassified)
    }

    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s)) {
        Icon(Icons.Outlined.Info, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
            Text(
                text = stringResource(R.string.occupancy_note),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(R.string.occupancy_based_on,
                    occupancy.lastObservedDate.toSpanishText()),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ClassRow(label: String, percent: Double?) {
    Column(
        modifier = Modifier.semantics(mergeDescendants = true) {},
        verticalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
            Text(
                text = formatOneDecimal(percent)?.let { stringResource(R.string.occupancy_percent, it) } ?: MISSING_VALUE,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
        LinearProgressIndicator(
            progress = { ((percent ?: 0.0).toFloat() / MAX_PERCENT).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(ClassBarHeight),
            color = MaterialTheme.colorScheme.primary,
            trackColor = WatupakuySkySoft,
            strokeCap = StrokeCap.Round,
            drawStopIndicator = {}
        )
    }
}

@Composable
private fun ClampedPill() {
    StatusPill(
        text = stringResource(R.string.occupancy_clamped),
        icon = Icons.Outlined.Tune,
        containerColor = WatupakuyWarningContainer,
        contentColor = WatupakuyWarning
    )
}
