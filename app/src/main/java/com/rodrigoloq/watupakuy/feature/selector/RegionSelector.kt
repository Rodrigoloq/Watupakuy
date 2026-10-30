package com.rodrigoloq.watupakuy.feature.selector

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rodrigoloq.watupakuy.R
import com.rodrigoloq.watupakuy.ui.theme.Spacing
import com.rodrigoloq.watupakuy.utils.RegionCoordinates

private val SelectorMaxListHeight = 300.dp
private val SelectorElevation = 6.dp

@Composable
fun RegionSelector(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onRegionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedRegion by remember { mutableStateOf("") }
    val placeholder = stringResource(R.string.region_selector_placeholder)


    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(SelectorElevation, MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(role = Role.Button) { onExpandedChange(!expanded) }
                .padding(Spacing.l),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_cities),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(end = Spacing.s)
            )
            Text(
                text = selectedRegion.ifEmpty { placeholder },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (selectedRegion.isEmpty()) FontWeight.SemiBold else FontWeight.Normal,
                color = if (selectedRegion.isEmpty()) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(
                    if (expanded) R.drawable.ic_arrow_cities_up else R.drawable.ic_arrow_cities_down
                ),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column {
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                RegionList(
                    onRegionSelected = { region ->
                        onRegionSelected(region)
                        selectedRegion = region
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}

@Composable
private fun RegionList(
    onRegionSelected: (String) -> Unit
) {
    val regions = listOf(
        "Amazonas", "Áncash", "Apurímac", "Arequipa", "Ayacucho",
        "Cajamarca", "Callao", "Cusco", "Huancavelica", "Huánuco", "Ica", "Junín", "La Libertad",
        "Lambayeque", "Lima", "Loreto", "Madre de Dios", "Moquegua", "Pasco", "Piura", "Puno",
        "San Martín", "Tacna", "Tumbes", "Ucayali"
    )

    var selectedRegion by remember { mutableStateOf("") }

    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = SelectorMaxListHeight)
    ) {
        items(regions) { region ->
            val isSelected = region == selectedRegion
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = Spacing.minTouchTarget)
                    .selectable(selected = isSelected, role = Role.RadioButton) {
                        selectedRegion = region
                        onRegionSelected(region)
                    }
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                    )
                    .padding(horizontal = Spacing.xl, vertical = Spacing.m),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = region,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
                if (isSelected) {
                    Icon(Icons.Rounded.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
