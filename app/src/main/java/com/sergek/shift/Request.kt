package com.sergek.shift

class Request(
    var number: Number,
    var scheme: String,
    var type: String,
    var brand: String,
    var prepaid: Boolean,
    var country: Country,
    var bank: Bank
)
class Country(
    var numeric: String,
    var alpha2: String,
    var name: String,
    var currency: String,
    var latitude: Int,
    var longitude: Int
)

class Bank(
    var name: String,
    var url: String,
    var phone: String,
    var city: String
)

class Number(
    var length: Int,
    var luhn: Boolean
)