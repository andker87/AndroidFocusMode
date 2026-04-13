# Hilt
-keep class dagger.hilt.** { *; }
-keepclasseswithmembernames class * {
    @dagger.hilt.* <fields>;
    @dagger.hilt.* <methods>;
}

# Room
-keep class * extends androidx.room.RoomDatabase { *; }
-keepclassmembers class * {
    @androidx.room.* <fields>;
}

# Gson
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Coroutines
-keep class kotlin.coroutines.** { *; }
-keep class kotlinx.coroutines.** { *; }

# Keep data models
-keep class com.androidfocusmode.app.data.model.** { *; }

# Keep viewmodel
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keepclassmembers class * {
    @androidx.lifecycle.* <fields>;
}
