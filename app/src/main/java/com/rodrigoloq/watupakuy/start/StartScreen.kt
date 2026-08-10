package com.rodrigoloq.watupakuy.start

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rodrigoloq.watupakuy.R
import com.rodrigoloq.watupakuy.refrofit.entities.information.InformationResponse
import com.rodrigoloq.watupakuy.refrofit.entities.occupation.OccupationResponse
import com.rodrigoloq.watupakuy.refrofit.entities.usefulplaces.UsefulPlacesResponse
import com.rodrigoloq.watupakuy.refrofit.entities.weather.WeatherResponse
import com.rodrigoloq.watupakuy.start.viewmodel.StartViewModel
import com.rodrigoloq.watupakuy.ui.theme.WatupakuyTheme
import com.rodrigoloq.watupakuy.utils.CategoryMapper
import com.rodrigoloq.watupakuy.utils.RegionMapper
import com.rodrigoloq.watupakuy.utils.WeatherForecastItem
import java.text.Normalizer

@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomePreview() {
    WatupakuyTheme() {
        Surface() {
            StartView()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartView(modifier: Modifier = Modifier, viewModel: StartViewModel = viewModel()) {

    val weatherInformation = viewModel.weatherData
    val usefulPlacesInformation = viewModel.usefulPlacesData
    val occupationInformation = viewModel.occupationData
    val regionInformation = viewModel.InformationData
    val isLoading = viewModel.isLoading
    val loadingMessage = viewModel.loadingMessage

    val regions = listOf(
        "Amazonas", "Áncash", "Apurímac", "Arequipa", "Ayacucho",
        "Cajamarca", "Callao", "Cusco", "Huancavelica", "Huánuco", "Ica", "Junín", "La Libertad",
        "Lambayeque", "Lima", "Loreto", "Madre de Dios", "Moquegua", "Pasco", "Piura", "Puno",
        "San Martín", "Tacna", "Tumbes", "Ucayali"
    )

    var expanded by remember { mutableStateOf(false) }
    var regionName by remember { mutableStateOf("Region") }
    var selectedOption by remember { mutableStateOf("")}
    var selectedUsefulPlacesCategory by remember { mutableStateOf("accommodation") }

    fun String.removeAccents(): String {
        val normalized = Normalizer.normalize(this, Normalizer.Form.NFD)
        return Regex("\\p{InCombiningDiacriticalMarks}+")
            .replace(normalized, "")
    }

    val getAllData = {
        RegionMapper.getCoordinatesFromRegion(regionName){latitude, longitude ->
            viewModel.getAllData(selectedUsefulPlacesCategory.lowercase(),
                latitude, longitude, regionName.removeAccents())
        }
    }

    Box(modifier.fillMaxSize()) {
        Image(
            painterResource(R.drawable.background_app),
            contentDescription = "Imagen de Fondo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)) {
            Image(
                modifier = Modifier.padding(vertical = 14.dp),
                painter = painterResource(R.drawable.logo_watupakuy),
                contentDescription = ""
            )
            if(regionName == "Region") Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Seleccione una region",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = Color.White
            )
            Box(
                Modifier
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = Color.White)
                    .clickable {
                        expanded = true
                    }) {
                Column() {
                    Row(
                        Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_cities),
                            "",
                            tint = Color.Gray,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text(regionName, color = Color.Gray)
                        Column(Modifier.fillMaxWidth()) {
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .clickable {
                                        expanded = !expanded
                                    },
                                painter = painterResource(
                                    if (expanded) {
                                        R.drawable.ic_arrow_cities_up
                                    } else {
                                        R.drawable.ic_arrow_cities_down
                                    }
                                ),
                                contentDescription = "",
                                tint = Color.Gray
                            )
                        }
                    }
                    if (expanded) {
                        DropDownList(regions) { region, isExpanded ->
                            regionName = region
                            expanded = isExpanded
                            selectedOption = "MainMenu"
                            getAllData()
                        }
                    }
                }
            }
            if(selectedOption == "MainMenu" || selectedOption == ""){
                Box() { }
            } else {
                Row(modifier = Modifier.clickable{
                    selectedOption = "MainMenu"
                }) {
                    Icon(Icons.Default.ArrowBackIosNew, "")
                    Text(
                        text = "Volver al menu principal",
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                        color = Color.White)
                }
            }
            if (selectedOption != ""){
                when(selectedOption){
                    "Pronóstico meteorológico" -> {
                        WeatherForecast(weatherResponse = weatherInformation)
                    }
                    "Pronóstico de ocupabilidad hotelera" -> {
                        OccupationForecast(occupationResponse = occupationInformation)
                    }
                    "Información de lugares útiles" -> {
                        UsefulPlacesInformation(usefulPlacesResponse = usefulPlacesInformation,
                            isLoading = isLoading){category ->
                            selectedUsefulPlacesCategory = category

                            RegionMapper.getCoordinatesFromRegion(regionName){latitude, longitude ->
                                viewModel.getUsefulPlaces(category.lowercase(),
                                    latitude,
                                    longitude)
                            }
                        }
                    }
                    "Información turística" -> {
                        RegionInformation(informationResponse = regionInformation,
                            regionName = regionName)
                    }
                    "MainMenu" -> {
                        if (isLoading){
                            LoadingOverlay(loadingMessage)
                        } else {
                            StartMenuView(modifier = Modifier.align(Alignment.CenterHorizontally)){ option ->
                                selectedOption = option
                            }
                        }
                    }
                }
            } else {
                Box() { }
            }
        }
    }
}

