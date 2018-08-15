package com.kovalsikoski.johan.mynotes

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserModel (
        @PrimaryKey
        var email: String = "",
        var passwordTip: String = "",
        var password: String = "") : RealmObject()
