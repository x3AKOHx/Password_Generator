package com.example.passwordgenerator.data

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Singleton
class PassRepository @Inject constructor(private val passDao: PassDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun getPasswords() = passDao.getPasswords()

    fun getPass(passId: Long) = passDao.getPass(passId)

    fun editTitle(title: String, passId: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            passDao.editTitle(title, passId)
        }
    }

    fun editLogin(login: String, passId: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            passDao.editLogin(login, passId)
        }
    }

    fun editPass(pass: String, passId: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            passDao.editPass(pass, passId)
        }
    }

    fun deletePass(passId: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            passDao.deletePass(passId)
        }
    }

    fun insert(pass: PassEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            passDao.insert(pass)
        }
    }

    companion object {
        @Volatile
        private var instance: PassRepository? = null

        fun getInstance(passDao: PassDao) =
            instance ?: synchronized(this) {
                instance ?: PassRepository(passDao).also { instance = it }
            }
    }
}
