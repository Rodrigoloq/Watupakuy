package com.rodrigoloq.watupakuy.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rodrigoloq.watupakuy.R
import com.rodrigoloq.watupakuy.ui.theme.WatupakuyTheme
import com.rodrigoloq.watupakuy.utils.DateFormatter
import com.rodrigoloq.watupakuy.utils.WeatherForecastItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Preview
@Composable
fun WeatherCarouselItemPreview(){
    WatupakuyTheme() {
        Surface() {
            WeatherCarouselItemView(weatherForecastItem = WeatherForecastItem())
        }
    }
}


@Composable
fun WeatherCarouselItemView(modifier: Modifier = Modifier,
                            weatherForecastItem: WeatherForecastItem){

    val month = DateFormatter.dateFormatter(weatherForecastItem.time)

    Box(modifier
        .clip(RoundedCornerShape(8.dp))
        .background(Color.White.copy(alpha = 0.9f))) {
        Column(Modifier.padding(8.dp).width(intrinsicSize = IntrinsicSize.Max)) {
            Box(Modifier.fillMaxWidth()) {
                Text(month!!, fontSize = 25.sp, color = Color.Black)
            }
            Text("${weatherForecastItem.temperature_2m_mean}º", fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp))
            Row() {
                Icon(painterResource(R.drawable.ic_arrow_downward),
                    "",
                    tint = Color.Black)
                Text("${weatherForecastItem.temperature_min24h_2m_mean}º/", color = Color.Black, fontWeight = FontWeight.Medium)
                Icon(painterResource(R.drawable.ic_arrow_upward),
                    "",
                    tint = Color.Black)
                Text("${weatherForecastItem.temperature_max24h_2m_mean}º", color = Color.Black, fontWeight = FontWeight.Medium)
            }
            Column() {
                Row() {
                    WeatherVariableBox(R.drawable.ic_cloud_cover,
                        "Nubes",
                        "${weatherForecastItem.cloud_cover_mean} " +
                                weatherForecastItem.monthlyUnits.cloud_cover_mean
                    )
                    WeatherVariableBox(R.drawable.ic_showers,
                        "Lluvias",
                        "${weatherForecastItem.showers_mean} " +
                                weatherForecastItem.monthlyUnits.showers_mean
                    )
                    WeatherVariableBox(R.drawable.ic_wind_speed,
                        "Viento",
                        "${weatherForecastItem.wind_speed_10m_mean} " +
                                weatherForecastItem.monthlyUnits.wind_speed_10m_mean
                    )
                }
                Row() {
                    WeatherVariableBox(R.drawable.ic_radiation,
                        "Nieve",
                        "${weatherForecastItem.snowfall_mean} " +
                                weatherForecastItem.monthlyUnits.snowfall_mean
                    )
                    WeatherVariableBox(R.drawable.ic_sunshine,
                        "Luz solar",
                        "${weatherForecastItem.sunshine_duration_mean.toInt() / 3600} hrs"
                    )
                    WeatherVariableBox(R.drawable.ic_pressure,
                        "Presion",
                        "${weatherForecastItem.pressure_msl_mean} hPa"
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherVariableBox(icon: Int,
                       variableName: String,
                       variableValue: String){
    Box(Modifier.padding(2.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(color = colorResource(R.color.background_variable_box))
        .width(105.dp).height(100.dp)) {
        Column(Modifier.padding(8.dp)) {
            Image(painterResource(icon),
                "")
            Text(variableName,
                Modifier.padding(vertical = 8.dp),
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                maxLines = 1)
            Text(variableValue,
                fontWeight = FontWeight.Light,
                color = Color.DarkGray)
        }
    }
}