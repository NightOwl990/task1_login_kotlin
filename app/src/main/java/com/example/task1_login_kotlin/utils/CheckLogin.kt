package com.example.task1_login_kotlin.utils

import android.text.TextUtils

class CheckLogin {
    companion object {

        fun validateLoginInput(username: String, password: String): String {
            val phoneValidation = checkPhoneNumber(username)
            if (phoneValidation.isNotEmpty()) {
                return phoneValidation
            }

            val passValidation = checkPassword(password)
            if (passValidation.isNotEmpty()) {
                return passValidation
            }

            return ""
        }

        fun checkPhoneNumber(value: String): String {
            if (TextUtils.isEmpty(value)) {
                return "Phone number can't be empty"
            }
            if (!isPhoneNumberFormat(value)) {
                return "Phone number incorrect format"
            }
            return ""
        }

        fun checkPassword(value: String): String {
            if (TextUtils.isEmpty(value)) {
                return "Password can't be empty"
            }
            if (!isPasswordFormat(value)) {
                return "Password must contain at least 8 characters and at most 15 characters, " +
                        "contain at least 1 number, contain at least 1 uppercase letter, " +
                        "contain at least 1 lowercase letter, contain at least 1 special character"
            }
            return ""
        }

        private fun isPhoneNumberFormat(value: String): Boolean {
            val reg =
                "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$".toRegex()
            return value.matches(reg)
        }

        private fun isPasswordFormat(value: String): Boolean {
            val reg =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%&*()\\-+=^])(?!.*\\s).{8,15}\$".toRegex()
            return value.matches(reg)
        }
    }
}