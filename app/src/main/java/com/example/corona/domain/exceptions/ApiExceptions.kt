package com.example.corona.domain.exceptions

abstract class ApiExceptions(message: String?): Exception(message)


class TimeOutException(message: String?): ApiExceptions(message)