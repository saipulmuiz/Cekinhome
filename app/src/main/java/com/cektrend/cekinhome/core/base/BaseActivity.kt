package com.cektrend.cekinhome.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import java.lang.reflect.ParameterizedType

/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
abstract class BaseActivity<T : ViewDataBinding, V : ViewModel> : AppCompatActivity(){

    val NO_VIEW_MODEL_BINDING_VARIABLE = -1

    private lateinit var mViewModel: V
    private lateinit var mViewDataBinding: T

    abstract fun getViewModelBindingVariable() : Int

    @LayoutRes
    abstract fun getLayoutId() : Int

    fun getViewModel() : V = mViewModel

    fun getDataBinding() : T = mViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        performDataBinding()
//        provideViewModel()

    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        if (getViewModelBindingVariable() != NO_VIEW_MODEL_BINDING_VARIABLE) {
            setViewModelBindingVariable()
        }
    }

    private fun setViewModelBindingVariable() {
        mViewDataBinding.setVariable(getViewModelBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
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