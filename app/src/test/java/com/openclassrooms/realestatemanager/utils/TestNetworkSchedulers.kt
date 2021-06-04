package com.openclassrooms.realestatemanager.utils

import com.openclassrooms.realestatemanager.presenter.protocols.utils.NetworkSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class TestNetworkSchedulers : NetworkSchedulers {
    override val io: Scheduler
        get() = Schedulers.trampoline()
    override val main: Scheduler
        get() = Schedulers.trampoline()
}
