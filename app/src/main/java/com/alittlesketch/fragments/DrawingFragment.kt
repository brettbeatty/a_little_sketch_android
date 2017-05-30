package com.alittlesketch.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.alittlesketch.R
import com.alittlesketch.views.DrawingView


class DrawingFragment : Fragment(), View.OnClickListener {

    fun clear() {
        val drawing_view = view!!.findViewById(R.id.drawing_view) as DrawingView

        drawing_view.clear()
    }

    override fun onClick(view: View) {

        when (view.id) {
            R.id.clear -> clear()
            R.id.eraser -> toggleEraser()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val clear_button = view!!.findViewById(R.id.clear) as ImageButton
//        clear_button.setOnClickListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_drawing, container, false)
        val clear_button = view.findViewById(R.id.clear)
        val erase_button = view.findViewById(R.id.eraser)

        clear_button.setOnClickListener(this)
        erase_button.setOnClickListener(this)

        return view
    }

    fun toggleEraser() {
        val drawing_view = view!!.findViewById(R.id.drawing_view) as DrawingView
        val erase_button = view!!.findViewById(R.id.eraser) as ImageButton

        if (drawing_view.erasing) {

            erase_button.setImageResource(R.mipmap.eraser)
            drawing_view.stopErasing()
        } else {

            erase_button.setImageResource(R.mipmap.pencil)
            drawing_view.startErasing()
        }
    }
}
