package loan.exchange.setting.util

import java.util.Locale


object UnitConverter {
    /**
     * Convert a unit to another
     * @param amount the number
     * @param from the unit the number is in
     * @param to the unit the number should be converted to
     * @return the converted amount
     * @throws IncompatibleUnitTypesException when the units have different UnitTypes (are not from the same category)
     */
    @Throws(IncompatibleUnitTypesException::class)
    fun convert(amount: Double, from: Unit, to: Unit): Double {
        if (from.type != to.type) {
            throw IncompatibleUnitTypesException()
        }
        val based: Double = if (from.isBase) {
            amount
        } else {
            from.to!!.convert(amount)
        }
        val result: Double = if (to.isBase) {
            based
        } else {
            to.from!!.convert(based)
        }
        return result
    }

    enum class Unit {
        //BASE UNITS
        KELVIN(
            UnitType.TEMPERATURE,
            "K",
            UnitSystem.METRIC,
            "Kelvin",
            Aliases("°k", "kelvin")
        ),
        KILOGRAM(
            UnitType.MASS,
            "kg",
            UnitSystem.METRIC,
            "Kilogram",
            Aliases("kilos", "kilo", "kilogram", "kgs", "kilograms")
        ),
        METRE(
            UnitType.LENGTH,
            "m",
            UnitSystem.METRIC,
            "Metre",
            Aliases("meter", "meters", "metres", "metre")
        ),
        LITRE(
            UnitType.VOLUME,
            "l",
            UnitSystem.METRIC,
            "Litre",
            Aliases("litre", "litres", "liter", "liters")
        ),
        SQ_METRE(
            UnitType.AREA,
            "m²",
            UnitSystem.METRIC,
            "Square metre"),  //Temperature
        FAHRENHEIT(
            UnitType.TEMPERATURE,
            "F",
            UnitSystem.IMPERIAL,
            "Fahrenheit",
            KELVIN,
            object : Converter {
                override fun convert(amount: Double): Double {
                    return 5.0 / 9.0 * (amount - 32.0) + 273.0
                }
            },
            object : Converter {
                override fun convert(amount: Double): Double {
                    return 9.0 / 5.0 * (amount - 273.0) + 32.0
                }
            },
            Aliases("°F", "fahrenheit", "farenheit")
        ),
        CELSIUS(
            UnitType.TEMPERATURE, "C", UnitSystem.METRIC, "Celsius", KELVIN,
            object : Converter {
                override fun convert(amount: Double): Double {
                    return amount + 273.15
                }
            },
            object : Converter {
                override fun convert(amount: Double): Double {
                    return amount - 273.15
                }
            },
            Aliases("°C", "celsius")
        ),  //MASS
        OUNCE(
            UnitType.MASS,
            "oz",
            UnitSystem.IMPERIAL,
            "Ounce",
            KILOGRAM,
            0.0283495,
            35.274,
            Aliases("ounce", "ounces")
        ),
        POUND(
            UnitType.MASS,
            "lb",
            UnitSystem.IMPERIAL,
            "Pound",
            KILOGRAM,
            0.453592,
            2.20462,
            Aliases("pounds", "lbs", "pound")
        ),
        STONE(
            UnitType.MASS,
            "st",
            UnitSystem.IMPERIAL,
            "Stone",
            KILOGRAM,
            6.35029,
            0.157473,
            Aliases("stone", "stones", "sts")
        ),
        IMPERIAL_TON(
            UnitType.MASS,
            "tn",
            UnitSystem.IMPERIAL,
            "Imperial Tonne",
            KILOGRAM,
            0.157473,
            0.000984207
        ),
        GRAM(
            UnitType.MASS,
            "g",
            UnitSystem.METRIC,
            "Gram",
            KILOGRAM,
            0.001,
            1000.0,
            Aliases("gram", "grams", "gramm", "gramms")
        ),
        TONNE(
            UnitType.MASS,
            "t",
            UnitSystem.METRIC,
            "Tonne",
            KILOGRAM,
            1000.0,
            0.001,
            Aliases("ton", "tons", "tonnes", "tns")
        ),  //LENGTH
        INCH(
            UnitType.LENGTH,
            "in",
            UnitSystem.IMPERIAL,
            "Inch",
            METRE,
            0.0254,
            39.3701,
            Aliases("inch", "inchs", "inches")
        ),
        FOOT(
            UnitType.LENGTH,
            "ft",
            UnitSystem.IMPERIAL,
            "Feet",
            METRE,
            0.3048,
            3.28084,
            Aliases("foot", "foots")
        ),
        YARD(
            UnitType.LENGTH,
            "yd",
            UnitSystem.IMPERIAL,
            "Yard",
            METRE,
            0.9144,
            1.09361,
            Aliases("yard", "yards")
        ),
        MILE(
            UnitType.LENGTH,
            "mi",
            UnitSystem.IMPERIAL,
            "Mile",
            METRE,
            1609.34,
            0.000621371,
            Aliases("mile", "ml", "miles", "mis")
        ),
        MILLIMETRE(
            UnitType.LENGTH,
            "mm",
            UnitSystem.METRIC,
            "Millimetre",
            METRE,
            0.001,
            1000.0,
            Aliases("millis", "millimetre", "millimeter", "millimeters", "millimetres")
        ),
        CENTIMETRE(
            UnitType.LENGTH,
            "cm",
            UnitSystem.METRIC,
            "Centimetre",
            METRE,
            0.01,
            100.0,
            Aliases("centis", "centimetre", "centimeter", "centimetres", "centimeters")
        ),
        KILOMETRE(
            UnitType.LENGTH,
            "km",
            UnitSystem.METRIC,
            "Kilometre",
            METRE,
            1000.0,
            0.001,
            Aliases("kilometres", "kilometers", "kms", "kilometre", "kilometer")
        ),  //VOLUME
        FLUID_OUNCE(
            UnitType.VOLUME,
            "floz",
            UnitSystem.IMPERIAL,
            "Fluid ounce",
            LITRE,
            0.0284131,
            35.1951
        ),
        PINT(
            UnitType.VOLUME,
            "pt",
            UnitSystem.IMPERIAL,
            "Pint",
            LITRE,
            0.568261,
            1.75975,
            Aliases("pint", "pints", "pts")
        ),
        QUART(
            UnitType.VOLUME,
            "qt",
            UnitSystem.IMPERIAL,
            "Quart",
            LITRE,
            1.13652,
            0.879877,
            Aliases("quart", "quarts")
        ),
        GALLON(
            UnitType.VOLUME,
            "gal",
            UnitSystem.IMPERIAL,
            "Gallon",
            LITRE,
            4.54609,
            0.219969,
            Aliases("gallon", "gallons")
        ),
        MILLILITRE(
            UnitType.VOLUME, "ml", UnitSystem.METRIC, "Millilitre", LITRE, 0.001, 1000.0,
            Aliases("millilitres", "milliliters", "milliliter", "millilitre")
        ),  //AREA
        SQ_INCH(
            UnitType.AREA,
            "in²",
            UnitSystem.IMPERIAL,
            "Square inch",
            SQ_METRE,
            0.00064516,
            1550.0
        ),
        SQ_FOOT(
            UnitType.AREA,
            "ft²",
            UnitSystem.IMPERIAL,
            "Square foot",
            SQ_METRE,
            0.092903,
            10.7639
        ),
        SQ_MILE(
            UnitType.AREA,
            "ml²",
            UnitSystem.IMPERIAL,
            "Square mile",
            SQ_METRE,
            2589988.0,
            0.0000003861
        ),
        SQ_YARD(
            UnitType.AREA,
            "yd²",
            UnitSystem.IMPERIAL,
            "Square yard",
            SQ_METRE,
            0.836127,
            1.19599
        ),
        ACRE(
            UnitType.AREA, "ac", UnitSystem.IMPERIAL, "Acre", SQ_METRE, 4046.86, 0.000247105,
            Aliases("acre", "acres")
        ),
        HECTARE(
            UnitType.AREA, "ha", UnitSystem.METRIC, "Hectare", SQ_METRE, 1000.0, 0.0001,
            Aliases("hectare", "hectars", "hectares", "hectar")
        ),
        SQ_KILOMETRE(
            UnitType.AREA,
            "km²",
            UnitSystem.METRIC,
            "Square kilometres",
            SQ_METRE,
            1000000.0,
            0.000001
        );

