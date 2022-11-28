package com.jarvis.app.view.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spanned
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.MenuCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
    lateinit var editFieldDialogFactory: EditFieldDialogFactory

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
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun onConfigsReceived(configItems: List<FieldItemViewModel<*>>) {
        emptyView.isVisible = configItems.isEmpty()
        recyclerView.isVisible = configItems.isNotEmpty()
        configListAdapter.setFields(configItems)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        MenuCompat.setGroupDividerEnabled(menu, true)

        viewModel.menuState.observe(this) {
            configListAdapter.deactivateAllFields = !it.isActive
            toolbar.subtitle = styleSubtitle(it)
            menu.findItem(R.id.menu_lock).isChecked = it.isLocked
            menu.findItem(R.id.menu_active).isChecked = it.isActive
        }
        return true
    }

    private fun styleSubtitle(menuModel: MainMenuViewModel): Spanned =
        when (menuModel.isActive) {
            true -> fromHtml(menuModel.toolbarSubtitle)
            false -> fromHtml("<font color='#FF0000'>${menuModel.toolbarSubtitle}</font>")
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
                showDeleteConfigsDialog {
                    viewModel.clearConfigs()
                }
                true
            }
            R.id.menu_help -> {
                showHelpDialog()
                true
            }
            else -> false
        }

    private fun showDeleteConfigsDialog(onConfirm: () -> Unit) {
        if (alertDialog != null) return

        alertDialog = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.menu_item_delete_all)
            .setMessage(R.string.clear_fields_dialog_message)
            .setPositiveButton(R.string.clear_fields_dialog_ok) { _, _ ->
                onConfirm()
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

        fun onDismiss() {
            alertDialog?.dismiss()
            alertDialog = null
        }

        fun onFieldUpdated(newValue: Any, isPublished: Boolean) {
            viewModel.updateFieldValue(item, newValue, isPublished)
        }

        alertDialog =
            editFieldDialogFactory.getEditFieldDialog(item, this, ::onDismiss, ::onFieldUpdated)
                .also {
                    it.show()
                }
    }

    private fun showHelpDialog() {
        val uri = Uri.parse("https://github.com/slambang/jarvis/tree/main/jarvis-app")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun fromHtml(source: String): Spanned =
        HtmlCompat.fromHtml(source, HtmlCompat.FROM_HTML_MODE_LEGACY)
}
