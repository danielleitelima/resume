package com.danielleitelima.resume.activity

class IMainViewModel : MainViewModel() {

    override fun setInitialState(): MainContract.State {
        return MainContract.State()
    }

    override fun handleEvents(event: MainContract.Event) {
    }
}
