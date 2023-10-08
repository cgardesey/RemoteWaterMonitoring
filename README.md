# SMS-based Android App for Real-time Water Level Monitoring

This Android application allows users to monitor real-time water level data retrieved from an embedded system hardware. The hardware sends water level information to a Node.js backend server, which is then saved to a database. The Android app connects to the backend server using a socket connection to display real-time water level updates.

## Features

- **Real-time Monitoring:** View live water level updates from the embedded system hardware in real-time.
- **Historical Data:** Access historical water level data stored in the database for analysis and trends.
- **User-Friendly Interface:** Intuitive and easy-to-use interface for seamless user experience.

## Getting Started

To get started with the project, follow the instructions below:

1. Clone this repository to your local machine using Git or download the ZIP file.
2. Open Android Studio and select "Open an existing Android Studio project."
3. Navigate to the cloned or downloaded project directory and click "OK."
4. Android Studio will build the project and download any necessary dependencies.
5. Connect an Android device or use an emulator to run the application.

## Configuration

The example project requires the following configuration:

- Minimum SDK version: 14
- Target SDK version: 23

You may need to update these values in the `build.gradle` file based on your project requirements.

## Usage

1. Open the project in Android Studio.
2. Connect your Android device to your computer via USB.
3. Build and run the app on your Android device.

If you're looking to integrate with arduino project, make sure to check out the the repository corresponding to the [ariduino firmware](https://github.com/cgardesey/remote_water_level_measurement_firmware) for detailed instructions.

If you're looking to integrate with the node.js backend, make sure to check out the the repository corresponding to the [backend](https://github.com/cgardesey/remote_water_level_measurement_firmware) for detailed instructions.

## License

RemoteWaterMonitoring project is licensed under the [MIT License](https://opensource.org/licenses/MIT). Feel free to use it as a reference or starting point for your own projects.
