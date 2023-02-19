package xyz.savvamirzoyan.nous.core

sealed class ErrorEntity {
    object NoConnection : ErrorEntity()
    object ServerError : ErrorEntity()
    object NoData : ErrorEntity()
    object Unknown : ErrorEntity()
}