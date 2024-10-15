package com.dezzy.weatherapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dezzy.weatherapp.R
import com.dezzy.weatherapp.network.NetworkResponse
import com.dezzy.weatherapp.network.WeatherModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {

    var city by remember { mutableStateOf("") }
    val weatherResult = viewModel.weatherResult.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = {
                    Text(text = stringResource(id = R.string.search_city))
                },
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = {
                viewModel.getWeatherData(city)
                keyboardController?.hide()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_icon_description)
                )
            }
        }
        when(val result = weatherResult.value){
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                WeatherDetails(data = result.data)
            }
            null -> {}
        }
    }


}

@Composable
fun WeatherDetails(data: WeatherModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "Location Icon",
                Modifier.size(40.dp)
            )
            Text(text = data.location.name, fontSize = 32.sp)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = data.location.country, fontSize = 24.sp, color = Color.Gray)
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
    Text(
        text = "${data.current.temp_c}℃",
        fontSize = 64.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )

    AsyncImage(
        model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
        contentDescription = "Weather Icon",
        modifier = Modifier.size(128.dp)
    )

    Text(
        text = data.current.condition.text,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        color = Color.Gray
    )

    Spacer(modifier = Modifier.height(24.dp))
    WeatherCard(data = data)
}

@Composable
fun WeatherCard(data: WeatherModel) {
    val scrollState = rememberScrollState()

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .weight(0.5f)
                ) {
                    WeatherKeyValueData(key = "Humidity", value = "${data.current.humidity}%")
                    WeatherKeyValueData(key = "Wind", value = "${data.current.wind_kph}km/h")
                    WeatherKeyValueData(key = "UV", value = data.current.uv)
                    WeatherKeyValueData(key = "Heat index", value = "${data.current.heatindex_c}℃")
                    WeatherKeyValueData(
                        key = "Local Time",
                        value = data.location.localtime.split(" ")[1]
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .weight(0.5f)
                ) {
                    WeatherKeyValueData(key = "Dewpoint", value = data.current.dewpoint_c)
                    WeatherKeyValueData(key = "Visibility", value = "${data.current.vis_km}km")
                    WeatherKeyValueData(key = "Pressure", value = "${data.current.pressure_mb}mb")
                    WeatherKeyValueData(key = "Precipitation", value = "${data.current.precip_mm}mm")
                    WeatherKeyValueData(key = "Last updated", value = data.current.last_updated)
                }
            }
        }

    }
}

@Composable
fun WeatherKeyValueData(key: String, value: String) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherScreenPreview() {
    val viewModel: WeatherViewModel = viewModel()
    WeatherScreen(viewModel)
}