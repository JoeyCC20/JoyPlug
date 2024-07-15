package com.joybox.joyplug.view_plugin_sample2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joybox.joyplug.plugcore.component.JoyBaseView
import com.joybox.joyplug.plugcore.delegate.HostViewDelegate
import com.joybox.joyplug.plugcore.service.HostServiceCenter
import com.joybox.joyplug.services.NetService
import org.json.JSONArray
import java.net.URL

class SampleListView(private val context: Context) : JoyBaseView(context) {
    override fun createContentView(parent: ViewGroup): View? {
        val contentView = LayoutInflater.from(context).inflate(R.layout.list_view_sample, parent)
        val recyclerView = contentView.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = MyAdapter(getHostViewDelegate(), context)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        //
        val netService = HostServiceCenter.getService<NetService>()
        val ret = netService?.GET(URL("https://www.test.com"))
        Log.i("PLUG", "Plug call GET() response:$ret")
        //
        return null
    }

    class MyAdapter(private val hostViewDelegate: HostViewDelegate?, private val context: Context) : RecyclerView.Adapter<MyHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            val view = View.inflate(context, R.layout.list_item_sample, null)
            return MyHolder(view)
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            when (position % 3) {
                0 -> holder.mIvIcon.setImageResource(R.drawable.ic_sample_1)
                1 -> holder.mIvIcon.setImageResource(R.drawable.ic_sample_2)
                2 -> holder.mIvIcon.setImageResource(R.drawable.ic_sample_3)
            }
            holder.mTvMsg.text = "This is message ${position+1}"
            holder.mTvMsg.setOnClickListener {
                Toast.makeText(context, "Item clicked : $position", Toast.LENGTH_SHORT).show()
                hostViewDelegate?.onClick(holder.mTvMsg)
            }
        }

    }

    class MyHolder(viewItem : View) : RecyclerView.ViewHolder(viewItem) {
        var mIvIcon : ImageView
        var mTvMsg : TextView

        init {
            mIvIcon = viewItem.findViewById(R.id.iv_icon)
            mTvMsg = viewItem.findViewById(R.id.tv_msg)
        }
    }
}