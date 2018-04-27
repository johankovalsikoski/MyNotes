package com.kovalsikoski.johan.mynotes

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.EditText

class NoteInputDialog: DialogFragment() {

    internal lateinit var parentActivity : FragmentActivity
    private lateinit var positiveButtonDescription: String
    private lateinit var negativeButtonDescription: String

    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText

    private lateinit var noteInputDialogListener: NoteInputDialogListener

    private lateinit var alertDialog: AlertDialog

    interface NoteInputDialogListener {

        fun onNoteInputDialogPositiveButtonClicked(dialog: DialogInterface?, title: String, description: String)

        fun onNoteInputDialogNegativeButtonClicked(dialog: DialogInterface?)
    }

    companion object {
        fun newInstance(activity: FragmentActivity,
                        positiveButtonDescription: String,
                        negativeButtonDescription: String) : DialogFragment {
            val dialog = NoteInputDialog()

            dialog.parentActivity = activity
            dialog.positiveButtonDescription = positiveButtonDescription
            dialog.negativeButtonDescription = negativeButtonDescription

            return dialog
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            noteInputDialogListener = context as NoteInputDialogListener
        } catch (e: Throwable) {
            throw ClassCastException(context.toString() + " must implement NoteInputDialogListener.")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialogView = View.inflate(context,R.layout.custom_input_dialog, null)

        val alertDialogBuilder = AlertDialog.Builder(parentActivity)

        alertDialogBuilder.setView(dialogView)

        titleEditText = dialogView.findViewById(R.id.titleEditText)

        descriptionEditText = dialogView.findViewById(R.id.descriptionEditText)

        alertDialogBuilder.setPositiveButton("Salvar") { dialog, _ ->

            val title = titleEditText.text.toString()

            val description = descriptionEditText.text.toString()

            noteInputDialogListener.onNoteInputDialogPositiveButtonClicked(dialog, title, description)
        }

        alertDialogBuilder.setNegativeButton("Cancelar") { dialog, _ ->

            noteInputDialogListener.onNoteInputDialogNegativeButtonClicked(dialog)

        }

        alertDialog = alertDialogBuilder.create()

        return alertDialog
    }
}