        var type: UnitType
            private set
        var symbol: String
            private set
        var system: UnitSystem
            private set
        var names: String
            private set
        var reference: Unit?
            private set
        var to: Converter?
            private set
        var from: Converter?
            private set
        var isBase: Boolean
            private set
        private var aliases: Aliases?
        var equivalent: Unit? = null
            private set

        /**
         * Creates a new Unit
         * @param type unit category
         * @param unit short name for this unit
         * @param system unit system to use (imperial/metric)
         * @param name unit name
         * @param reference the base unit of this category to convert to
         * @param multtoref the amount to multiply the amount with to convert it to the base unit
         */
        constructor(
            type: UnitType,
            unit: String,
            system: UnitSystem,
            names: String,
            reference: Unit,
            multtoref: Double,
            multfromref: Double,
            aliases: Aliases
        ) {
            this.type = type
            symbol = unit
            this.system = system
            this.names = names
            this.reference = reference
            to = MultiplicationConverter(multtoref)
            from = MultiplicationConverter(multfromref)
            isBase = false
            this.aliases = aliases
        }

        /**
         * Creates a new Unit
         * @param type unit category
         * @param unit short name for this unit
         * @param system unit system to use (imperial/metric)
         * @param name unit name
         * @param reference the base unit of this category to convert to
         * @param multtoref the amount to multiply the amount with to convert it to the base unit
         */
        constructor(
            type: UnitType,
            unit: String,
            system: UnitSystem,
            names: String,
            reference: Unit,
            multtoref: Double,
            multfromref: Double
        ) {
            this.type = type
            symbol = unit
            this.system = system
            this.names = names
            this.reference = reference
            to = MultiplicationConverter(multtoref)
            from = MultiplicationConverter(multfromref)
            isBase = false
            aliases = null
        }

