package com.oskarrek.fridgemanager

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.oskarrek.fridgemanager.MainActivity.IntentFilters.PRODUCT_CREATE
import com.oskarrek.fridgemanager.MainActivity.IntentFilters.PRODUCT_EDIT
import com.oskarrek.fridgemanager.MainActivity.IntentFilters.PRODUCT_ACTION
import com.oskarrek.fridgemanager.MainActivity.IntentFilters.PRODUCT_SERIALIZABLE
import com.oskarrek.fridgemanager.models.Product
import kotlinx.android.synthetic.main.activity_create_edit_product.*
import kotlinx.android.synthetic.main.content_create_edit_product.*
import java.lang.RuntimeException
import java.util.*
import com.squareup.picasso.Picasso

class CreateEditProductActivity : AppCompatActivity() {

    private val RESULT_LOAD_IMG = 555

    private var imageUri : String = ""

    private var product : Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit_product)
        setSupportActionBar(toolbar)

        setupListeners()

        handleIntent(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode == Activity.RESULT_OK && requestCode == RESULT_LOAD_IMG) {
            imageUri = data?.data.toString()

            System.out.println("----------------------------------- ${imageUri}")

            Picasso
                .get()
                .load(imageUri)
                .error(R.drawable.ic_launcher_background)
                .into(product_edit_image)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun buildDatePicker(dateView: TextView) {

        val calendar = Calendar.getInstance()
        val timeSpan =  (dateView.tag ?: 0L) as Long

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

    private fun setupListeners() {
        product_fab_add_image.setOnClickListener { view ->

            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
        }

        product_fab_save.setOnClickListener {
            if(!isDataCorrect()) {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.missing_data))
                    .setMessage(getString(R.string.missing_data_desctrpition))
                    .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            } else {

                val name = product_edit_name.text.toString()
                val expirationDate = product_edit_expiration_date.tag as Long
                val quantity = product_edit_quantity.text.toString()

                if(product == null)
                    product = Product(name, expirationDate, quantity, imageUri)

                val intent = Intent()
                intent.putExtra(PRODUCT_ACTION, product)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        product_edit_expiration_date.setOnClickListener {
            buildDatePicker(it as TextView)
        }
    }

    private fun handleIntent(intent : Intent) {
        if(!intent.hasExtra(PRODUCT_ACTION))
            throw RuntimeException()

        // Handle requests.
       when(intent.getIntExtra(PRODUCT_ACTION, -1)) {
           PRODUCT_CREATE -> {}
           PRODUCT_EDIT ->  {
               this.product = intent.getSerializableExtra(PRODUCT_SERIALIZABLE) as Product
               product_edit_name.setText(product?.name)
               product_edit_expiration_date.tag = product?.expirationDate
               product_edit_quantity.setText(product?.quantity)
           }
       }
    }

    private fun isDataCorrect() : Boolean {
       return !(product_edit_name.text.isEmpty() ||
               product_edit_expiration_date.tag == null ||
               product_edit_quantity.text.isEmpty() ||
               imageUri.isEmpty())
    }
}
