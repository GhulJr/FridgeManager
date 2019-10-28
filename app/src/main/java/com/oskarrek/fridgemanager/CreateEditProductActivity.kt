package com.oskarrek.fridgemanager

import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_edit_product.*
import kotlinx.android.synthetic.main.content_create_edit_product.*
import kotlinx.android.synthetic.main.item_product.*
import java.util.*

class CreateEditProductActivity : AppCompatActivity() {

    var timeInMillis: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit_product)
        setSupportActionBar(toolbar)
        product_fab_add_image.setOnClickListener { view ->

        }

        product_edit_expiration_date.setOnClickListener {
            buildDialogPicker(it as TextView)
        }
    }

    private fun buildDialogPicker(dateView: TextView) {

        val calendar = Calendar.getInstance()
        val timeSpan =  (dateView?.tag ?: 0L) as Long

        if(timeSpan > 0) { calendar.timeInMillis = timeSpan }

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener{ view, year, month, day ->
                    dateView.text = "${day}.${month + 1}.${year}"
                    val cld = Calendar.getInstance()
                    cld.set(year, month, day)
                    dateView.tag = cld.timeInMillis
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))

        datePickerDialog.show()

    }

   // private handleIntent(intent : Inten)
}
