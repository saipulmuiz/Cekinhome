package com.cektrend.cekinhome

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.cektrend.cekinhome.data.db.entity.HistoryLog
/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
class HistoryTableAdapter(context: Context, data: ArrayList<HistoryLog>) : ArrayAdapter<HistoryLog>(context, -1, data) {
    private val mContext: Context = context
    private val dataSet: ArrayList<HistoryLog> = data
    private val headerColor = Color.parseColor("#0062cc")

    class ViewHolder(row: View?) {
        var layout: LinearLayout? = null
        var tvNo: TextView? = null
        var tvDeviceName: TextView? = null
        var tvKondisi: TextView? = null
        var tvDate: TextView? = null

        init {
            layout = row?.findViewById(R.id.listLayout) as LinearLayout
            tvNo = row.findViewById(R.id.tv_no) as TextView
            tvDeviceName = row.findViewById(R.id.tv_device) as TextView
            tvKondisi = row.findViewById(R.id.tv_condition) as TextView
            tvDate = row.findViewById(R.id.tv_date) as TextView
        }
    }

    override fun getViewTypeCount(): Int {
        return super.getCount()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private var lastPosition = -1
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val model: HistoryLog? = getItem(position)
        val viewHolder: ViewHolder
        val view: View

        if (convertView == null) {
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_history_table, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
            viewHolder.tvNo?.text = ""
            viewHolder.tvDeviceName?.text = ""
            viewHolder.tvKondisi?.text = ""
            viewHolder.tvDate?.text = ""
        }

        lastPosition = position

        if (model?.id != null) {
            viewHolder.tvNo?.text = model.id.toString()
            viewHolder.tvDeviceName?.text = model.deviceName.toString()
            viewHolder.tvKondisi?.text = model.conditionState.toString()
            viewHolder.tvDate?.text = model.dateInfo.toString()
            viewHolder.layout?.setBackgroundColor(headerColor)
            viewHolder.tvNo?.setTypeface(null, Typeface.BOLD)
            viewHolder.tvDeviceName?.setTypeface(null, Typeface.BOLD)
            viewHolder.tvKondisi?.setTypeface(null, Typeface.BOLD)
            viewHolder.tvDate?.setTypeface(null, Typeface.BOLD)
            viewHolder.tvNo?.setTextColor(Color.WHITE)
            viewHolder.tvDeviceName?.setTextColor(Color.WHITE)
            viewHolder.tvKondisi?.setTextColor(Color.WHITE)
            viewHolder.tvDate?.setTextColor(Color.WHITE)
        } else {
            viewHolder.tvNo?.text = model?.id.toString()
            viewHolder.tvDeviceName?.text = model?.deviceName
            viewHolder.tvKondisi?.text = model?.conditionState
            viewHolder.tvDate?.text = model?.dateInfo.toString()
            viewHolder.layout?.setBackgroundColor(Color.WHITE)
            viewHolder.tvNo?.setTypeface(null, Typeface.NORMAL)
            viewHolder.tvDeviceName?.setTypeface(null, Typeface.NORMAL)
            viewHolder.tvKondisi?.setTypeface(null, Typeface.NORMAL)
            viewHolder.tvDate?.setTypeface(null, Typeface.NORMAL)
            viewHolder.tvNo?.setTextColor(Color.BLACK)
            viewHolder.tvDeviceName?.setTextColor(Color.BLACK)
            viewHolder.tvKondisi?.setTextColor(Color.BLACK)
            viewHolder.tvDate?.setTextColor(Color.BLACK)
        }
        return view
    }
}