package com.ufabc.covidabc.model


import android.widget.ImageView
import java.io.Serializable

class News: Serializable {

    private lateinit var titulo: String
    private lateinit var tituloAuxiliar: String
    private lateinit var imagem: ImageView

    constructor()

    constructor(titulo: String, tituloAuxiliar: String, imagem: ImageView) {
        this.titulo = titulo
        this.tituloAuxiliar = tituloAuxiliar
        this.imagem = imagem
    }


    fun getTitulo() = this.titulo
    fun getTituloAuxiliar() = this.tituloAuxiliar
    fun getImagem() = this.imagem

}