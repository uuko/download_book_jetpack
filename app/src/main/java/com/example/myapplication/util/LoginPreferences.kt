package com.example.myapplication.util

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginPreferences @Inject constructor(context: Context) :
    PreferencesHelper(context), LoginPreferencesProvider {
    override val className = LoginPreferences::class.java.name
    private val SP_ORG_ID = "SP_ORG_ID"
    private val SP_PERSON_ID = "SP_PERSON_ID"
    private val SP_PERSON_NAME = "SP_PERSON_NAME"
    private val FACTORY_NUMBER = "FACTORY_NUMBER"
    override fun setOrgId(mOrgId: String?) {
        save(Type.STRING, SP_ORG_ID, mOrgId)
    }

    override fun setPersonId(mPersonId: String?) {
        save(Type.STRING, SP_PERSON_ID, mPersonId)
    }

    override fun setPersonName(mName: String?) {
        save(Type.STRING, SP_PERSON_NAME, mName)
    }

    override fun setFactoryNumber(mNumber: String?) {
        save(Type.STRING, FACTORY_NUMBER, mNumber)
    }

    override fun getOrgId(): String? {
        return get(Type.STRING, SP_ORG_ID) as String?
    }

    override fun getPersonId(): String? {
        return get(Type.STRING, SP_PERSON_ID) as String?
    }

    override fun getPersonName(): String? {
        return get(Type.STRING, SP_PERSON_NAME) as String?
    }

    override fun getFactoryNumber(): String? {
        return get(Type.STRING, FACTORY_NUMBER) as String?
    }
}
