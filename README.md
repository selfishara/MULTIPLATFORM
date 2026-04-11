# 🏋️ GymSpot Lite - Kotlin Multiplatform

A lightweight multiplatform application built with **Kotlin Multiplatform (KMP)** and **Jetpack Compose Multiplatform** that allows users to browse exercises, create workout routines, and execute workout sessions in a simple and interactive way.

> **Note**: This is a learning project focused on MVP simplicity with plans for future enhancements like real API integration, persistence, and advanced tracking.

---

## 🚀 Features

- 📋 **Browse Exercises**: Scrollable list of available exercises with names and target muscle groups
- 🔍 **Exercise Details**: View comprehensive information including muscle group, execution instructions
- ➕ **Add to Routine**: Dual-path approach:
  - **Quick Add**: Direct button from exercise list
  - **Informed Add**: View details first, then add from detail screen
- 📝 **Edit Routine**: Rename your workout routine
- ❌ **Remove Exercises**: Delete individual exercises from routine
- 🧹 **Clear Routine**: Remove all exercises at once
- ▶️ **Workout Mode**: Execute workouts with real-time tracking (foundation for future sets/reps/duration tracking)

---

## 🧠 Tech Stack

- **Kotlin Multiplatform (KMP)** - Shared code across platforms
- **Jetpack Compose Multiplatform** - Cross-platform UI
- **Navigation 3 (JetBrains)** - Modern navigation system with state restoration
- **Compose State** (`mutableStateOf`, `mutableStateListOf`) - State management

---

## 🧱 Architecture

The project follows a clean, modular structure:

```
commonMain/
├── data/           # Fake data source (FakeExercises)
├── model/          # Domain models (Exercise)
├── state/          # Global state (RoutineState)
├── navigation/     # Navigation setup (Route, NavConfig, NavigationWrapper)
├── components/     # Reusable UI components (ExerciseItem)
└── screens/        # UI screens (Home, Exercises, Detail, Routine, Workout)
```

### Key Components

- **Exercise Model**: Core entity with id, name, muscleGroup, and instructions
- **RoutineState**: Singleton state container for managing user's routine (exercises, routine name)
- **FakeExercises**: Mock data source (3 sample exercises: Push Up, Squat, Plank)
- **ExerciseItem Component**: Reusable card for displaying exercises with remove button

---

## 🔄 User Flow

```
Home Screen (with exercise counter)
├── "Browse Exercises" → Exercise List Screen
│   ├── Click item → Exercise Detail Screen
│   │   └── "Add to Routine" → Adds to RoutineState
│   └── Click "Add" button → Direct add to RoutineState
│
└── "View My Routine (X)" → My Routine Screen
    ├── Edit routine name
    ├── View exercises (with remove button per item)
    ├── "Clear Routine" → Clears all exercises
    └── "Start Workout" [enabled if exercises present]
        └── Workout Mode Screen
            └── Navigate through exercises with progress tracking
```

---

## 🎯 Project Goals

This project serves as a learning platform for:

- ✅ Kotlin Multiplatform fundamentals
- ✅ Compose UI development for multiplatform
- ✅ Navigation system best practices
- ✅ State management without ViewModel (MVP pattern)
- ✅ Clean and modular architecture
- ⏳ Advanced features (persistence, real data, tracking)

---

## ⚠️ Current Limitations

- Uses fake data (no real API yet)
- Only one routine supported
- No persistence (data resets on restart)
- No advanced workout tracking (sets, reps, timers) - planned for next phase
- UI/UX styling focused on functionality first

---

## 🔮 Future Improvements (Roadmap)

### Phase 2 - Workout Mode Enhancement
- Add sets, reps, and duration tracking to Exercise model
- Real-time progress tracking during workouts
- Simple timer for time-based exercises
- Workout session history

### Phase 3 - Data Persistence
- 💾 Local database integration (Room or Realm)
- ☁️ Cloud sync capability

### Phase 4 - Production Ready
- 🌐 Real exercises API integration
- 📊 Advanced workout analytics
- 👤 User profile and authentication
- 🎨 Professional UI/UX design
- 🧠 ViewModel + StateFlow architecture migration

---

## ▶️ How to Run

### Android
Run from Android Studio using an emulator or physical device:
```shell
./gradlew :composeApp:assembleDebug
# or via IDE run configuration
```

### Desktop (JVM)
```shell
./gradlew :composeApp:run
# or on Windows:
.\gradlew.bat :composeApp:run
```

### Web (WASM)
```shell
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
# or on Windows:
.\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
```

### iOS
Open `iosApp/iosApp.xcodeproj` in Xcode and run from there.

---

## 📝 Recent Changes (Current MVP)

### ✅ Navigation System
- Clean NavigationWrapper with 5 routes (Home, Exercises, ExerciseDetail, MyRoutine, Workout)
- Proper state management with RoutineState integration
- Automatic error handling (redirect if exercise not found)

### ✅ Exercise Management
- Dual-path exercise addition: quick add from list OR detailed view
- Automatic duplicate prevention (same exercise can't be added twice)
- Real-time state reflection across screens

### ✅ User Feedback
- Exercise counter on Home screen showing routine size
- State-aware buttons (disabled when appropriate)
- Clear visual feedback on all interactions

### ✅ Component Reusability
- ExerciseItem component used consistently in MyRoutineScreen
- Data source unified with FakeExercises
- No code duplication

---

## 🏗️ Development Notes

- **State Management**: Currently using simple Compose state (RoutineState singleton) for MVP. Plan to migrate to ViewModel + StateFlow in production phase.
- **Data Layer**: FakeExercises provides mock data. Replace with real API integration in Phase 2.
- **Navigation**: Navigation 3 provides robust navigation with back stack management and serialization support.

---

## 👩‍💻 Author

Developed by **Sara** as part of a Kotlin Multiplatform learning project.

---

## 📚 Learning Resources

- [Kotlin Multiplatform Documentation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/)
- [Compose Multiplatform GitHub](https://github.com/JetBrains/compose-multiplatform)
- [Jetpack Compose Best Practices](https://developer.android.com/jetpack/compose)

