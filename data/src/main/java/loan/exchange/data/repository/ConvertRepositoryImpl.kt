package loan.exchange.data.repository

import android.content.Context
import loan.exchange.data.dummy.GetConvertUnitValues
import loan.exchange.domain.repository.ConvertRepository
import javax.inject.Inject

class ConvertRepositoryImpl @Inject constructor(
    private val context: Context,
    private val getConvertUnitValues: GetConvertUnitValues,
) : ConvertRepository {

    override fun getMeasurements() = getConvertUnitValues.getMeasurements(context)

}