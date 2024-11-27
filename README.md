# Android Workmanager and Notification Basic App

This is an Android application that demonstrates how to manage notifications (including runtime permissions on Android 13+) and implement edge-to-edge design. The app requests the necessary permissions to post notifications on Android 13+ devices, creates a `NotificationChannel`, and schedules a `WorkManager` task to trigger a notification after a set delay. Additionally, it handles edge-to-edge window insets to ensure the content layout respects system bars.

## Features
- **Permission Handling**: Requests `POST_NOTIFICATIONS` permission for Android 13+.
- **Notification Channel**: Creates a notification channel for posting notifications.
- **WorkManager**: Uses `WorkManager` to schedule notifications.
- **Edge-to-Edge UI**: Ensures content respects system bars and displays properly on devices with notches or rounded corners.
  
## Requirements
- Android 13 (API level 33) or higher for managing notification permissions.
- Android Studio with Gradle.

## Installation

To clone this repository and build the project locally:

1. Clone this repository to your local machine:
    ```bash
    git clone https://github.com/your-username/android-notification-and-edge-to-edge.git
    ```

2. Open the project in Android Studio.

3. Ensure you have the necessary SDKs and build tools installed.

4. Sync the project with Gradle files.

5. Build and run the app on a physical device or emulator.

## Permissions
The app requires the following permissions:
- `POST_NOTIFICATIONS` (Android 13+)

If permission is not granted, the app will request it at runtime.

## Code Overview

### `MainActivity.kt`
- Handles the setup for notification permissions and notification channel creation.
- Configures a `WorkManager` task to send a notification after a delay.
- Applies edge-to-edge window insets for a modern, immersive UI.

### `NotificationWorker.kt`
- A custom worker class that sends a notification after the specified delay.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.


## Acknowledgements

- [Android Developers Documentation](https://developer.android.com/)
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
