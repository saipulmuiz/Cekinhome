package com.cektrend.cekinhome.features.history

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.cektrend.cekinhome.R
import com.cektrend.cekinhome.data.db.entity.HistoryLog
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
class HistoryTableAdapter(context: Context, data: ArrayList<HistoryLog>) : ArrayAdapter<HistoryLog>(context, -1, data) {
    private val mContext: Context = context
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
        if (count > 0) {
            return count
        } else {
            return super.getViewTypeCount()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private var lastPosition = -1
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // val model: HistoryLog? = getItem(position)
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

        viewHolder.apply {
            if (position == 0) {
                tvNo?.text = StringBuilder("No.")
                tvDeviceName?.text = StringBuilder("Nama Device")
                tvKondisi?.text = StringBuilder("Kondisi")
                tvDate?.text = StringBuilder("Tanggal")
                tvNo?.setTypeface(null, Typeface.BOLD)
                tvDeviceName?.setTypeface(null, Typeface.BOLD)
                tvKondisi?.setTypeface(null, Typeface.BOLD)
                tvDate?.setTypeface(null, Typeface.BOLD)
            } else {
                val model: HistoryLog? = getItem(position - 1)
                val date: String = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(model?.dateInfo)
                tvNo?.text = position.toString()
                tvDeviceName?.text = model?.deviceName
                tvKondisi?.text = model?.conditionState
                tvDate?.text = date
            }
        }
        return view
    }
}