@Composable
fun DropDownList(regions: List<String>, onSelected:(String, Boolean) -> Unit){
    LazyColumn(Modifier.fillMaxSize()) {
        items(regions.size) { i ->
            val region = regions[i]
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
                    .clickable {
                        onSelected(region, false)
                    }) {
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = Color.Gray, text = region
                )
            }
        }
    }
}

@Composable
fun WeatherForecast(modifier: Modifier = Modifier, weatherResponse: WeatherResponse?){

    var listSize = weatherResponse!!.monthly.time.size

    if (weatherResponse.monthly.temperature_2m_mean.lastOrNull() == null) {
        listSize = weatherResponse.monthly.time.size - 1
    }

    Column(modifier.fillMaxSize()) {
        Text(text = "Pronóstico meteorológico",
            fontSize = 50.sp,
            lineHeight = 50.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold)
        LazyRow(Modifier
            .fillMaxHeight()
            .padding(top = 8.dp)) {
            items(listSize){i ->
                val weatherItem = WeatherForecastItem(
                    date = weatherResponse.monthly.time[i],
                    temperature_2m_mean = weatherResponse.monthly.temperature_2m_mean[i],
                    temperature_max24h_2m_mean = weatherResponse.monthly.temperature_max24h_2m_mean[i],
                    temperature_min24h_2m_mean = weatherResponse.monthly.temperature_min24h_2m_mean[i],
                    showers_mean = weatherResponse.monthly.showers_mean[i],
                    cloud_cover_mean = weatherResponse.monthly.cloud_cover_mean[i],
                    shortwave_radiation_mean = weatherResponse.monthly.shortwave_radiation_mean[i],
                    pressure_msl_mean = weatherResponse.monthly.pressure_msl_mean[i],
                    wind_speed_10m_mean = weatherResponse.monthly.wind_speed_10m_mean[i],
                    sunshine_duration_mean = weatherResponse.monthly.sunshine_duration_mean[i],
                    dew_point_2m_mean = weatherResponse.monthly.dew_point_2m_mean[i],
                    snowfall_mean = weatherResponse.monthly.snowfall_mean[i],
                    monthlyUnits = weatherResponse.monthly_units,
                    time = weatherResponse.monthly.time[i]
                )
                WeatherCarouselItemView(Modifier.padding(end = 8.dp),weatherItem)
            }
        }
    }
}


@Composable
fun OccupationForecast(modifier: Modifier = Modifier,
                       occupationResponse: List<OccupationResponse?>){
    Column(modifier.fillMaxWidth()) {
        Text(text = "Pronóstico de ocupabilidad hotelera",
            fontSize = 50.sp,
            lineHeight = 50.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold)
        LazyRow(Modifier
            .fillMaxHeight()
            .padding(top = 8.dp)) {
            items(occupationResponse.size){i ->
                val occupationItem = occupationResponse[i]
                OccupationCarouselItemView(Modifier.padding(end = 8.dp), occupationItem)
            }
        }
    }
}

@Composable
fun UsefulPlacesInformation(modifier: Modifier = Modifier,
                            usefulPlacesResponse: UsefulPlacesResponse?,
                            isLoading: Boolean,
                            onSelectedCategory:(String) -> Unit){
    val placesCategory = listOf("Alojamiento", "Natural", "Transporte",
        "Centro comercial", "Supermercado", "Entretenimiento", "Sanidad", "Bancos", "Restaurantes")
    var selectedCategory by remember { mutableStateOf(placesCategory[0]) }

    Column(modifier.fillMaxWidth()) {
        Text(text = "Información de lugares útiles",
            fontSize = 50.sp,
            lineHeight = 50.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            placesCategory.forEach {category ->
                val categoryName = CategoryMapper.getCategoryName(category)
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = {
                        selectedCategory = category
                        onSelectedCategory(categoryName)
                    },
                    label = {Text(category)},
                    colors = FilterChipDefaults.filterChipColors(
                        labelColor = Color.DarkGray,
                        selectedLabelColor = Color.White
                    ),
                    leadingIcon = if (selectedCategory == category){
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Seleccionado",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else null
                )
            }
        }
        if (isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 4.dp
                )
            }
        } else {
            LazyColumn(Modifier
                .fillMaxSize()
                .padding(top = 8.dp)) {
                items(usefulPlacesResponse!!.features.size){ i ->
                    val usefulPlaceItem = usefulPlacesResponse.features[i]
                    UsefulPlacesListItemView(Modifier.padding(bottom = 8.dp), usefulPlaceItem)
                }
            }
        }
    }
}

@Composable
fun RegionInformation(modifier: Modifier = Modifier,
                      regionName: String,
                      informationResponse: InformationResponse?){
    var maxLines = Int.MAX_VALUE

    val region = informationResponse?.regions?.find {
        it.title == regionName
    }

    Column(modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        Text(text = region?.title ?: "Region",
            fontSize = 50.sp,
            lineHeight = 50.sp,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold)
        Text(text = region?.description ?: "Description",
            maxLines = maxLines,
            color = Color.Black,
            fontWeight = FontWeight.Light,
            modifier = Modifier.clickable{
                if (maxLines == Int.MAX_VALUE) maxLines = 3 else maxLines = Int.MAX_VALUE
            }.padding(vertical = 4.dp))

        LazyRow(Modifier.fillMaxHeight()) {
            items(region?.best_places?.size ?: 1){i ->
                val bestPlace = region?.best_places[i]

                BestPlacesCarouselItemView(bestPlace = bestPlace,
                    regionName = region?.title ?: "Region")
            }
        }
    }
}

@Composable
fun LoadingOverlay(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.9f)
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    color = Color(0xFF4A90E2),
                    strokeWidth = 3.dp
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }
    }
}