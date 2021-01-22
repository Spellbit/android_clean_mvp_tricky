package dev.stanislav.testtask.i18n

import android.annotation.SuppressLint
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

class AndroidResourceLocalization(val uiScheduler: Scheduler, val stringProvider: IStringProvider) : ILocalization {

    private var stringHolder: StringHolder? = null
    private val changeSubject = PublishSubject.create<StringHolder>()
    override fun sync() = getStringHolder().map { changeSubject.onNext(it) }.ignoreElement()
    override fun getStringHolder() = Single.just(stringHolder ?: StringHolder(stringProvider).apply { stringHolder = this })
    override fun getLangObservable() = getStringHolder().toObservable().concatWith(changeSubject)

    @SuppressLint("CheckResult")
    override fun UI(f: (stringHolder: StringHolder) -> Unit) = getLangObservable()
        .observeOn(uiScheduler)
        .subscribe {
            f(it)
        }

    @SuppressLint("CheckResult")
    override fun UISingle(f: (stringHolder: StringHolder) -> Unit) = getLangObservable()
        .observeOn(uiScheduler)
        .firstOrError()
        .subscribe { stringHolder ->
            f(stringHolder)
        }
}