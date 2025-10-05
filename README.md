# WeatherApp-compose 🌤️

A simple and clean **Weather Application** built with **Jetpack Compose**, based on the official Android [architecture templates](https://github.com/android/architecture-templates/tree/base?tab=readme-ov-file)
 for guidance on building a clean and scalable app structure.


---

## Features
- Supports phone and tablet responsive layouts (portrait stack / landscape split)
- Fetches current weather and hourly forecasts using OpenWeather API
- Displays user-friendly error messages for network or search issues
- Smooth, modern Compose UI

## Architecture & Tech Stack
- MVVM architecture for maintainability
- Dependency Injection with Hilt
- Retrofit for network requests
- Coil for image loading

---

## Screenshot & Demo


![Weather App screen](https://github.com/DudeInTheWood/MyImage/blob/main/weather%20app%20screen.png)

![Weather App Demo](https://github.com/DudeInTheWood/MyImage/raw/main/weather-app-demo.gif)

---

## Prerequisites

- Android Studio (latest stable version recommended)
- OpenWeather API key ([Get it here](https://openweathermap.org/))

---

## Getting Started

1. **Clone the repository**
2. Add your OpenWeather API key to local.properties:

`OPENWEATHER_API_KEY=YOUR_API_KEY_HERE`

3. Build and run the app on a device or emulator

