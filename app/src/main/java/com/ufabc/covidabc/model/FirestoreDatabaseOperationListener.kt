package com.ufabc.covidabc.model

interface FirestoreDatabaseOperationListener<K>{
    fun onCompleted(sucess : K)
}