package loan.exchange.uikit.extension

import loan.exchange.uikit.R


fun String.getImageResource(): Int{
    var returnResource = 0
    returnResource = when(this){
        "AZN" -> R.drawable.ic_azerbaijan
        "ALL" -> R.drawable.ic_albania
        "ARS" -> R.drawable.ic_argentina
        "AUD" -> R.drawable.ic_australia
        "AED" -> R.drawable.ic_united_arab_emirates
        "AFN" -> R.drawable.ic_afghanistan
        "AMD" -> R.drawable.ic_armenia
        "AOA" -> R.drawable.ic_angola

        "BRL" -> R.drawable.ic_brazil
        "BMD" -> R.drawable.ic_bermuda
        "BHD" -> R.drawable.ic_bahrain
        "BDT" -> R.drawable.ic_bangladesh
        "BYN" -> R.drawable.ic_belarus
        "BOB" -> R.drawable.ic_bolivia
        "BAM" -> R.drawable.ic_bosnia_and_herzegovina
        "BGN" -> R.drawable.ic_bulgaria

        "CHF" -> R.drawable.ic_switzerland
        "CZK" -> R.drawable.ic_czech_republic
        "CAD" -> R.drawable.ic_canada
        "CLP" -> R.drawable.ic_chile
        "CNY" -> R.drawable.ic_china
        "CRC" -> R.drawable.ic_costa_rica
        "CUC" -> R.drawable.ic_cuba

        "DZD" -> R.drawable.ic_algeria
        "DKK" -> R.drawable.ic_denmark

        "EGP" -> R.drawable.ic_egypt
        "EUR" -> R.drawable.ic_european_union
        "ETB" -> R.drawable.ic_ethiopia

        "FJD" -> R.drawable.ic_fiji

        "GHS" -> R.drawable.ic_ghana
        "GBP" -> R.drawable.ic_united_kingdom
        "GEL" -> R.drawable.ic_georgia

        "HKD" -> R.drawable.ic_hong_kong
        "HRK" -> R.drawable.ic_croatia

        "INR" -> R.drawable.ic_india
        "ISK" -> R.drawable.ic_iceland
        "IQD" -> R.drawable.ic_iraq
        "IRR" -> R.drawable.ic_iran
        "ILS" -> R.drawable.ic_israel

        "JMD" -> R.drawable.ic_jamaica
        "JPY" -> R.drawable.ic_japan

        "KHR" -> R.drawable.ic_cambodia
        "KZT" -> R.drawable.ic_kazakhstan
        "KWD" -> R.drawable.ic_kuwait


        "MNT" -> R.drawable.ic_mongolia
        "MYR" -> R.drawable.ic_malaysia

        "PKR" -> R.drawable.ic_pakistan
        "PLN" -> R.drawable.ic_poland


        "RUB" -> R.drawable.ic_russia

        "SYS" -> R.drawable.ic_el_salvador
        "SZL" -> R.drawable.ic_swaziland

        "TRY" -> R.drawable.ic_turkey
        "TMT" -> R.drawable.ic_turkmenistan

        "USD" -> R.drawable.ic_united_states

        else -> R.drawable.bg_balance
    }
    return returnResource
}

