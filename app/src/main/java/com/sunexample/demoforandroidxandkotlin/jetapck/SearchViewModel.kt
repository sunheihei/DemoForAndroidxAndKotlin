package com.sunexample.demoforandroidxandkotlin.jetapck

import androidx.lifecycle.*
import com.sunexample.demoforandroidxandkotlin.jetapck.bean.MusicBean
import com.sunexample.demoforandroidxandkotlin.jetapck.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) :
    ViewModel() {
    private var queryVideoLiveData = MutableLiveData<String>()

    var repoResult: LiveData<Resource<List<MusicBean>>> =
        Transformations.switchMap(queryVideoLiveData) {
            repository.searchvideo(it)
        }

    fun searchVideo(query: String) {
        queryVideoLiveData.postValue(query)
    }


}