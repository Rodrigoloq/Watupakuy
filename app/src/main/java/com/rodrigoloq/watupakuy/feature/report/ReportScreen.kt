package com.rodrigoloq.watupakuy.feature.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rodrigoloq.watupakuy.R
import com.rodrigoloq.watupakuy.feature.report.ui.OccupancyCard
import com.rodrigoloq.watupakuy.feature.report.ui.TourismCard
import com.rodrigoloq.watupakuy.feature.report.ui.WeatherCard
import com.rodrigoloq.watupakuy.feature.report.viewmodel.ReportUIState
import com.rodrigoloq.watupakuy.feature.report.viewmodel.ReportViewModel
import com.rodrigoloq.watupakuy.ui.common.SectionError
import com.rodrigoloq.watupakuy.ui.common.SectionSkeleton
import com.rodrigoloq.watupakuy.ui.theme.Spacing
import com.rodrigoloq.watupakuy.ui.theme.WatupakuyTheme
import com.rodrigoloq.watupakuy.utils.PlaceCategoryGroup
import com.rodrigoloq.watupakuy.utils.PlaceCategoryGroups
import com.rodrigoloq.watupakuy.utils.RegionMapper
import com.rodrigoloq.watupakuy.utils.toSpanishTitle
import java.time.YearMonth

@Preview(showBackground = true)
@Composable
fun ReportPreview(){
    WatupakuyTheme {
        Surface {
            ReportViewContent(
                state = ReportUIState(),
                modifier = Modifier,
                navController = rememberNavController(),
                region = "Cusco",
                yearMonth = "23/10/2026",
                getOccupancyReport = { department, date -> },
                getTourismReport = { department, date ->  },
                getWeatherReport = { department, yearMonth -> },
                getInitialNearbyPlacesReport = { lat, lon, department, group -> },
                getMenuNearbyPlacesReport = { lat, lon, department, group -> }
            )
        }
    }
}

@Composable
fun ReportViewContent(state: ReportUIState,
                      modifier: Modifier,
                      navController: NavController,
                      region: String,
                      yearMonth: String,
                      getOccupancyReport: (String, String) -> Unit,
                      getTourismReport: (String, String) -> Unit,
                      getWeatherReport: (String, String) -> Unit,
                      getInitialNearbyPlacesReport: (Double, Double,
                                                     String, PlaceCategoryGroup) -> Unit,
                      getMenuNearbyPlacesReport: (Double, Double,
                                                  String, PlaceCategoryGroup ) -> Unit){


    LaunchedEffect(Unit) {
        getOccupancyReport(region, "$yearMonth-01")
        getTourismReport(region, yearMonth)
        getWeatherReport(region, yearMonth)
        getInitialNearbyPlacesReport(
            RegionMapper.getLatitudeFromRegion(region),
            RegionMapper.getLongitudeFromRegion(region),
            region,
            PlaceCategoryGroups.first()
        )
    }

    val tourism = state.tourism
    val weather = state.weather
    val occupancy = state.occupancy
    val nearbyPlaces = state.nearbyPlaces

    Box(modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.background_app),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        LazyColumn(modifier = Modifier
            .widthIn(max = Spacing.maxContentWidth)
            .fillMaxSize(),
            contentPadding = WindowInsets.safeDrawing.asPaddingValues().plus(
                PaddingValues(horizontal = Spacing.l, vertical = Spacing.l)
            ),
            verticalArrangement = Arrangement.spacedBy(Spacing.l)) {
            item { ReportHeader(regionName = region,
                month = YearMonth.parse(yearMonth), onBack = {navController.popBackStack()}) }
            item {
                if (state.isTourismLoading){
                    SectionSkeleton()
                } else {
                    if (tourism != null){
                        TourismCard(tourism = tourism)
                    } else {
                        SectionError(message = "Error al cargar: ${state.tourismErrorMessage}",
                            retryLabel = "Volver a cargar",
                            onRetry = {getTourismReport(region, yearMonth)})
                    }
                }
            }
            item {
                if (state.isWeatherLoading){
                    SectionSkeleton()
                } else {
                    if (weather != null){
                        WeatherCard(weather = weather,
                            yearMonth = YearMonth.parse(yearMonth))
                    } else {
                        SectionError(message = "Error al cargar: ${state.weatherErrorMessage}",
                            retryLabel = "Volver a cargar",
                            onRetry = {getWeatherReport(region, yearMonth)})
                    }
                }
            }
            item {
                if (state.isOccupancyLoading){
                    SectionSkeleton()
                } else {
                    if(occupancy != null){
                        OccupancyCard(occupancy )
                    } else {
                        SectionError(message = "Error al cargar: ${state.occupancyErrorMessage}",
                            retryLabel = "Volver a cargar",
                            onRetry = {getOccupancyReport(region, "$yearMonth-01")})
                    }
                }

            }
            item {
                if (state.isInitialNearbyPlacesLoading){
                    SectionSkeleton()
                } else {
                    if (nearbyPlaces != null){
                        NearbyPlacesCard(region = region,
                            nearbyPlaces = nearbyPlaces,
                            isLoading = state.isMenuNearbyPlacesLoading) { latLong, group ->
                            getMenuNearbyPlacesReport(latLong.latitude, latLong.longitude, region, group)
                        }
                    } else {
                        SectionError(message = "Error al cargar: ${state.nearbyPlacesErrorMessage}",
                            retryLabel = "Volver a cargar",
                            onRetry = {getInitialNearbyPlacesReport(RegionMapper.getLatitudeFromRegion(region),
                                RegionMapper.getLongitudeFromRegion(region),
                                region,
                                PlaceCategoryGroups.first())})
                    }
                }
            }
        }
    }

}

