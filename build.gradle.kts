plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.avatar_ai_cloud_storage"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        all {
            // Load IBM Cloud Storage Credentials from gradle.properties
            val databaseEndPoint = project.property("DATABASE_STORAGE_ENDPOINT")
            val modelEndPoint = project.property("MODEL_STORAGE_ENDPOINT")
            val cloudObjectStorageApiKey = project.property("CLOUD_OBJECT_STORAGE_API_KEY")

            // Initialise the credentials as BuildConfig fields.
            buildConfigField("String", "DATABASE_STORAGE_ENDPOINT", "$databaseEndPoint")
            buildConfigField("String", "MODEL_STORAGE_ENDPOINT", "$modelEndPoint")
            buildConfigField("String", "CLOUD_OBJECT_STORAGE_API_KEY", "$cloudObjectStorageApiKey")
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")

    // Room Libraries
    implementation("androidx.room:room-runtime:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")

    // WEb Service Libraries
    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")
    // Retrofit with Moshi Converter
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}