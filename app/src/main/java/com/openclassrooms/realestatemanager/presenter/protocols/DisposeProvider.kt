package com.openclassrooms.realestatemanager.presenter.protocols

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

interface DisposablePresenter<View> : Presenter<View>, DisposeProvider {
    override var view: View?
    
    override fun destroy() {
        super<Presenter>.destroy()
        super<DisposeProvider>.destroy()
    }
}

interface DisposeProvider : LifecycleObserver {
    val disposeBag: CompositeDisposable
        get() = CompositeDisposable()
    
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        disposeBag.dispose()
    }
}

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

interface Presenter<View> {
    var view: View?
    
    fun attach(view: View)
    fun destroy() { view = null }
}
