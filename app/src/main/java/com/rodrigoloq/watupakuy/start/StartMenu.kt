package com.rodrigoloq.watupakuy.start

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rodrigoloq.watupakuy.R
import com.rodrigoloq.watupakuy.ui.theme.WatupakuyTheme

@Preview
@Composable
fun StartMenuPreview() {
    WatupakuyTheme() {
        Surface() {
            StartMenuView() {}
        }
    }
}

@Composable
fun StartMenuView(modifier: Modifier = Modifier, onSelect: (String) -> Unit) {
    Box(modifier.padding(6.dp)) {
        Column() {
            Row(Modifier.weight(0.5f, fill = false)) {
                MenuItemBoxNew(
                    R.drawable.ic_cloud_cover,
                    "Pronóstico meteorológico",
                    Modifier.weight(0.5f)){
                    onSelect("Pronóstico meteorológico")
                }
                MenuItemBoxNew(
                    R.drawable.ic_occupation,
                    "Pronóstico de ocupabilidad hotelera",
                    Modifier.weight(0.5f)){
                    onSelect("Pronóstico de ocupabilidad hotelera")
                }
            }
            Row(Modifier.weight(0.5f, fill = false)) {
                MenuItemBoxNew(
                    R.drawable.ic_places,
                    "Información de lugares útiles",
                    Modifier.weight(0.5f)){
                    onSelect("Información de lugares útiles")
                }
                MenuItemBoxNew(
                    R.drawable.ic_information,
                    "Información turística",
                    Modifier.weight(0.5f)){
                    onSelect("Información turística")
                }
            }
        }
    }
}

@Composable
fun MenuItemBox(
    icon: Int,
    variableName: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .height(180.dp)
            .padding(2.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = colorResource(R.color.background_variable_box).copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painterResource(icon),
                "", Modifier.size(50.dp),
            )
            Text(
                variableName,
                Modifier.padding(vertical = 8.dp),
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
            )
        }
    }
}

@Composable
fun MenuItemBoxNew(
    icon: Int,
    variableName: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        label = "scaleAnim"
    )

    Box(
        modifier = modifier
            .padding(6.dp)
            .height(180.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(10.dp, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE3F2FD),
                        Color(0xFFBBDEFB)
                    )
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = true),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // 🔵 Icono dentro de círculo
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = variableName,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF145bc8),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                lineHeight = 20.sp
            )
        }
    }
}