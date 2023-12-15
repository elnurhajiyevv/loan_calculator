package loan.exchange.home.state

import loan.exchange.domain.entity.home.response.LatestRatesResponseModel
import loan.exchange.home.view.HomePageFragment

sealed class HomePageState {

    class GetLatestExchangeRates(var type: HomePageFragment.SELECTION_TYPE, var latestRatesResponseModel: LatestRatesResponseModel): HomePageState()

    object GetLatestExchangeRatesErrorResult: HomePageState()
}