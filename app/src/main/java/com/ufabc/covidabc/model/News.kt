package com.ufabc.covidabc.model

import android.media.Image
import java.io.Serializable

class News: Serializable {

    private lateinit var titulo: String
    private lateinit var tituloAuxiliar: String
    private lateinit var imagem: Image

    constructor()

    constructor(titulo: String, tituloAuxiliar: String, imagem: Image) {
        this.titulo = titulo
        this.tituloAuxiliar = tituloAuxiliar
        this.imagem = imagem
    }


    fun getTitulo() = this.titulo
    fun getTituloAuxiliar() = this.tituloAuxiliar
    fun getImagem() = this.imagem

}