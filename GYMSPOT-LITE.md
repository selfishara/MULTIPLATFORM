# 🏋️ GymSpot Lite (Kotlin Multiplatform)

GymSpot Lite is a lightweight multiplatform application built with Kotlin Multiplatform and Jetpack Compose.  
It allows users to browse exercises, create a workout routine, and execute workouts in a simple and interactive way.

---

## 🚀 Features

- 📋 Browse a list of exercises
- 🔍 View exercise details (muscle group, instructions)
- ➕ Add exercises to a personal routine
- 📝 Edit routine name
- ❌ Remove exercises from routine
- 🧹 Clear entire routine
- ▶️ Start workout mode and navigate through exercises

---

## 🧠 Tech Stack

- Kotlin Multiplatform (KMP)
- Jetpack Compose Multiplatform
- Navigation 3 (JetBrains)
- Compose State (`mutableStateOf`, `mutableStateListOf`)

---

## 🧱 Architecture

The project follows a simple and modular structure:

commonMain/
├── data/           # Fake data source (FakeExercises)
├── model/          # Domain models (Exercise)
├── state/          # Global state (RoutineState)
├── navigation/     # Navigation setup (Route, NavConfig, NavigationWrapper)
├── components/     # Reusable UI components (ExerciseItem)
└── screens/        # UI screens (Home, Exercises, Detail, Routine, Workout)

---

## 🔄 User Flow

Home
├── Browse Exercises
│     └── Exercise Detail → Add to Routine
├── View My Routine
│     ├── Edit name
│     ├── Remove exercises
│     ├── Clear routine
│     └── Start Workout
└── Workout Mode
└── Navigate through exercises

---

## 🎯 Project Goal

The goal of this project is to practice:

- Kotlin Multiplatform fundamentals
- Compose UI development
- Navigation between screens
- State management (MVP approach without ViewModel)
- Clean and modular architecture

---

## ⚠️ Current Limitations

- Uses fake data (no real API yet)
- Only one routine supported
- No persistence (data resets on restart)
- No advanced workout tracking (sets, reps, timers)

---

## 🔮 Future Improvements

- 🌐 Integrate real exercises API
- 💾 Add local persistence (database)
- 📊 Workout tracking (sets, reps, timers)
- 👤 User profile
- 🎨 UI/UX improvements
- 🧠 Migrate to ViewModel + StateFlow

---

## ▶️ How to Run

### Android
Run from Android Studio using an emulator or physical device.

### Desktop
```bash```
./gradlew :composeApp:run

### Web (optional)

./gradlew :composeApp:wasmJsBrowserDevelopmentRun


## 👩‍💻 Author

Developed by Sara as part of a Kotlin Multiplatform learning project.

