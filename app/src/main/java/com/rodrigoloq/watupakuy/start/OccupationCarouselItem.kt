package com.rodrigoloq.watupakuy.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rodrigoloq.watupakuy.R
import com.rodrigoloq.watupakuy.refrofit.entities.occupation.OccupationResponse
import com.rodrigoloq.watupakuy.ui.theme.WatupakuyTheme
import com.rodrigoloq.watupakuy.utils.DateFormatter

@Preview
@Composable
fun OccupationCarouselItemPreview(){
    WatupakuyTheme() {
        OccupationCarouselItemView(occupationResponse = OccupationResponse())
    }
}

@Composable
fun OccupationCarouselItemView(modifier: Modifier = Modifier, occupationResponse: OccupationResponse?){
    val month = DateFormatter.dateFormatter(occupationResponse!!.fecha)

    Box(modifier
        .clip(RoundedCornerShape(8.dp))
        .background(Color.White.copy(alpha = 0.9f))) {
        BoxWithConstraints(Modifier.width(300.dp)) {
            Image(painter = painterResource(R.drawable.ic_occupation),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterEnd)
                    .size(84.dp).padding(top = 8.dp, end = 8.dp))

            val finalWidth = maxWidth - 84.dp

            Column(Modifier.width(finalWidth).padding(start = 8.dp, top = 8.dp, bottom = 8.dp)) {
                Text(month!!, color = Color.Black, fontSize = 25.sp)
                Text(occupationResponse.ocupacionEstimadaconsolidado.toString() + "%", fontSize = 50.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                Row(modifier = Modifier.padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Hotel: ${occupationResponse.ocupacionEstimadahotel}%", modifier = Modifier.padding(end = 4.dp),
                        color = Color.Black, fontWeight = FontWeight.Medium)
                    CustomProgressBar(
                        Modifier
                            .height(14.dp)
                            .clip(shape = RoundedCornerShape(4.dp)),
                        Color.LightGray,
                        Color(0xff3990f2),
                        50
                    )
                }
                Row(modifier = Modifier.padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Hostal: ${occupationResponse.ocupacionEstimadahostal}%", modifier = Modifier.padding(end = 4.dp),
                        color = Color.Black, fontWeight = FontWeight.Medium)
                    CustomProgressBar(
                        Modifier
                            .height(14.dp)
                            .clip(shape = RoundedCornerShape(4.dp)),
                        Color.LightGray,
                        Color(0xff3990f2),
                        50
                    )
                }
                Row(modifier = Modifier.padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Otros: ${occupationResponse.ocupacionEstimadaotros}%", modifier = Modifier.padding(end = 4.dp),
                        color = Color.Black, fontWeight = FontWeight.Medium)
                    CustomProgressBar(
                        Modifier
                            .height(14.dp)
                            .clip(shape = RoundedCornerShape(4.dp)),
                        Color.LightGray,
                        Color(0xff3990f2),
                        50
                    )
                }
            }
        }
    }
}

@Composable
fun CustomProgressBar(
    modifier: Modifier,
    backgroundColor: Color,
    foregroundColor: Color,
    percent: Int
) {
    BoxWithConstraints(
        modifier = modifier
            .background(backgroundColor).fillMaxWidth()
    ) {
        Box(
            modifier = modifier
                .background(foregroundColor)
                .width(maxWidth * percent / 100)
        )
    }
}