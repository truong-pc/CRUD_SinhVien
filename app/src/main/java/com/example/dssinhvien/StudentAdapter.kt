package com.example.dssinhvien

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter (private val con_text: Context, val students: MutableList<StudentModel>) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.text_student_name)
        val tvStudentID: TextView = itemView.findViewById(R.id.text_student_id)
        val btnDelete: ImageView = itemView.findViewById(R.id.image_remove)
        val btnEdit: ImageView = itemView.findViewById(R.id.image_edit)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item, parent, false)
        return StudentViewHolder(view)
    }


    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.tvName.text = students[position].name
        holder.tvStudentID.text = students[position].StudentID

        holder.btnDelete.setOnClickListener {
            students.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, students.size)
        }
        holder.btnEdit.setOnClickListener {
            showEditDialog(position)
        }
        holder.itemView.setOnClickListener {
            showEditDialog(position)
        }
    }

    private fun showEditDialog(position: Int) {
        val student = students[position]
        val dialogView = LayoutInflater.from(con_text).inflate(R.layout.dialog_edit_student, null)
        val editName = dialogView.findViewById<EditText>(R.id.edit_student_name)
        val editID = dialogView.findViewById<EditText>(R.id.edit_student_id)
        val btnSave = dialogView.findViewById<Button>(R.id.btn_save)
        val btnCancel = dialogView.findViewById<Button>(R.id.btn_cancel)

        editName.setText(student.name)
        editID.setText(student.StudentID)

        val dialog = AlertDialog.Builder(con_text)
            .setTitle("Edit Student")
            .setView(dialogView)
            .create()

            btnSave.setOnClickListener {
                student.name = editName.text.toString()
                student.StudentID = editID.text.toString()
                notifyItemChanged(position)
                dialog.dismiss()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

        dialog.show()
        }

    fun showAddDialog() {
        val dialogView = LayoutInflater.from(con_text).inflate(R.layout.dialog_edit_student, null)
        val editName = dialogView.findViewById<EditText>(R.id.edit_student_name)
        val editID = dialogView.findViewById<EditText>(R.id.edit_student_id)
        val btnSave = dialogView.findViewById<Button>(R.id.btn_save)
        val btnCancel = dialogView.findViewById<Button>(R.id.btn_cancel)

        val dialog = AlertDialog.Builder(con_text)
            .setTitle("Add Student")
            .setView(dialogView)
            .create()

        btnSave.setOnClickListener {
            students.add(StudentModel(editName.text.toString(), editID.text.toString()))
            notifyItemInserted(students.size - 1)
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    override fun getItemCount(): Int {
        return students.size
    }

}