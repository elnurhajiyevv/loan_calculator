package loan.exchange.setting.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import loan.exchange.core.base.BaseViewModel
import loan.exchange.domain.entity.enums.UNIT_TYPE
import loan.exchange.setting.effect.ConvertUnitDetailEffect
import loan.exchange.setting.state.ConvertUnitDetailState
import loan.exchange.setting.util.UnitConverter
import javax.inject.Inject

@HiltViewModel
class ConvertUnitDetailViewModel @Inject constructor(

) : BaseViewModel<ConvertUnitDetailState, ConvertUnitDetailEffect>(){

    fun updateValues(unitType: UNIT_TYPE) {
        when(unitType){
            UNIT_TYPE.WEIGHT ->{
                fromConverter.clear()
                toConverter.clear()
                fromConverter.add(UnitConverter.Unit.KILOGRAM)
                fromConverter.add(UnitConverter.Unit.GRAM)
                fromConverter.add(UnitConverter.Unit.IMPERIAL_TON)
                fromConverter.add(UnitConverter.Unit.TONNE)
                fromConverter.add(UnitConverter.Unit.STONE)
                fromConverter.add(UnitConverter.Unit.POUND)
                fromConverter.add(UnitConverter.Unit.OUNCE)
                toConverter.addAll(fromConverter)
            }
            UNIT_TYPE.VOLUME ->{
                fromConverter.clear()
                toConverter.clear()
                fromConverter.add(UnitConverter.Unit.FLUID_OUNCE)
                fromConverter.add(UnitConverter.Unit.PINT)
                fromConverter.add(UnitConverter.Unit.QUART)
                fromConverter.add(UnitConverter.Unit.GALLON)
                fromConverter.add(UnitConverter.Unit.MILLILITRE)
                fromConverter.add(UnitConverter.Unit.LITRE)
                toConverter.addAll(fromConverter)
            }
            UNIT_TYPE.TEMPERATURE ->{
                fromConverter.clear()
                toConverter.clear()
                fromConverter.add(UnitConverter.Unit.CELSIUS)
                fromConverter.add(UnitConverter.Unit.FAHRENHEIT)
                fromConverter.add(UnitConverter.Unit.KELVIN)
                toConverter.addAll(fromConverter)

            }
            UNIT_TYPE.LENGTH ->{
                fromConverter.clear()
                toConverter.clear()
                fromConverter.add(UnitConverter.Unit.INCH)
                fromConverter.add(UnitConverter.Unit.FOOT)
                fromConverter.add(UnitConverter.Unit.METRE)
                fromConverter.add(UnitConverter.Unit.YARD)
                fromConverter.add(UnitConverter.Unit.MILE)
                fromConverter.add(UnitConverter.Unit.MILLIMETRE)
                fromConverter.add(UnitConverter.Unit.CENTIMETRE)
                fromConverter.add(UnitConverter.Unit.KILOMETRE)
                toConverter.addAll(fromConverter)
            }
            UNIT_TYPE.SPEED ->{

            }
            UNIT_TYPE.AREA ->{
                fromConverter.clear()
                toConverter.clear()
                fromConverter.add(UnitConverter.Unit.SQ_INCH)
                fromConverter.add(UnitConverter.Unit.SQ_FOOT)
                fromConverter.add(UnitConverter.Unit.SQ_MILE)
                fromConverter.add(UnitConverter.Unit.SQ_YARD)
                fromConverter.add(UnitConverter.Unit.SQ_METRE)
                fromConverter.add(UnitConverter.Unit.ACRE)
                fromConverter.add(UnitConverter.Unit.HECTARE)
                fromConverter.add(UnitConverter.Unit.SQ_KILOMETRE)

                toConverter.addAll(fromConverter)

            }
            UNIT_TYPE.TIME ->{

            }
            UNIT_TYPE.PRESSURE ->{

            }
            UNIT_TYPE.STORAGE ->{

            }
        }
    }

    var fromConverter = arrayListOf<UnitConverter.Unit>()
    var toConverter = arrayListOf<UnitConverter.Unit>()

    var selectedFromConverter = UnitConverter.Unit.KILOGRAM
    var selectedToConverter = UnitConverter.Unit.GRAM
    var rate = 1.0

}
