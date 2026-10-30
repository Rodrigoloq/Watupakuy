package com.rodrigoloq.watupakuy.feature.report.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Opacity
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rodrigoloq.watupakuy.R
import com.rodrigoloq.watupakuy.utils.Weather
import com.rodrigoloq.watupakuy.ui.common.BrandCard
import com.rodrigoloq.watupakuy.ui.common.SectionHeader
import com.rodrigoloq.watupakuy.ui.common.StatusPill
import com.rodrigoloq.watupakuy.ui.theme.Spacing
import com.rodrigoloq.watupakuy.utils.formatInteger
import com.rodrigoloq.watupakuy.utils.formatOneDecimal
import com.rodrigoloq.watupakuy.utils.toSpanishTitle
import java.time.YearMonth

private const val MISSING_VALUE = "—"
private val MetricTileIconSize = 20.dp

@Composable
fun WeatherCard(
    modifier: Modifier = Modifier,
    weather: Weather,
    yearMonth: YearMonth
) {
    val title = stringResource(R.string.section_weather)
    BrandCard(modifier) {
        SectionHeader(title = title, icon = Icons.Outlined.WbSunny)
        WeatherContent(weather, yearMonth)
    }
}

@Composable
private fun WeatherContent(weather: Weather,
                           yearMonth: YearMonth) {

    StatusPill(text = stringResource(R.string.weather_period, yearMonth.toSpanishTitle()))

    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.m)) {
        Icon(Icons.Outlined.Thermostat, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
        Column(Modifier.semantics(mergeDescendants = true) {}) {
            Text(
                text = stringResource(R.string.weather_mean_temperature),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = formatOneDecimal(weather.temperature_2m_mean)
                    ?.let { stringResource(R.string.weather_value_celsius, it) } ?: MISSING_VALUE,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(
                    R.string.weather_max_min,
                    formatOneDecimal(weather.temperature_max24h_2m_mean) ?: MISSING_VALUE,
                    formatOneDecimal(weather.temperature_min24h_2m_mean) ?: MISSING_VALUE
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    MetricRow {
        MetricTile(
            icon = Icons.Outlined.WaterDrop,
            label = stringResource(R.string.weather_rain),
            value = formatInteger(weather.precipitation_mean)?.let { stringResource(R.string.weather_value_mm, it) },
            modifier = Modifier.weight(1f)
        )
        MetricTile(
            icon = Icons.Outlined.Cloud,
            label = stringResource(R.string.weather_clouds),
            value = formatInteger(weather.cloud_cover_mean)?.let { stringResource(R.string.weather_value_percent, it) },
            modifier = Modifier.weight(1f)
        )
    }
    MetricRow {
        MetricTile(
            icon = Icons.Outlined.Air,
            label = stringResource(R.string.weather_wind),
            value = formatOneDecimal(weather.wind_speed_10m_mean)?.let { stringResource(R.string.weather_value_kmh, it) },
            modifier = Modifier.weight(1f)
        )
        MetricTile(
            icon = Icons.Outlined.WbSunny,
            label = stringResource(R.string.weather_sun),
            value = formatInteger(weather.sunshine_duration_mean)?.let { stringResource(R.string.weather_value_hours, it) },
            modifier = Modifier.weight(1f)
        )
    }
    MetricRow {
        MetricTile(
            icon = Icons.Outlined.AcUnit,
            label = stringResource(R.string.weather_snow),
            value = formatOneDecimal(weather.snowfall_mean)?.let { stringResource(R.string.weather_value_cm, it) },
            modifier = Modifier.weight(1f)
        )
        MetricTile(
            icon = Icons.Outlined.Opacity,
            label = stringResource(R.string.weather_dew_point),
            value = formatOneDecimal(weather.dew_point_2m_mean)?.let { stringResource(R.string.weather_value_celsius, it) },
            modifier = Modifier.weight(1f)
        )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s)) {
        Icon(Icons.Outlined.Info, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            text = stringResource(R.string.weather_disclaimer),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    Text(
        text = stringResource(R.string.weather_attribution),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

/** Fila de dos cuadros con la misma altura aunque una etiqueta ocupe dos líneas. */
@Composable
private fun MetricRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(Spacing.s),
        content = content
    )
}

@Composable
private fun MetricTile(icon: ImageVector, label: String, value: String?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(Spacing.m)
            .semantics(mergeDescendants = true) {},
        verticalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(MetricTileIconSize)
            )
            Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        AutoSizeValueText(
            text = value ?: MISSING_VALUE,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AutoSizeValueText(
    text: String,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    val MinValueFontSize = 12.sp
    BasicText(
        text = text,
        modifier = modifier,
        style = style.copy(color = color, textAlign = textAlign),
        maxLines = 1,
        autoSize = TextAutoSize.StepBased(minFontSize = MinValueFontSize, maxFontSize = style.fontSize)
    )
}

