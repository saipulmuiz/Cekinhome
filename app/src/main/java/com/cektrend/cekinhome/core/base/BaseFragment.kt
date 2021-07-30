package com.cektrend.cekinhome.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
abstract class BaseFragment<T : ViewDataBinding, V : ViewModel> : Fragment() {

    private val NO_VIEW_MODEL_BINDING_VARIABLE = -1

    lateinit var mViewModel: V
    lateinit var mViewDataBinding: T

    abstract fun getViewModelBindingVariable() : Int

    @LayoutRes
    abstract fun getLayoutId() : Int

    fun getViewModel() : V = mViewModel

    fun getDataBinding() : T = mViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (getViewModelBindingVariable() != NO_VIEW_MODEL_BINDING_VARIABLE) {
            mViewDataBinding.setVariable(getViewModelBindingVariable(), mViewModel)
            mViewDataBinding.executePendingBindings()
        }
    }
}