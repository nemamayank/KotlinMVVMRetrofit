# KotlinMVVMRetrofit

MVVM With Retrofit and Recyclerview in Kotlin

MVVM architecture is a Model-View-ViewModel architecture that removes the tight coupling between each component.
Also, in this architecture, the children don’t have a direct reference to the parent, they only have the reference by observables.
Here Remote Source is transfering data and invoke the data to observer listening to the API.


Add the following dependencies in your app level build.gradle.

```
    //ViewModel and livedata
    implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"

    //Retrofit
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

    //Glide
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
```

Also, don’t forget to add a internet permission in your manifeats.xml file.
```
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.INTERNET"/>
```

Setup the Data Layer
In the data layer, we have to prepare the model for the data, and an API call needs to be implemented. In this example, I am using a Repository pattern to handle the data.

Setting up Retrofit
Retrofit is a “A type-safe HTTP client for Android and Java”.

First, create the interface for the API call definition.

```
@GET("photos")
    fun getAllPhotos(): Call<List<Photo>>

    companion object {

        private var retrofitService: RetrofitService? = null

        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
```

For the other implementation and code you may refer to the repositary.

Thanks and Happy Coding !!!
