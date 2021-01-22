package dev.stanislav.testtask.functional

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.subjects.PublishSubject

fun <T> Single<T>.composeRetryWhen(subject: PublishSubject<Boolean>): Single<T> {
    val transformer = SingleTransformer<T, T> { single ->
        return@SingleTransformer single.retryWhen { errors ->
            errors.zipWith(
                subject.toFlowable(BackpressureStrategy.LATEST),
                { throwable: Throwable, retry: Boolean ->
                    if (retry) {
                        Flowable.just(throwable)
                    } else {
                        Flowable.error(throwable)
                    }
                })
        }
    }
    return compose(transformer)
}