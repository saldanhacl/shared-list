package br.com.go5.sharedlist.persistence

import com.chibatching.kotpref.KotprefModel

object UserInfo : KotprefModel() {
    var isLogged by booleanPref()
    var username by stringPref()
    var id by longPref()
}