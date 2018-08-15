package com.kovalsikoski.johan.mynotes

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserModel (
        @PrimaryKey
        var uuid: String = "",
        var email: String = "",
        var passwordTip: String = "",
        var password: String = "") : RealmObject()
