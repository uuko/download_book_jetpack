package com.example.myapplication.util

interface LoginPreferencesProvider {
    fun setOrgId(mOrgId: String?)

    fun setPersonId(mPersonId: String?)

    fun setPersonName(mName: String?)

    fun setFactoryNumber(mNumber: String?)

    fun getOrgId(): String?

    fun getPersonId(): String?

    fun getPersonName(): String?

    fun getFactoryNumber(): String?
}