        /**
         * Creates a new Unit
         * @param type unit category
         * @param unit short name for this unit
         * @param system unit system to use (imperial/metric)
         * @param name unit name
         * @param reference the base unit of this category to convert to
         */
        constructor(
            type: UnitType,
            unit: String,
            system: UnitSystem,
            names: String,
            reference: Unit,
            converterto: Converter,
            converterfrom: Converter
        ) {
            this.type = type
            symbol = unit
            this.system = system
            this.names = names
            this.reference = reference
            to = converterto
            from = converterfrom
            isBase = false
            aliases = null
        }

        /**
         * Creates a new Unit
         * @param type unit category
         * @param unit short name for this unit
         * @param system unit system to use (imperial/metric)
         * @param name unit name
         * @param reference the base unit of this category to convert to
         */
        constructor(
            type: UnitType,
            unit: String,
            system: UnitSystem,
            names: String,
            reference: Unit,
            converterto: Converter,
            converterfrom: Converter,
            aliases: Aliases
        ) {
            this.type = type
            symbol = unit
            this.system = system
            this.names = names
            this.reference = reference
            to = converterto
            from = converterfrom
            isBase = false
            this.aliases = aliases
        }

        /**
         * Creates a new Base Unit
         * @param type unit category
         * @param unit short name for this unit
         * @param system unit system to use (imperial/metric)
         * @param name unit name
         */
        constructor(type: UnitType, unit: String, system: UnitSystem, names: String) {
            this.type = type
            symbol = unit
            this.system = system
            this.names = names
            reference = null
            to = null
            from = null
            isBase = true
            aliases = null
        }

