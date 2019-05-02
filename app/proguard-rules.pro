# ProGuard rules for release build

# see https://www.guardsquare.com/en/products/proguard/manual/usage/optimizations

#-keepattributes SourceFile,LineNumberTable

-assumevalues class android.os.Build$VERSION {
    int SDK_INT return 28;
}

##---------------Begin: proguard configuration for OkHttp  ----------
-assumenosideeffects class okhttp3.internal.platform.ConscryptPlatform {
    public static okhttp3.internal.platform.ConscryptPlatform buildIfSupported() return null;
}
-assumenosideeffects class okhttp3.internal.platform.Jdk9Platform {
    public static okhttp3.internal.platform.Jdk9Platform buildIfSupported() return null;
}
-assumenosideeffects class okhttp3.internal.platform.JdkWithJettyBootPlatform {
    public static okhttp3.internal.platform.Platform buildIfSupported() return null;
}
##---------------End: proguard configuration for OkHttp  ----------