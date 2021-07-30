package com.cektrend.cekinhome.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import java.lang.reflect.ParameterizedType

/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
abstract class BaseActivity<V : ViewModel> : AppCompatActivity() {

    val NO_VIEW_MODEL_BINDING_VARIABLE = -1

    private lateinit var mViewModel: V

    abstract fun getViewModelBindingVariable(): Int

    @LayoutRes
    abstract fun getLayoutId(): Int

    fun getViewModel(): V = mViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        provideViewModel()
    }

    private fun provideViewModel() {
        val clazz = getViewModelClass(javaClass)
    }

    private fun getViewModelClass(aClass: Class<*>): Class<V> {
        val type = aClass.genericSuperclass

        return if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<V>
        } else {
            getViewModelClass(aClass.superclass as Class<*>)
        }
    }

}