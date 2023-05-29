package com.example.practicafinalmoviles.clases

class User(var email: String, var name: String, var age: Int, var address: String ) {
    override fun toString(): String {
        return "User(Email=$email, Name=$name, Age=$age, Address=$address)"
    }
}