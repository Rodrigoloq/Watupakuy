package com.rodrigoloq.watupakuy.feature.report

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Hotel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.rodrigoloq.watupakuy.R
import com.rodrigoloq.watupakuy.utils.NearbyPlaces
import com.rodrigoloq.watupakuy.ui.common.BrandCard
import com.rodrigoloq.watupakuy.ui.common.SectionEmpty
import com.rodrigoloq.watupakuy.ui.common.SectionHeader
import com.rodrigoloq.watupakuy.ui.common.StatusPill
import com.rodrigoloq.watupakuy.ui.common.TealGradient
import com.rodrigoloq.watupakuy.ui.theme.Spacing
import com.rodrigoloq.watupakuy.utils.PlaceCategoryGroup
import com.rodrigoloq.watupakuy.utils.PlaceCategoryGroups
import com.rodrigoloq.watupakuy.utils.RegionMapper
import com.rodrigoloq.watupakuy.utils.formatDistance


private const val COLLAPSED_PLACES_COUNT = 5
private val PlaceIconContainerSize = 36.dp
private val PlaceIconSize = 20.dp
private val PlaceRowMinHeight = 56.dp

@Composable
fun NearbyPlacesCard(modifier: Modifier = Modifier,
                     region: String,
                     nearbyPlaces: List<NearbyPlaces>,
                     isLoading: Boolean,
                     onLocationSelect: (LatLng, PlaceCategoryGroup) -> Unit){
    val defaultLocation = LatLng(RegionMapper.getLatitudeFromRegion(region),
        RegionMapper.getLongitudeFromRegion(region))

    var selectedLocation by remember { mutableStateOf(defaultLocation) }
    var selectedGroup by remember { mutableStateOf(PlaceCategoryGroups.first()) }

    val title = stringResource(R.string.section_places)
    BrandCard(modifier) {
        SectionHeader(title = title, icon = Icons.Outlined.Hotel, gradient = TealGradient)
        LocationSelector(defaultLocation = defaultLocation) {
            onLocationSelect(it, selectedGroup)}
        if (nearbyPlaces.isEmpty()) {
            SectionEmpty(stringResource(R.string.places_empty))
        } else {
            PlacesContent(nearbyPlaces, isLoading){
                selectedGroup = it
                onLocationSelect(selectedLocation, it)
            }
        }
        Text(
            text = stringResource(R.string.places_attribution),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

    }
}

@Composable
private fun PlacesContent(nearbyPlaces: List<NearbyPlaces>,
                          isLoading: Boolean,
                          onSelectGroup: (PlaceCategoryGroup) -> Unit) {
    val groups = PlaceCategoryGroups
    var selectedGroupName by rememberSaveable {
        mutableStateOf((groups.first().name))
    }
    var showAll by rememberSaveable(selectedGroupName) { mutableStateOf(false) }
    val selectedGroup = groups.find {
        it.name == selectedGroupName
    }

    LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.s)) {
        items(groups.size) { i ->
            val group = groups[i]
            FilterChip(
                selected = group == selectedGroup,
                onClick = { selectedGroupName = group.name
                          onSelectGroup(group)},
                label = { Text(group.label) },
                leadingIcon = { Icon(group.icon, contentDescription = null, modifier = Modifier.size(18.dp)) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
    if (isLoading){
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {CircularProgressIndicator() }

    } else {
        if (nearbyPlaces.isEmpty()) {
            SectionEmpty(stringResource(R.string.places_group_empty))
        } else {
            val visible = if (showAll) nearbyPlaces else nearbyPlaces.take(COLLAPSED_PLACES_COUNT)
            Column(Modifier.animateContentSize()) {
                visible.forEachIndexed { index, place ->
                    if (index > 0) HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    PlaceRow(place, selectedGroup!!.icon)
                }
            }
            if (nearbyPlaces.size > COLLAPSED_PLACES_COUNT) {
                TextButton(onClick = { showAll = !showAll }) {
                    Text(
                        if (showAll) {
                            stringResource(R.string.places_show_less)
                        } else {
                            stringResource(R.string.places_show_all, nearbyPlaces.size)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun PlaceRow(place: NearbyPlaces, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = PlaceRowMinHeight)
            .padding(vertical = Spacing.s)
            .semantics(mergeDescendants = true) {},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.m)
    ) {
        Box(
            modifier = Modifier
                .size(PlaceIconContainerSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(PlaceIconSize))
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Spacing.xxs)
        ) {
            Text(
                text = place.name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            if (place.address != null) {
                Text(
                    text = place.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (place.distanceMeters != null) {
            StatusPill(text = formatDistance(place.distanceMeters))
        }
    }
}

@Composable
fun LocationSelector(
    defaultLocation: LatLng,
    onLocationSelect: (LatLng) -> Unit
) {
    val ubicacionInicial = defaultLocation

    var puntoSeleccionado by remember {
        mutableStateOf<LatLng?>(defaultLocation)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            ubicacionInicial,
            12f
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Selecciona una ubicación",
            style = MaterialTheme.typography.titleLarge
        )

        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            cameraPositionState = cameraPositionState,
            onMapClick = { coordenadas ->
                puntoSeleccionado = coordenadas
                onLocationSelect(coordenadas)
            }
        ) {
            puntoSeleccionado?.let { punto ->
                Marker(
                    state = rememberMarkerState(
                        position = punto
                    ),
                    title = "Punto seleccionado",
                    snippet = "Ubicación elegida"
                )
            }
        }
    }
}