        /**
         * Creates a new Base Unit
         * @param type unit category
         * @param unit short name for this unit
         * @param system unit system to use (imperial/metric)
         * @param name unit name
         */
        constructor(
            type: UnitType,
            unit: String,
            system: UnitSystem,
            names: String,
            aliases: Aliases
        ) {
            this.type = type
            symbol = unit
            this.system = system
            this.names = names
            reference = null
            to = null
            from = null
            isBase = true
            this.aliases = aliases
        }

        fun hasAlias(al: String?): Boolean {
            return if (aliases == null) false else aliases!!.contains(al)
        }

        companion object {
            init {
                KILOGRAM.equivalent = POUND
                METRE.equivalent = YARD
                LITRE.equivalent = PINT
                SQ_METRE.equivalent = SQ_FOOT
                FAHRENHEIT.equivalent = CELSIUS
                CELSIUS.equivalent = FAHRENHEIT
                OUNCE.equivalent = GRAM
                POUND.equivalent = KILOGRAM
                STONE.equivalent = KILOGRAM
                IMPERIAL_TON.equivalent = TONNE
                GRAM.equivalent = OUNCE
                TONNE.equivalent = IMPERIAL_TON
                INCH.equivalent = CENTIMETRE
                FOOT.equivalent = CENTIMETRE
                YARD.equivalent = METRE
                MILE.equivalent = KILOMETRE
                MILLIMETRE.equivalent = INCH
                CENTIMETRE.equivalent = INCH
                KILOMETRE.equivalent = MILE
                FLUID_OUNCE.equivalent = MILLILITRE
                PINT.equivalent = LITRE
                QUART.equivalent = LITRE
                GALLON.equivalent = LITRE
                MILLILITRE.equivalent = FLUID_OUNCE
                SQ_INCH.equivalent = SQ_METRE
                SQ_FOOT.equivalent = SQ_METRE
                SQ_MILE.equivalent = SQ_KILOMETRE
                SQ_YARD.equivalent = SQ_METRE
                ACRE.equivalent = SQ_KILOMETRE
                HECTARE.equivalent = SQ_FOOT
                SQ_KILOMETRE.equivalent = SQ_MILE
            }

            fun getBySymbol(sym: String?): Unit? {
                for (unit in values()) {
                    if (unit.symbol.equals(sym, ignoreCase = true)) return unit
                }
                return null
            }

            fun getByType(type: UnitType): Collection<Unit> {
                val col: MutableCollection<Unit> = ArrayList()
                for (unit in values()) {
                    if (unit.type == type) col.add(unit)
                }
                return col
            }

            fun getByAlias(alias: String): Unit? {
                for (unit in values()) {
                    if (unit.hasAlias(alias.lowercase(Locale.getDefault()))) return unit
                }
                return null
            }

            fun getByTyped(typed: String): Unit? {
                var unit = getBySymbol(typed)
                if (unit != null) return unit
                unit = getByAlias(typed)
                return unit
            }
        }
    }

    enum class UnitType(val names: String) {
        TEMPERATURE("Temperature"), LENGTH("Length"), AREA("Area"), VOLUME("Volume"), MASS("Mass");

    }

    enum class UnitSystem(val names: String) {
        IMPERIAL("Imperial"), METRIC("Metric");

    }

    class Aliases(vararg aliases: String) {
        var aliases = arrayListOf<String>()

        init {
            this.aliases.addAll(aliases)
        }

        val aliasesCollection: List<String>
            get() = aliases

        operator fun contains(text: String?): Boolean {
            return aliasesCollection.contains(text)
        }
    }

    // CONVERTERS
    interface Converter {
        fun convert(amount: Double): Double
    }

    private class MultiplicationConverter(private val fac: Double) : Converter {
        override fun convert(amount: Double): Double {
            return amount * fac
        }
    }

    //EXCEPTION
    class IncompatibleUnitTypesException : Exception {
        constructor(message: String?) : super(message) {}
        constructor() : super() {}
    }
}