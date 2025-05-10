package com.kwork.translationagency.core.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kwork.translationagency.core.common.ConstantsApplication.APP_ACTIVITY
import com.kwork.translationagency.core.common.UiState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Extensions {

    fun Fragment.showToast(message: String) {
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun ImageView.  loadImageUri(uri: Uri) {
        Glide.with(this).load(uri).into(this)
    }
    fun ImageView.loadImageURL(url: String?) {
        Glide.with(this).load(url).centerCrop().into(this)
    }

    inline fun <T> Fragment.observeData(
        flow: Flow<T>,
        lifecycleOwner: LifecycleOwner = viewLifecycleOwner,
        state: Lifecycle.State = Lifecycle.State.STARTED,
        crossinline block: (T) -> Unit,
    ) = lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(state) {
            flow.collect { data ->
                block(data)
            }
        }
    }

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

    fun View.showKeyboard() {
        this.post {
            this.requestFocus()
            val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    @SuppressLint("Range")
    fun getFilenameFromUri(uri: Uri): String {
        val cursor = APP_ACTIVITY.contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            if (it.moveToFirst()) {
                it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME)) ?: ""
            } else {
                ""
            }
        } ?: ""
    }

    fun Int.formatAsPrice(): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(this)
    }

    fun Fragment.snackbar(msg: String) {
        view?.apply {
            Snackbar.make(this, msg, Snackbar.LENGTH_LONG).show()
        }
    }

    val EditText.textFlow: Flow<String>
        get() = callbackFlow {
            val textWatcher = doAfterTextChanged {
                if (it != null) {
                    trySend(it.toString())
                }
            }
            awaitClose { removeTextChangedListener(textWatcher) }
        }

    fun showAlertDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String = "OK",
        negativeButtonText: String? = null,
        onPositiveButtonClick: (() -> Unit)? = null,
        onNegativeButtonClick: (() -> Unit)? = null
    ) {
        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                onPositiveButtonClick?.invoke()
                dialog.dismiss()
            }

        if (negativeButtonText != null) {
            builder.setNegativeButton(negativeButtonText) { dialog, _ ->
                onNegativeButtonClick?.invoke()
                dialog.dismiss()
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("ShowToast")
    fun Snackbar.showSnack(message: String) {
        view.let {
            Snackbar.make(it, message, Snackbar.LENGTH_INDEFINITE).apply {
                setAction("Dismiss") {
                    dismiss()
                }
            }
        }
    }

    fun Context.sendEmail(email: String, subject: String, message: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
            type = "message/rfc822"
        }
        startActivity(Intent.createChooser(intent, "Choose an email client:"))
    }
    fun Context.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    fun NavController.safeNavigate(actionId: Int) {
        val currentDest: NavDestination? = currentDestination
        if (currentDest?.getAction(actionId) != null) {
            navigate(actionId)
        }
    }
    inline var View.isVisible: Boolean
        get() = visibility == View.VISIBLE
        set(v) { visibility = if (v) View.VISIBLE else View.GONE }

    fun View.onClick(block: () -> Unit) = setOnClickListener { block() }


    fun Fragment.toast(msg: String) =
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()


    inline fun <T> StateFlow<UiState<T>>.collectUi(
        owner: LifecycleOwner,
        crossinline onSuccess: (T) -> Unit = {},
        crossinline onError:   (String) -> Unit = {},
        crossinline onLoading: () -> Unit = {}
    ) {
        owner.lifecycleScope.launch {
            this@collectUi
                .flowWithLifecycle(owner.lifecycle)
                .collect { state ->
                    when (state) {
                        is UiState.Loading -> onLoading()
                        is UiState.Success -> onSuccess(state.data)
                        is UiState.Error   -> onError(state.mes)
                        else               -> Unit
                    }
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    fun plusDaysString(plusDays: Long) =
        LocalDate.now().plusDays(plusDays).format(fmt)

}