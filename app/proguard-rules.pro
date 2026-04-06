# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Project specific
-keep class loan.calculator.domain.entity.** { *; }
-keep class com.shockwave.**

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}
-keepclassmembernames class kotlinx.coroutines.android.HandlerContext$HandlerPost {
    *** run();
}

# Retrofit 2
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleAnnotations, RuntimeInvisibleParameterAnnotations
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# OkHttp 3
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-keepclassmembernames class okhttp3.internal.publicsuffix.PublicSuffixDatabase {
    getDatabaseBuffer();
}

# Moshi
-keep class com.squareup.moshi.** { *; }
-keep interface com.squareup.moshi.** { *; }
-keepattributes Signature,InnerClasses,EnclosingMethod
-dontwarn com.squareup.moshi.**

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.** { *; }
-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.reflect.TypeToken
-keep @com.google.gson.annotations.SerializedName class * { *; }
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Glide
-keep public class * implements com.github.bumptech.glide.module.GlideModule
-keep public class * extends com.github.bumptech.glide.module.AppGlideModule
-keep public enum com.github.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Hilt
-keep,allowobfuscation,allowshrinking @dagger.hilt.android.internal.lifecycle.HiltViewModelMap *
-keep,allowobfuscation,allowshrinking @dagger.hilt.internal.GeneratedEntryPoint *

# Lottie
-keep class com.airbnb.lottie.** { *; }

# OneSignal
-keep class com.onesignal.** { *; }
-dontwarn com.onesignal.**

# Timber
-dontwarn timber.log.**
-keep class timber.log.** { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# Navigation
-keepclassmembers class * extends androidx.navigation.NavArgs {
    public <init>(...);
}

# Google Play Services / AdMob
-keep class com.google.android.gms.ads.** { *; }
-keep class com.google.android.gms.common.** { *; }

# Keep Hilt core classes
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class dagger.** { *; }
-dontwarn dagger.hilt.**
-dontwarn javax.inject.**

# Keep all Hilt-generated classes (this is the most important for your crash)
-keep class **.Hilt_* { *; }
-keep class **.*Hilt* { *; }

# Keep classes annotated with Hilt annotations
-keep,allowobfuscation,allowshrinking class * {
    @dagger.hilt.* <fields>;
    @dagger.hilt.* <methods>;
}

# Keep your MainActivity and other @AndroidEntryPoint classes
-keep class loan.calculator.app.MainActivity { *; }

# Optional but recommended for safety
-keepclassmembers class ** {
    @javax.inject.* <fields>;
    @dagger.* <fields>;
}
