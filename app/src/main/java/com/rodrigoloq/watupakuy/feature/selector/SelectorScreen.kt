package com.rodrigoloq.watupakuy.feature.selector

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rodrigoloq.watupakuy.R
import com.rodrigoloq.watupakuy.ui.theme.Spacing
import com.rodrigoloq.watupakuy.ui.theme.WatupakuyTheme
import com.rodrigoloq.watupakuy.utils.RegionCoordinates
import java.time.LocalDate
import java.time.YearMonth

@Preview(showBackground = true)
@Composable
fun SelectorPreview(){
    WatupakuyTheme() {
        Surface() {
            SelectorView(navController = rememberNavController())
        }
    }
}

@Composable
fun SelectorView(modifier: Modifier = Modifier,
                 navController: NavController){
    var regionListExpanded by rememberSaveable { mutableStateOf(false) }
    var regionSelected by remember { mutableStateOf("")}
    var yearMonthSelected by remember { mutableStateOf<YearMonth?>(null) }
    val canViewReport = (regionSelected.isNotEmpty() &&
        yearMonthSelected != null)

    Box(modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.background_app),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .widthIn(max = Spacing.maxContentWidth)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.l, vertical = Spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.l)
        ) {
            Image(
                painter = painterResource(R.drawable.logo_watupakuy),
                contentDescription = stringResource(R.string.logo_content_description),
                contentScale = ContentScale.Fit,
                modifier = modifier.fillMaxWidth()
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.selection_title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(Spacing.xs))
                Text(
                    text = stringResource(R.string.selection_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
            RegionSelector(
                expanded = regionListExpanded,
                onExpandedChange = { regionListExpanded = it },
                onRegionSelected = { regionSelected = it })
            AnimatedVisibility(
                visible = regionSelected.isNotBlank() && !regionListExpanded,
                enter = expandVertically() + fadeIn()
            ) {
                MonthSelector(
                    onMonthSelected = {
                        yearMonthSelected = it
                    }
                )
            }
            ViewReportButton(
                enabled = canViewReport,
                hint = when{
                    regionSelected.isEmpty() -> stringResource(R.string.view_report_hint_region)
                    yearMonthSelected == null -> stringResource(R.string.view_report_hint_month)
                    else -> null
                }
            ) { navController.navigate("report/$regionSelected/$yearMonthSelected") }
        }
    }
}


@Composable
private fun ViewReportButton(enabled: Boolean, hint: String?, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(Spacing.s)) {
        Button(
            onClick = onClick,
            enabled = enabled,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(stringResource(R.string.view_report), style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.padding(start = Spacing.s))
            Icon(Icons.AutoMirrored.Rounded.ArrowForward, contentDescription = null)
        }
        if (hint != null) {
            Text(
                text = hint,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}