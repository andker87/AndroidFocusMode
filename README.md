# Android Focus Mode

A powerful Android app for managing notification focus modes with time-based, location-based, and manual activation.

## Features

- 🔔 **Pre-configured Focus Modes**: Normal, Night, Driving, Gym, Work
- ⏰ **Time-Based Activation**: Set specific times for each mode
- 📍 **Location-Based Activation**: Activate modes based on geofencing
- 🎯 **Manual Activation**: Toggle modes on demand
- 📱 **Android Auto Support**: Auto-activate during connected car sessions
- ⭐ **Favorites-Only Calls**: Allow calls from specific contacts during focus modes
- 🔕 **Custom Whitelists**: Choose which apps can notify you
- 📊 **Material Design 3 UI**: Beautiful, modern interface with Jetpack Compose

## Tech Stack

- **Kotlin** - Modern programming language for Android
- **Jetpack Compose** - Modern UI toolkit
- **Room Database** - Local data persistence
- **WorkManager** - Background job scheduling
- **Geofencing API** - Location-based triggers
- **Hilt** - Dependency injection
- **Coroutines** - Asynchronous programming

## Requirements

- Android 12+ (minSdk 31)
- API Level 34+ recommended
- ~50MB storage space

## Installation

### Prerequisites

1. Install [Android Studio](https://developer.android.com/studio)
2. Clone this repository
3. Open the project in Android Studio

### Building

```bash
# Build the project
./gradlew build

# Build and run on emulator/device
./gradlew installDebug
```

## Project Structure

```
app/src/main/kotlin/com/androidfocusmode/app/
├── data/
│   ├── db/              # Room Database & DAO
│   ├── factory/         # Predefined modes factory
│   ├── model/           # Data models
│   ├── preferences/     # SharedPreferences management
│   └── repository/      # Repository pattern implementation
├── di/                  # Dependency Injection (Hilt)
├── receiver/            # Broadcast receivers (Geofence, Boot)
├── service/
│   ├── dnd/            # Do Not Disturb management
│   ├── geofence/       # Location services
│   ├── notification/   # Notification management
│   └── initialization/ # Boot-time initialization
├── ui/
│   ├── components/     # Reusable Compose components
│   ├── navigation/     # Navigation setup
│   ├── screen/         # Screens (Home, Detail)
│   ├── theme/          # Theme configuration
│   └── viewmodel/      # ViewModels
├── worker/             # Background job workers
└── AndroidFocusModeApp.kt  # Application class
```

## Usage

### Creating a Focus Mode

1. Tap the **+** button to create a new mode
2. Enter **mode name** and description
3. Choose **activation trigger**:
   - **Manual**: Toggle on/off manually
   - **Time-based**: Set start/end times
   - **Location-based**: Set location with geofence radius
4. Configure **notifications**:
   - Allow/block notifications
   - Allow/block vibration
   - Choose which apps can notify
5. Configure **calls**:
   - Allow all calls
   - Allow favorites only
   - Block all calls
6. Tap **Save Mode**

### Activating a Focus Mode

- Tap the **toggle switch** on any mode card to activate it
- Only one mode can be active at a time
- Tap **Edit** to modify settings
- Tap **Delete** to remove the mode

## Current Status (MVP)

✅ **Completed:**
- Data models and database schema
- UI for creating/editing modes
- List view with toggle switches
- Dependency injection setup
- Theme and styling
- Predefined modes factory

🚧 **In Progress:**
- Time-based scheduling with WorkManager
- Geofencing integration
- DND policy application
- Per-app notification blocking

⏳ **TODO:**
- Widget creation
- Quick settings tile
- Android Auto integration
- Call filtering
- Contact favorites integration
- Export/Import profiles
- Advanced analytics

## Permissions

The app requests the following permissions:

- `ACCESS_FINE_LOCATION` - For precise geofencing
- `ACCESS_COARSE_LOCATION` - For approximate location
- `ACCESS_BACKGROUND_LOCATION` - For location in background
- `CHANGE_NOTIFICATION_POLICY` - To control DND
- `POST_NOTIFICATIONS` - For app notifications
- `SCHEDULE_EXACT_ALARM` - For precise time-based triggers
- `READ_PHONE_STATE` - For call detection
- `READ_CONTACTS` - For favorites list
- `BLUETOOTH` / `BLUETOOTH_ADMIN` - For BT control
- `CHANGE_WIFI_STATE` - For Wi-Fi control

## Known Issues

- Screen brightness control requires system permissions
- Screen timeout changes require system-level permission
- Some devices have aggressive battery optimization for background location

## Future Enhancements

- [ ] Widget for quick activation
- [ ] Quick settings tile
- [ ] Android Auto integration
- [ ] Advanced call filtering
- [ ] Cloud sync (Firebase?)
- [ ] Tasker integration
- [ ] IFTTT support

## Contributing

Contributions welcome! Feel free to:
- Report bugs
- Suggest features
- Submit pull requests

## License

This project is licensed under the MIT License.

## Support

For issues or questions:
1. Check existing GitHub issues
2. Create a new issue with details
3. Include device info (Android version, device model)

## Roadmap

**v1.0.0** - Core features
- ✅ Focus mode creation/editing
- ✅ Time-based activation
- ✅ Location-based activation
- ⏳ Full DND integration

**v1.1.0** - Widgets & Quick Access
- ⏳ Home screen widget
- ⏳ Quick settings tile

**v2.0.0** - Advanced Features
- ⏳ Cloud sync
- ⏳ Profile scheduling
- ⏳ Advanced automation
