package com.ufabc.covidabc.model

interface FirestoreDatabaseOperationListener<K>{
    fun onSuccess(result: K)
    fun onFailure()
}