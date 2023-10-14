package com.example.currentweather.data.remote
sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val error: String, val data: T? = null) : Resource<T>()

    companion object {
        inline fun <T, R> transform(
            resource: Resource<T>,
            transformFunction: (T) -> R
        ): Resource<R> {
            return when (resource) {
                is Success -> Success(transformFunction(resource.data))
                is Error -> Error(resource.error, resource.data?.let { transformFunction(it) })
                is Loading -> Loading()
            }
        }
    }
}