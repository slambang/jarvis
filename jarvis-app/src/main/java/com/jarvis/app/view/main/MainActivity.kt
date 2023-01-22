package com.jarvis.app.view.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.MenuCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jarvis.app.R
import com.jarvis.app.view.main.editfielddialog.EditFieldDialogFactory
import com.jarvis.app.view.main.recyclerview.ConfigItemListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var getEditFieldDialog: EditFieldDialogFactory

    private lateinit var configListAdapter: ConfigItemListAdapter

    private lateinit var toolbar: Toolbar
    private lateinit var emptyView: View
    private lateinit var recyclerView: RecyclerView

    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
        viewModel.configItems.observe(this@MainActivity, ::onConfigsReceived)
    }

    private fun setupView() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        emptyView = findViewById(R.id.empty_view)
        configListAdapter = ConfigItemListAdapter(::showEditFieldDialog)

        recyclerView = findViewById<RecyclerView>(R.id.config_item_list).apply {
            adapter = configListAdapter
        }
    }

    private fun onConfigsReceived(configItems: List<ConfigGroupItemViewModel>) {
        emptyView.isVisible = configItems.isEmpty()
        recyclerView.isVisible = configItems.isNotEmpty()
        configListAdapter.setGroups(configItems)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        MenuCompat.setGroupDividerEnabled(menu, true)

        viewModel.menuState.observe(this) {
            configListAdapter.deactivateAllFields = !it.isActive
            toolbar.subtitle = it.toolbarSubtitle
            menu.findItem(R.id.menu_lock).isChecked = it.isLocked
            menu.findItem(R.id.menu_active).isChecked = it.isActive
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_active -> {
                viewModel.setJarvisIsActive(!item.isChecked)
                true
            }
            R.id.menu_lock -> {
                viewModel.setJarvisIsLocked(!item.isChecked)
                true
            }
            R.id.menu_delete -> {
                showDeleteConfigsDialog()
                true
            }
            R.id.menu_help -> {
                showHelp()
                true
            }
            else -> false
        }

    private fun showDeleteConfigsDialog() {
        if (alertDialog != null) return

        alertDialog = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.menu_item_delete_all)
            .setMessage(R.string.clear_fields_dialog_message)
            .setPositiveButton(R.string.clear_fields_dialog_ok) { _, _ ->
                viewModel.clearConfigs()
            }
            .setNeutralButton(R.string.clear_fields_dialog_cancel, null)
            .setOnDismissListener {
                alertDialog = null
            }
            .create()

        with(alertDialog!!) {
            setOnShowListener {
                val saveButton = getButton(AlertDialog.BUTTON_POSITIVE)
                saveButton.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            }

            show()
        }
    }

    private fun showEditFieldDialog(item: FieldItemViewModel<*>) {
        if (alertDialog != null) return

        fun onDialogDismiss() {
            alertDialog?.dismiss()
            alertDialog = null
        }

        fun onFieldUpdate(newValue: Any, isPublished: Boolean) {
            viewModel.updateFieldValue(item, newValue, isPublished)
        }

        alertDialog = getEditFieldDialog(item, this, ::onDialogDismiss, ::onFieldUpdate)
            .also { it.show() }
    }

    private fun showHelp() {
        val uri = Uri.parse("https://github.com/slambang/jarvis/tree/main/jarvis-app")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}
