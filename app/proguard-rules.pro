# This file is used by ProGuard to shrink and obfuscate your code.
# To enable this process, set minifyEnabled to true in your
# module-level build.gradle file.
#
# For more details, see the ProGuard documentation:
#   http://proguard.sourceforge.net/index.html#manual/usage.html

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/user/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# line to avoid problems with JS obfuscation.
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Add any project specific keep options here.

# If you use the Support Library, uncomment the following lines.
#-keep public class * extends android.support.v4.**
#-keep public class * extends android.app.Fragment
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.view.View {
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#    public void set*(...);
#}
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#}
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#-keepclasseswithmembers class * {
#    public void set*(...);
#}
#-keepclassmembers class * implements android.os.Parcelable {
#    static android.os.Parcelable$Creator CREATOR;
#}
#-keepclassmembers class **.R$* {
#    public static <fields>;
#}

# If you use Gson, uncomment the following lines.
-keep class com.google.gson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.example.simplesteamsearch.data.model.** { *; }
-keep class com.example.simplesteamsearch.data.network.** { *; }
-keep class com.example.simplesteamsearch.data.repository.** { *; }
-keep class com.example.simplesteamsearch.ui.** { *; }
-keep class com.example.simplesteamsearch.utils.** { *; }
-keep class com.example.simplesteamsearch.viewmodel.** { *; }

# Keep Retrofit and OkHttp
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keep class okio.** { *; }

# Keep Gson
-keep class com.google.gson.** { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.internal.** { *; }

# Keep Kotlin metadata
-keepattributes *Annotation*