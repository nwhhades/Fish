# 随便混混
-keep class cn.haizhe.**{*;}
-dontwarn cn.haizhe.**

#Eventbus
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode {*;}
-keepclassmembers class org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
-keep class org.greenrobot.eventbus.android.AndroidComponentsImpl

# Gson
-keepattributes Signature
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken

# OkHttp3
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** {*;}
-keep interface okhttp3.** {*;}
-dontwarn okhttp3.**
-dontwarn okio.**

# Retrofit2
-keep,allowobfuscation,allowshrinking class io.reactivex.Flowable
-keep,allowobfuscation,allowshrinking class io.reactivex.Maybe
-keep,allowobfuscation,allowshrinking class io.reactivex.Observable
-keep,allowobfuscation,allowshrinking class io.reactivex.Single

# 下载组件
-keep class com.liulishuo.okdownload.**{*;}
-dontwarn edu.umd.cs.findbugs.annotations.SuppressFBWarnings
