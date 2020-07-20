package com.ufabc.covidabc.model

interface FirestoreDatabaseOperationListener<K>{
    fun onCompleted(result : K)
}