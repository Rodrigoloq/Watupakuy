package com.rodrigoloq.watupakuy.start

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rodrigoloq.watupakuy.R
import com.rodrigoloq.watupakuy.refrofit.entities.information.BestPlace
import com.rodrigoloq.watupakuy.ui.theme.WatupakuyTheme
import java.text.Normalizer
import androidx.core.net.toUri

@Preview()
@Composable
fun BestPlacesCarouselItemPreview(){
    WatupakuyTheme() {
        BestPlacesCarouselItemView(bestPlace = BestPlace(title = "titulo"),
            regionName = "region")
    }
}

@Composable
fun BestPlacesCarouselItemView(modifier: Modifier = Modifier,
                               bestPlace: BestPlace?,
                               regionName: String){
    val context = LocalContext.current

    fun String.removeAccents(): String {
        val normalized = Normalizer.normalize(this, Normalizer.Form.NFD)
        return Regex("\\p{InCombiningDiacriticalMarks}+").replace(normalized, "")
    }

    val placeWebDetail = {
        val regionName = regionName.removeAccents()?.lowercase()
            ?.replace("ñ","n")
            ?.replace(" ", "-")

        val placeName = bestPlace?.title?.removeAccents()?.lowercase()
            ?.replace("ñ","n")
            ?.replace(" ", "-")

        val intent = Intent(Intent.ACTION_VIEW,
            "https://www.ytuqueplanes.com/destinos/${regionName}/${placeName}".toUri()
        )

        context.startActivity(intent)
    }

    Column(modifier.padding(end = 16.dp)) {
        Box(Modifier
            .clip(RoundedCornerShape(8.dp))
            .width(236.dp).height(305.dp)){

            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(bestPlace?.image ?: "")
                .crossfade(true)
                .build(),
                placeholder = painterResource(R.drawable.poster_loading),
                error = painterResource(R.drawable.no_image),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(Modifier.padding(top = 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .width(236.dp)
            .padding(16.dp)){
            Text(bestPlace?.title ?: "titulo",
                color = Color(0xFF145bc8),
                fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(4.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFF14c8b6),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = regionName,
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    )
                }
                Button(
                    onClick = {
                        placeWebDetail()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF14c8b6)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("Ver más", color = Color.Black, fontSize = 12.sp)
                }
            }



        }

    }

}