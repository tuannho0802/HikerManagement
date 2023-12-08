plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.hikermanagement"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.hikermanagement"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //scalable size
    implementation("com.intuit.sdp:sdp-android:1.0.6")
    implementation("com.intuit.ssp:ssp-android:1.0.6")

    //circular image
    implementation("de.hdodenhof:circleimageview:3.1.0")

    //image crop dependency
    implementation("com.theartofdev.edmodo:android-image-cropper:2.8.+")

    //swap layout
    implementation("com.chauthai.swipereveallayout:swipe-reveal-layout:1.4.1")
}