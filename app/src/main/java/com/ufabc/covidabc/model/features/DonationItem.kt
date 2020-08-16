package com.ufabc.covidabc.model.features

enum class DonationItem(private val value: String, private val minCount: Int) {
    ACUCAR("Açúcar", 1),
    ARROZ("Arroz", 1),
    BOLACHA("Bolacha", 1),
    CAFE("Café", 1),
    FEIJAO("Feijão", 1),
    MACARRAO("Macarrão", 2),
    MOLHO_DE_TOMATE("Molho de tomate", 2),
    OLEO("Óleo", 1),
    SAL("Sal", 1),
    ENLATADO("Enlatado", 1),
    ALHO_TRITURADO("Alho triturado", 0),
    FUBA("Fubá", 0),
    LEITE_EM_PO("Leite em pó", 0),
    MIOJO("Miojo", 0),
    FARINHA("Farinha", 0),
    ACHOCOLATADO_EM_PO("Achocolatado em pó", 0),
    PREPARO_DE_PUDIM("Preparo de pudim", 0),
    GELATINA("Gelatina", 0),
    LEITE("Leite", 0),
    MASSA_DE_BOLO("Massa de bolo", 0),
    MILHO_DE_PIPOCA("Milho de pipoca", 0),
    LEGUMES("Legumes", 0),
    TEMPERO("Tempero", 0),
    AVEIA_EM_FLOCOS("Aveia em flocos", 0),
    LASANHA("Lasanha", 0),
    MAIONESE("Maionese", 0),

    AGUA_SANITARIA("Água sanitária", 0),
    DETERGENTE("Detergente", 1),
    PASTA_DE_DENTE("Pasta de dente", 1),
    PAPEL_HIGIENICO("Papel higiênico", 1),
    SABONETE("Sabonete", 3),
    SABAO("Sabão", 2),
    DESINFETANTE("Desinfetante", 0),
    SABAO_EM_PO("Sabão em pó", 0),
    ABSORVENTE("Absorvente", 0),
    PAPEL_TOALHA("Papel toalha", 0),
    ESCOVA_DE_DENTE("Escova de dente", 0),
    BOMBRIL("bombril", 0);

    override fun toString() = value

    fun getMinCount() = minCount
}