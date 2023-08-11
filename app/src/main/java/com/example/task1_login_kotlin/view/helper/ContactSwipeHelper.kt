package com.example.task1_login_kotlin.view.helper

import android.graphics.*
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.task1_login_kotlin.App
import com.example.task1_login_kotlin.view.adapter.ContactsAdapter

class ContactSwipeHelper(private val adapter: ContactsAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.LEFT) {
            adapter.notifyItemChanged(position)
            Toast.makeText(App.instance.applicationContext, "Swipe left", Toast.LENGTH_SHORT).show()
        } else if (direction == ItemTouchHelper.RIGHT) {
            adapter.notifyItemChanged(position)
            Toast.makeText(App.instance.applicationContext, "Swipe right", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean,
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            val height = itemView.bottom.toFloat() - itemView.top.toFloat()
            val width = height / 3
            val p = Paint()
            if (dX < 0) {
                p.color = Color.RED
                val background = RectF(itemView.right.toFloat() + dX,
                    itemView.top.toFloat(),
                    itemView.right.toFloat(),
                    itemView.bottom.toFloat())
                c.drawRect(background, p)

                val margin = (dX / 5 - width) / 2
                val iconDest =
                    RectF(itemView.right.toFloat() + margin,
                        itemView.top.toFloat() + width,
                        itemView.right.toFloat() + (margin + width),
                        itemView.bottom.toFloat() - width)
                val emptyBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
                c.drawBitmap(emptyBitmap, null, iconDest, p)
            } else if (dX > 0) {
                p.color = Color.BLUE
                val background = RectF(itemView.left.toFloat(),
                    itemView.top.toFloat(),
                    itemView.left.toFloat() + dX,
                    itemView.bottom.toFloat())
                c.drawRect(background, p)
//                val icon = BitmapFactory.decodeResource(App.instance.applicationContext.resources, R.drawable.ic_check)
                val margin = (dX / 5 - width) / 2
                val iconDest = RectF(margin,
                    itemView.top.toFloat() + width,
                    margin + width,
                    itemView.bottom.toFloat() - width)
                val emptyBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
                c.drawBitmap(emptyBitmap, null, iconDest, p)
            }
        } else {
            c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        }
        // Giới hạn khoảng cách vuốt để nhận sự kiện nhỏ nhất là 1/5 chiều dài của item
        super.onChildDraw(c, recyclerView, viewHolder, dX / 5, dY, actionState, isCurrentlyActive)

    }
}