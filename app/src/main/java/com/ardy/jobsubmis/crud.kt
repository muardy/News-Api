package com.ardy.jobsubmis

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ardy.jobsubmis.databinding.ActivityCrudBinding
import com.ardy.jobsubmis.db.JourHelper
import com.ardy.jobsubmis.entity.jour
import java.text.SimpleDateFormat
import com.ardy.jobsubmis.db.DatabaseContract
import java.util.*

class crud : AppCompatActivity(), View.OnClickListener
{

    private var isEdit = false
    private var note: jour? = null
    private var position: Int = 0
    private lateinit var noteHelper: JourHelper

    private lateinit var binding: ActivityCrudBinding

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
    var button_date: Button? = null
    var cal = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudBinding.inflate(layoutInflater)
        setContentView(binding.root)
        noteHelper = JourHelper.getInstance(applicationContext)
        noteHelper.open()

        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            note = jour()
        }
        button_date = this.binding.buttonDate1
        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = "Update Journalist Data"
            btnTitle = "Update"

            note?.let {
                binding.edtTitle.setText(it.title)
                binding.edtPassword.setText(it.birth)

                val time = it.birth?.split("-")?.toTypedArray()
//                Toast.makeText(this@crud, time?.get(2).toString(), Toast.LENGTH_SHORT).show()
                val dateSetListener = object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                           dayOfMonth: Int) {

                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, monthOfYear)
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        updateDateInView()
                    }
                }


                button_date!!.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(view: View) {
                cal.set(Calendar.YEAR, time?.get(0)!!.toInt())
                cal.set(Calendar.MONTH, time?.get(1)!!.toInt() - 1)
                cal.set(Calendar.DAY_OF_MONTH, time?.get(2)!!.toInt())
                        DatePickerDialog(this@crud,
                            dateSetListener,

                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH)).show()

                    }

                })

            }

        } else {
            actionBarTitle = "Add Journalist Data"
            btnTitle = "Add Data"
            val dateSetListener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                       dayOfMonth: Int) {

                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateDateInView()
                }
            }


            button_date!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {

                    DatePickerDialog(this@crud,
                        dateSetListener,

                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()

                }

            })
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSubmit.text = btnTitle

        binding.btnSubmit.setOnClickListener(this)


    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd"

        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.edtPassword.text = sdf.format(cal.getTime())

    }
    override fun onClick(view: View) {
        if (view.id == R.id.btn_submit) {
            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtPassword.text.toString().trim()

            if (title.isEmpty()) {
                binding.edtTitle.error = "Field can not be blank"
                return
            }
            if (binding.edtPassword.text == "//-//-//")
            {
                Toast.makeText(this@crud, "Field can not be blank", Toast.LENGTH_SHORT).show()
                return
            }
            note?.title = title
            note?.birth = description

            val intent = Intent()
            intent.putExtra(EXTRA_NOTE, note)
            intent.putExtra(EXTRA_POSITION, position)

            val values = ContentValues()
            values.put(DatabaseContract.NoteColumns.TITLE, title)
            values.put(DatabaseContract.NoteColumns.BIRTH, description)

            if (isEdit) {
                val result = noteHelper.update(note?.id.toString(), values)
                if (result > 0) {
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    Toast.makeText(this@crud, "Failed to update data", Toast.LENGTH_SHORT).show()
                }
            } else {
                note?.date = getCurrentDate()
                values.put(DatabaseContract.NoteColumns.DATE, getCurrentDate())
                val result = noteHelper.insert(values)

                if (result > 0) {
                    note?.id = result.toInt()
                    setResult(RESULT_ADD, intent)
                    finish()
                } else {
                    Toast.makeText(this@crud, "Failed to add data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()

        return dateFormat.format(date)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_delete, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose) {
            dialogTitle = "Cancel"
            dialogMessage = "Do you want to undo changes to the form?"
        } else {
            dialogMessage = "Are you sure you want to delete this item?"
            dialogTitle = "Delete item"
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                if (isDialogClose) {
                    finish()
                } else {
                    val result = noteHelper.deleteById(note?.id.toString()).toLong()
                    if (result > 0) {
                        val intent = Intent()
                        intent.putExtra(EXTRA_POSITION, position)
                        setResult(RESULT_DELETE, intent)
                        finish()
                    } else {
                        Toast.makeText(this@crud, "Failed to delete data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}