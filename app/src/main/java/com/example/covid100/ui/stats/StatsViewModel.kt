package com.example.covid100.ui.stats

import androidx.lifecycle.*
import com.example.covid100.data.repositories.CovidRepository
import com.example.covid100.data.response.CovidStatResponse
import com.example.covid100.utils.Injector
import com.example.covid100.utils.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class StatsViewModel(
    private val repo: CovidRepository
) : ViewModel() {

    private val _covidCases = MutableLiveData<Result<CovidStatResponse>>()
    val covidCases: LiveData<Result<CovidStatResponse>> get() = _covidCases

    fun getCovidCases() = viewModelScope.launch {
        repo.getCovidStats().collect {
            _covidCases.postValue(it)
        }
    }

    init {
        getCovidCases()
    }


    companion object {
        private class StatsViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = Injector.getInstance().providesStatsRepository()
                return StatsViewModel(repo) as T
            }
        }

        fun getStatsViewModel(owner: ViewModelStoreOwner) : StatsViewModel {
            return ViewModelProvider(owner, StatsViewModelFactory())[StatsViewModel::class.java]
        }
    }
}