@Composable
fun ReportView(modifier: Modifier = Modifier,
               navController: NavController,
               region: String,
               yearMonth: String,
               viewModel: ReportViewModel = hiltViewModel()){

    val state by viewModel.uiState.collectAsState()

    ReportViewContent(
        state = state,
        modifier = modifier,
        navController = navController,
        region = region,
        yearMonth = yearMonth,
        getOccupancyReport = { department, date ->
            viewModel.getOccupancyReport(department, date)
        },
        getTourismReport = { department, date ->
            viewModel.getTourismReport(department, date)
        },
        getWeatherReport = { department, yearMonth ->
            viewModel.getWeatherReport(department, yearMonth)
        },
        getInitialNearbyPlacesReport = { lat, lon, department, group ->
            viewModel.getInitialNearbyPlacesReport(lat, lon, department, group)
        },
        getMenuNearbyPlacesReport = { lat, lon, department, group ->
            viewModel.getMenuNearbyPlacesReport(lat, lon, department, group)
        }
    )
}

@Composable
fun ReportHeader(regionName: String,
                 month: YearMonth,
                 onBack: () -> Unit){
    val headerActionSize = 48.dp

    val actionColors = IconButtonDefaults.iconButtonColors(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.m)
    ) {
        IconButton(onClick = onBack,
            colors = actionColors,
            modifier = Modifier.size(headerActionSize)) {
            Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = stringResource(R.string.report_change_selection))
        }
        Column(Modifier.weight(1f)) {
            Text(
                text = regionName,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.semantics { heading() }
            )
            Text(
                text = stringResource(R.string.report_month_reference, month.toSpanishTitle()),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun PaddingValues.plus(other: PaddingValues): PaddingValues {
    val direction = LocalLayoutDirection.current
    return PaddingValues(
        start = calculateStartPadding(direction) + other.calculateStartPadding(direction),
        top = calculateTopPadding() + other.calculateTopPadding(),
        end = calculateEndPadding(direction) + other.calculateEndPadding(direction),
        bottom = calculateBottomPadding() + other.calculateBottomPadding()
    )
}