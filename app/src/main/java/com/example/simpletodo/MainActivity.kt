package com.example.simpletodo

import android.icu.text.Transliterator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var ListOfTasks = mutableListOf<String>()
    lateinit var adapter: ClassItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : ClassItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // 1. Remove the item from the list
                ListOfTasks.removeAt(position)

                // 2. Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged()

                // call saveItems method
                saveItems()
            }
        }

        // 1. Detect when user clicks on add button
        findViewById<Button>(R.id.button).setOnClickListener {
            // Code executed when user clicks on button
//            Log.i()
        }

        // call loadItems method
        loadItems()

        // hardcoded
//        ListOfTasks.add("Testing adding this task")

        // Look up recycler view in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)
        // Create adapter passing in the sample user data
        adapter = ClassItemAdapter(ListOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Instead of calling findViewById<EditText>(R.id.AddTaskField), aka the addTaskField
        // val inputTextField = findViewById<EditText>(R.id.AddTaskField)

        // Set up button field and input field for tasks.
        findViewById<Button>(R.id.button).setOnClickListener {
            // 1. grab the text the user has inputted into @id/addTaskField
            val userInputtedTask = findViewById<EditText>(R.id.AddTaskField).text.toString()

            // 2. Add the string to out list of tasks: listOfTasks
            ListOfTasks.add(userInputtedTask)

            // Notify the adapter that our data has been updated
            adapter.notifyItemInserted(ListOfTasks.size - 1)

            // 3. reset the text field
            findViewById<EditText>(R.id.AddTaskField).setText("")
        }
    }

    // Save the data that the user has inputted
    // Save the data by writing and reading from a file

    // Create a method to get the file needed
    fun getDataFile() : File {
        // Every line represents a task in ListOfTasks
        return File(filesDir, "data.txt")
    }

    // Load the items by reading every line in the data file
    fun loadItems () {
        try {
            ListOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }

        catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // Save items by writing into data file
    fun saveItems() {
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), ListOfTasks)
        }

        catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}