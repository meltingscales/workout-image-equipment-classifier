package com.scanfit.modeling

import java.io.Serializable
import java.util.*

class Person(name: String, username: String, email: String, gender: String, bodyType: BodyType, birthday: Date) : Serializable, UserInfo {


    override var username: String = username
    override var name: String = name
    override var email: String = email

    var gender: String? = null
    var bodyType: BodyType? = null
    var birthday: Date? = null
    var weight: Weight? = null
    var height: Height? = null
}
