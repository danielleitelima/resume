package com.danielleitelima.resume.presentation.activity

class IMainViewModel : MainViewModel() {

    override fun setInitialState(): MainContract.State {
        return MainContract.State()
    }

    override fun handleEvents(event: MainContract.Event) {
    }
}
