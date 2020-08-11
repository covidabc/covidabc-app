package com.ufabc.covidabc.model.features

enum class DonationItem(private val value: String) {
    ACUCAR("Açúcar"),
    ARROZ("Arroz"),
    BOLACHA("Bolacha"),
    FEIJAO("Feijão"),
    MACARRAO("Macarrão"),
    MOLHO_DE_TOMATE("Molho de tomate"),
    OLEO("Óleo"),
    DSAL("Sal"),
    ENLATADO("Enlatado"),
    ALHO_TRITURADO("Alho triturado"),
    FUBA("Fubá"),
    LEITE_EM_PO("Leite em pó"),
    MIOJO("Miojo"),
    FARINHA("Farinha"),
    ACHOCOLATADO_EM_PO("Achocolatado em pó"),
    PREPARO_DE_PUDIM("Preparo de pudim"),
    GELATINA("Gelatina"),
    LEITE("Leite"),
    MASSA_DE_BOLO("Massa de bolo"),
    MILHO_DE_PIPOCA("Milho de pipoca"),
    LEGUMES("Legumes"),
    TEMPERO("Tempero"),
    AVEIA_EM_FLOCOS("Aveia em flocos"),
    LASANHA("Lasanha"),
    MAIONESE("Maionese"),

    AGUA_SANITARIA("Água sanitária"),
    DETERGENTE("Detergente"),
    PASTA_DE_DENTE("Pasta de dente"),
    PAPEL_HIGIENICO("Papel higiênico"),
    SABONETE("Sabonete"),
    SABAO("Sabão"),
    DESINFETANTE("Desinfetante"),
    SABAO_EM_PO("Sabão em pó"),
    ABSORVENTE("Absorvente"),
    PAPEL_TOALHA("Papel toalha"),
    ESCOVA_DE_DENTE("Escova de dente"),
    BOMBRIL("bombril");

    override fun toString() = value
}