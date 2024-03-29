package co.id.signaturedragable.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any, L : LiveData<T>> Fragment.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(body))
}

inline fun <T : Any, L : LiveData<T>> Fragment.observeNonNull(
    liveData: L,
    crossinline body: (T) -> Unit
) {
    liveData.observe(viewLifecycleOwner, Observer { it?.let(body) })
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

inline fun <T : Any, L : LiveData<T>> LifecycleOwner.observeNonNull(
    liveData: L,
    crossinline body: (T) -> Unit
) {
    liveData.observe(this, Observer { it?.let(body) })
}