fun String.getCurrencySymbolResource(): String{
    var returnResource = ""
    returnResource = when(this){
        "AED"->"United Arab Emirates Dirham"
        "AFN"->"Afghan Afghani"
        "ALL"->"Albanian Lek"
        "AMD"->"Armenian Dram"
        "ANG"->"Netherlands Antillean Guilder"
        "AOA"->"Angolan Kwanza"
        "ARS"->"Argentine Peso"
        "AUD"->"Australian Dollar"
        "AWG"->"Aruban Florin"
        "AZN"->"Azerbaijani Manat"
        "BAM"->"Bosnia-Herzegovina Convertible Mark"
        "BBD"->"Barbadian Dollar"
        "BDT"->"Bangladeshi Taka"
        "BGN"->"Bulgarian Lev"
        "BHD"->"Bahraini Dinar"
        "BIF"->"Burundian Franc"
        "BMD"->"Bermudan Dollar"
        "BND"->"Brunei Dollar"
        "BOB"->"Bolivian Boliviano"
        "BRL"->"Brazilian Real"
        "BSD"->"Bahamian Dollar"
        "BTC"->"Bitcoin"
        "BTN"->"Bhutanese Ngultrum"
        "BWP"->"Botswanan Pula"
        "BYN"->"Belarusian Ruble"
        "BZD"->"Belize Dollar"
        "CAD"->"Canadian Dollar"
        "CDF"->"Congolese Franc"
        "CHF"->"Swiss Franc"
        "CLF"->"Chilean Unit of Account (UF)"
        "CLP"->"Chilean Peso"
        "CNH"->"Chinese Yuan (Offshore)"
        "CNY"->"Chinese Yuan"
        "COP"->"Colombian Peso"
        "CRC"->"Costa Rican Colón"
        "CUC"->"Cuban Convertible Peso"
        "CUP"->"Cuban Peso"
        "CVE"->"Cape Verdean Escudo"
        "CZK"->"Czech Republic Koruna"
        "DJF"->"Djiboutian Franc"
        "DKK"->"Danish Krone"
        "DOP"->"Dominican Peso"
        "DZD"->"Algerian Dinar"
        "EGP"->"Egyptian Pound"
        "ERN"->"Eritrean Nakfa"
        "ETB"->"Ethiopian Birr"
        "EUR"->"Euro"
        "FJD"->"Fijian Dollar"
        "FKP"->"Falkland Islands Pound"
        "GBP"->"British Pound Sterling"
        "GEL"->"Georgian Lari"
        "GGP"->"Guernsey Pound"
        "GHS"->"Ghanaian Cedi"
        "GIP"->"Gibraltar Pound"
        "GMD"->"Gambian Dalasi"
        "GNF"->"Guinean Franc"
        "GTQ"->"Guatemalan Quetzal"
        "GYD"->"Guyanaese Dollar"
        "HKD"->"Hong Kong Dollar"
        "HNL"->"Honduran Lempira"
        "HRK"->"Croatian Kuna"
        "HTG"->"Haitian Gourde"
        "HUF"->"Hungarian Forint"
        "IDR"->"Indonesian Rupiah"
        "ILS"->"Israeli New Sheqel"
        "IMP"->"Manx pound"
        "INR"->"Indian Rupee"
        "IQD"->"Iraqi Dinar"
        "IRR"->"Iranian Rial"
        "ISK"->"Icelandic Króna"
        "JEP"->"Jersey Pound"
        "JMD"->"Jamaican Dollar"
        "JOD"->"Jordanian Dinar"
        "JPY"->"Japanese Yen"
        "KES"->"Kenyan Shilling"
        "KGS"->"Kyrgystani Som"
        "KHR"->"Cambodian Riel"
        "KMF"->"Comorian Franc"
        "KPW"->"North Korean Won"
        "KRW"->"South Korean Won"
        "KWD"->"Kuwaiti Dinar"
        "KYD"->"Cayman Islands Dollar"
        "KZT"->"Kazakhstani Tenge"
        "LAK"->"Laotian Kip"
        "LBP"->"Lebanese Pound"
        "LKR"->"Sri Lankan Rupee"
        "LRD"->"Liberian Dollar"
        "LSL"->"Lesotho Loti"
        "LYD"->"Libyan Dinar"
        "MAD"->"Moroccan Dirham"
        "MDL"->"Moldovan Leu"
        "MGA"->"Malagasy Ariary"
        "MKD"->"Macedonian Denar"
        "MMK"->"Myanma Kyat"
        "MNT"->"Mongolian Tugrik"
        "MOP"->"Macanese Pataca"
        "MRU"->"Mauritanian Ouguiya"
        "MUR"->"Mauritian Rupee"
        "MVR"->"Maldivian Rufiyaa"
        "MWK"->"Malawian Kwacha"
        "MXN"->"Mexican Peso"
        "MYR"->"Malaysian Ringgit"
        "MZN"->"Mozambican Metical"
        "NAD"->"Namibian Dollar"
        "NGN"->"Nigerian Naira"
        "NIO"->"Nicaraguan Córdoba"
        "NOK"->"Norwegian Krone"
        "NPR"->"Nepalese Rupee"
        "NZD"->"New Zealand Dollar"
        "OMR"->"Omani Rial"
        "PAB"->"Panamanian Balboa"
        "PEN"->"Peruvian Nuevo Sol"
        "PGK"->"Papua New Guinean Kina"
        "PHP"->"Philippine Peso"
        "PKR"->"Pakistani Rupee"
        "PLN"->"Polish Zloty"
        "PYG"->"Paraguayan Guarani"
        "QAR"->"Qatari Rial"
        "RON"->"Romanian Leu"
        "RSD"->"Serbian Dinar"
        "RUB"->"Russian Ruble"
        "RWF"->"Rwandan Franc"
        "SAR"->"Saudi Riyal"
        "SBD"->"Solomon Islands Dollar"
        "SCR"->"Seychellois Rupee"
        "SDG"->"Sudanese Pound"
        "SEK"->"Swedish Krona"
        "SGD"->"Singapore Dollar"
        "SHP"->"Saint Helena Pound"
        "SLL"->"Sierra Leonean Leone"
        "SOS"->"Somali Shilling"
        "SRD"->"Surinamese Dollar"
        "SSP"->"South Sudanese Pound"
        "STD"->"São Tomé and Príncipe Dobra (pre-2018)"
        "STN"->"São Tomé and Príncipe Dobra"
        "SVC"->"Salvadoran Colón"
        "SYP"->"Syrian Pound"
        "SZL"->"Swazi Lilangeni"
        "THB"->"Thai Baht"
        "TJS"->"Tajikistani Somoni"
        "TMT"->"Turkmenistani Manat"
        "TND"->"Tunisian Dinar"
        "TOP"->"Tongan Pa'anga"
        "TRY"->"Turkish Lira"
        "TTD"->"Trinidad and Tobago Dollar"
        "TWD"->"New Taiwan Dollar"
        "TZS"->"Tanzanian Shilling"
        "UAH"->"Ukrainian Hryvnia"
        "UGX"->"Ugandan Shilling"
        "USD"->"United States Dollar"
        "UYU"->"Uruguayan Peso"
        "UZS"->"Uzbekistan Som"
        "VEF"->"Venezuelan Bolívar Fuerte (Old)"
        "VES"->"Venezuelan Bolívar Soberano"
        "VND"->"Vietnamese Dong"
        "VUV"->"Vanuatu Vatu"
        "WST"->"Samoan Tala"
        "XAF"->"CFA Franc BEAC"
        "XAG"->"Silver Ounce"
        "XAU"->"Gold Ounce"
        "XCD"->"East Caribbean Dollar"
        "XDR"->"Special Drawing Rights"
        "XOF"->"CFA Franc BCEAO"
        "XPD"->"Palladium Ounce"
        "XPF"->"CFP Franc"
        "XPT"->"Platinum Ounce"
        "YER"->"Yemeni Rial"
        "ZAR"->"South African Rand"
        "ZMW"->"Zambian Kwacha"
        "ZWL"->"Zimbabwean Dollar"
        else -> "Unknown"
    }
    return returnResource
}