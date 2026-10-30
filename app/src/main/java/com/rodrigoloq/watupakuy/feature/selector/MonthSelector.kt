package com.rodrigoloq.watupakuy.feature.selector

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rodrigoloq.watupakuy.R
import com.rodrigoloq.watupakuy.ui.common.BrandCard
import com.rodrigoloq.watupakuy.ui.common.SectionHeader
import com.rodrigoloq.watupakuy.ui.common.TealGradient
import com.rodrigoloq.watupakuy.ui.theme.Spacing
import com.rodrigoloq.watupakuy.utils.ReportMonthRange
import com.rodrigoloq.watupakuy.utils.monthAbbreviation
import com.rodrigoloq.watupakuy.utils.toSpanishText
import java.time.Clock
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

private const val MONTHS_PER_ROW = 4
private const val DISABLED_ALPHA = 0.38f
private val MonthCellMinHeight = 52.dp
private val MonthCellBorder = 1.dp

fun monthCellTag(month: YearMonth) = "month_cell_$month"


class ReportMonthPolicy {

    fun today(): LocalDate = LocalDate.now(Clock.systemDefaultZone())

    fun latestAllowedDate(): LocalDate = today().plusMonths(MAX_MONTHS_AHEAD)

    fun allowedRange(): ReportMonthRange =
        ReportMonthRange(first = YearMonth.from(today()), last = YearMonth.from(latestAllowedDate()))

    companion object {
        const val MAX_MONTHS_AHEAD = 4L
    }
}

@Composable
fun MonthSelector(
    onMonthSelected: (YearMonth) -> Unit,
    modifier: Modifier = Modifier
) {
    val range: ReportMonthRange = ReportMonthPolicy().allowedRange()

    var displayedYear by rememberSaveable(range) { mutableIntStateOf(range.first.year) }

    var selectedMonth by remember { mutableStateOf(range.first) }

    BrandCard(modifier) {
        SectionHeader(
            title = stringResource(R.string.month_selector_title),
            icon = Icons.Outlined.CalendarMonth,
            gradient = TealGradient
        )
        YearNavigator(
            year = displayedYear,
            canGoBack = displayedYear > range.years.first,
            canGoForward = displayedYear < range.years.last,
            onYearChange = { displayedYear = it }
        )
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s)) {
            Month.entries.chunked(MONTHS_PER_ROW).forEach { rowMonths ->
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s)) {
                    rowMonths.forEach { month ->
                        val yearMonth = YearMonth.of(displayedYear, month)
                        MonthCell(
                            month = yearMonth,
                            enabled = yearMonth in range,
                            selected = yearMonth == selectedMonth,
                            onClick = { onMonthSelected(yearMonth)
                                      selectedMonth = yearMonth},
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s)) {
            Icon(
                Icons.Outlined.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                Text(
                    text = stringResource(
                        R.string.month_selector_range,
                        range.first.toSpanishText(),
                        range.last.toSpanishText()
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = stringResource(R.string.month_selector_monthly_note),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun YearNavigator(
    year: Int,
    canGoBack: Boolean,
    canGoForward: Boolean,
    onYearChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { onYearChange(year - 1) }, enabled = canGoBack) {
            Icon(
                Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.month_selector_previous_year)
            )
        }
        Text(
            text = year.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        IconButton(onClick = { onYearChange(year + 1) }, enabled = canGoForward) {
            Icon(
                Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = stringResource(R.string.month_selector_next_year)
            )
        }
    }
}

@Composable
private fun MonthCell(
    month: YearMonth,
    enabled: Boolean,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val shape = MaterialTheme.shapes.small
    val background = when {
        selected -> colors.primary
        enabled -> colors.primaryContainer
        else -> Color.Transparent
    }
    val content = when {
        selected -> colors.onPrimary
        enabled -> colors.primary
        else -> colors.onSurfaceVariant.copy(alpha = DISABLED_ALPHA)
    }

    Box(
        modifier = modifier
            .heightIn(min = MonthCellMinHeight)
            .clip(shape)
            .background(background)
            .border(MonthCellBorder, if (enabled || selected) Color.Transparent else colors.outline, shape)
            .selectable(selected = selected, enabled = enabled, role = Role.RadioButton, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = month.monthAbbreviation(),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.SemiBold,
            color = content,
            textAlign = TextAlign.Center
        )
    }
}
