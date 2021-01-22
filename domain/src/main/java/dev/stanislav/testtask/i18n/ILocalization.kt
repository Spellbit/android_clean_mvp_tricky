package dev.stanislav.testtask.i18n

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface ILocalization {
    fun sync(): Completable
    fun getStringHolder(): Single<StringHolder>
    fun getLangObservable(): Observable<StringHolder>
    fun UI(f: (stringHolder: StringHolder) -> Unit): Disposable
    fun UISingle(f: (stringHolder: StringHolder) -> Unit): Disposable
}