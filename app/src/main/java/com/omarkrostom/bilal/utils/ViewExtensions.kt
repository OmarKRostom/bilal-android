package com.omarkrostom.bilal.utils

import androidx.fragment.app.Fragment

fun Fragment.showAlertDialogSingleOption(title: String,
                                         description: String,
                                         action: (() -> Unit)? = null) {

}

fun Fragment.showAlertDialogYesNo(title: String,
                                  description: String,
                                  positiveAction: () -> Unit,
                                  negativeAction: () -> Unit) {

}