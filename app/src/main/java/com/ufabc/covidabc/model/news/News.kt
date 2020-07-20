package com.ufabc.covidabc.model.news


import android.widget.ImageView
import java.io.Serializable

class News: Serializable {

    private lateinit var titulo: String
    private lateinit var tituloAuxiliar: String
    private lateinit var imagemURI: String

    constructor()

    constructor(titulo: String, tituloAuxiliar: String, imagemURI: String) {
        this.titulo = titulo
        this.tituloAuxiliar = tituloAuxiliar
        this.imagemURI = imagemURI
    }


    fun getTitulo() = this.titulo
    fun getTituloAuxiliar() = this.tituloAuxiliar
    fun getImagemURI() = this.imagemURI

}