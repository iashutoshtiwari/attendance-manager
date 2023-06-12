package com.ashutosh.ams

class Teacher_Get_Set {
    var fname: String
    var lname: String
    var address: String
    var mobile: String
    var eid: String
    var pass: String
    var add_id = 0

    constructor(fname: String, lname: String, address: String, mobile: String, eid: String, pass: String, add_id: Int) {
        this.fname = fname
        this.lname = lname
        this.address = address
        this.mobile = mobile
        this.eid = eid
        this.pass = pass
        this.add_id = add_id
    }

    constructor(fname: String, lname: String, address: String, mobile: String, eid: String, pass: String) {
        this.fname = fname
        this.lname = lname
        this.address = address
        this.mobile = mobile
        this.eid = eid
        this.pass = pass
    }
}