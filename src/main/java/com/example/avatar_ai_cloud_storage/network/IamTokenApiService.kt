package com.example.avatar_ai_cloud_storage.network

import android.util.Log
import com.example.avatar_ai_cloud_storage.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * The TokenApi serves as a network API to connect to IBM IAM Cloud Authentication Service.
 * IAM tokens can be requested to authenticate other services.
 */

private const val TAG = "IamTokenApiService"

// Minimum remaining token validity in seconds
private const val MIN_VALIDITY = 10

// SERVICE CREDENTIALS
// URL Details
private const val BASE_URL = "https://iam.cloud.ibm.com"
private const val ENDPOINT = "/oidc/token"

// Request Headers
private const val ACCEPT = "Accept: application/json"
private const val CONTENT_TYPE = "Content-Type: application/x-www-form-urlencoded"

// Authentication Details
private const val API_KEY_HEADER = "apikey"
private const val API_KEY = BuildConfig.CLOUD_OBJECT_STORAGE_API_KEY

// Request Fields
private const val RESPONSE_TYPE_HEADER = "response_type"
private const val RESPONSE_TYPE = "cloud_iam"
private const val GRANT_TYPE_HEADER = "grant_type"
private const val GRANT_TYPE = "urn:ibm:params:oauth:grant-type:apikey"

// Time in milliseconds before a TimeoutException
// is called on a download (GET) request.
private const val TIMEOUT_MS = 7000L

private const val MILLISECONDS_IN_SECOND = 1000L

/*
* Moshi Converter Factory - decodes JSON web data.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/*
* Retrofit object with the base URL. Fetches data
* and decodes it with the Moshi Converter Factory.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/*
* Network layer: this interface defines the Retrofit HTTP requests.
 */
interface TokenApiService {
    /*
    * This function performs a POST request for an IAM Token.
     */
    @FormUrlEncoded
    @POST(ENDPOINT)
    @Headers(ACCEPT, CONTENT_TYPE)
    suspend fun getToken(
        @FieldMap params: Map<String, String>
    ): IamTokenResult
}

/*
* TokenApi connects to IBM IAM Cloud Authentication Service.
* It is initialised as a public singleton object to conserve resources
* by ensuring that the Retrofit API service is only initialised once.
 */
object TokenApi {

    // Initialise the retrofit service only at first usage (by lazy).
    private val retrofitService: TokenApiService by lazy {
        retrofit.create(TokenApiService::class.java)
    }

    // The last token requested is saved here.
    private var tokenResult: IamTokenResult? = null

    /*
    * This function returns a valid IBM IAM token. A new token
    * is requested from the service if the last available token
    * has less than MIN_VALIDITY seconds of validity. Null is
    * returned if an Exception occurs.
     */
    suspend fun getToken(): String? {
        // The minimum expiration timestamp required in Unix format.
        val targetUnixTimestamp =
            (System.currentTimeMillis() / MILLISECONDS_IN_SECOND) + MIN_VALIDITY

        return if (tokenResult != null
            && targetUnixTimestamp < tokenResult!!.expiration
        ) {
            tokenResult!!.accessToken
        } else {
            Log.i(TAG, "New IAM token requested")
            getNewToken()
        }
    }

    /*
    * This function requests a new IAM token from the service
    * and returns null if an error occurs.
     */
    private suspend fun getNewToken(): String? {
        val params = mapOf(
            API_KEY_HEADER to API_KEY,
            RESPONSE_TYPE_HEADER to RESPONSE_TYPE,
            GRANT_TYPE_HEADER to GRANT_TYPE
        )
        return try {
            withTimeout(TIMEOUT_MS) {
                tokenResult = retrofitService.getToken(params)
                tokenResult!!.accessToken
            }
        } catch (e: HttpException) {
            val httpError = e.response()?.errorBody()?.string() ?: "Unknown error"
            Log.e(TAG, "getNewToken: HTTP error: $httpError", e)
            null
        } catch (e: Exception) {
            Log.e(TAG, "getNewToken: exception occurred", e)
            null
        }
    }

}