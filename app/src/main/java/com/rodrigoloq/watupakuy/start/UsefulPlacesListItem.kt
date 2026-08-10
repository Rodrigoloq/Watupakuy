package com.rodrigoloq.watupakuy.start

import android.app.SearchManager
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.rodrigoloq.watupakuy.R
import com.rodrigoloq.watupakuy.refrofit.entities.usefulplaces.Features
import com.rodrigoloq.watupakuy.ui.theme.WatupakuyTheme

@Preview
@Composable
fun UsefulPlacesListItemPreview(){
    WatupakuyTheme() {
        UsefulPlacesListItemView(features = Features())
    }
}

@Composable
fun UsefulPlacesListItemView(modifier: Modifier = Modifier, features: Features){

    val context = LocalContext.current
    val placeProperties = features.properties

    fun placeWebSearch(query: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, query)
        }
        context.startActivity(intent)
    }

    Box(modifier.fillMaxWidth()
        .clip(RoundedCornerShape(8.dp))
        .background(Color.White.copy(alpha = 0.9f))) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            Column(Modifier.weight(1f)) {
                Text(placeProperties.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black)
                Text(placeProperties.address_line2.dropLast(6),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 4.dp),
                    color = Color.Black)
            }
            Icon(painterResource(id = R.drawable.ic_web_search),
                tint = Color.Black,
                contentDescription = "",
                modifier = Modifier.size(50.dp).clickable{
                    placeWebSearch(placeProperties.formatted)
                })
        }

    }
}