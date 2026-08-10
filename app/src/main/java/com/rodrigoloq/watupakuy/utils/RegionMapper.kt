package com.rodrigoloq.watupakuy.utils

object RegionMapper {
    private val regionsMapper = listOf<RegionCoordinates>(
        RegionCoordinates("Amazonas", -6.2317, -77.8690),
        RegionCoordinates("Áncash", -9.5300, -77.5280),
        RegionCoordinates("Apurímac", -13.6339, -72.8814),
        RegionCoordinates("Arequipa", -16.4090, -71.5375),
        RegionCoordinates("Ayacucho", -13.1588, -74.2236),
        RegionCoordinates("Cajamarca", -7.1638, -78.5003),
        RegionCoordinates("Callao", -12.0566, -77.1181),
        RegionCoordinates("Cusco", -13.5319, -71.9675),
        RegionCoordinates("Huancavelica", -12.7826, -74.9727),
        RegionCoordinates("Huánuco", -9.9306, -76.2422),
        RegionCoordinates("Ica", -14.0678, -75.7286),
        RegionCoordinates("Junín", -12.0651, -75.2049),
        RegionCoordinates("La Libertad", -8.1116, -79.0288),
        RegionCoordinates("Lambayeque", -6.7714, -79.8409),
        RegionCoordinates("Lima", -12.0464, -77.0428),
        RegionCoordinates("Loreto", -3.7491, -73.2538),
        RegionCoordinates("Madre de Dios", -12.5933, -69.1891),
        RegionCoordinates("Moquegua", -17.1920, -70.9328),
        RegionCoordinates("Pasco", -10.6864, -76.2620),
        RegionCoordinates("Piura", -5.1945, -80.6328),
        RegionCoordinates("Puno", -15.8402, -70.0219),
        RegionCoordinates("San Martín", -6.0342, -76.9713),
        RegionCoordinates("Tacna", -18.0066, -70.2463),
        RegionCoordinates("Tumbes", -3.5669, -80.4515),
        RegionCoordinates("Ucayali", -8.3791, -74.5539)
    )

    fun getCoordinatesFromRegion(regionName: String, onResult:(Double, Double) -> Unit){
        val region = regionsMapper.find { it.name == regionName }
        onResult(region!!.latitude , region!!.longitude)
    }
}