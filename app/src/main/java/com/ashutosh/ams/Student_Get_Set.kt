package com.ashutosh.ams

class Student_Get_Set {
    var fname: String? = null
    var lname: String? = null
    var dept: String
    var st_class: String
    var address: String? = null
    var mobile: String? = null
    var eid: String
    var pass: String? = null
    var faculty_id: String? = null
    var subject: String? = null
    var date: String? = null
    var add_id = 0
    var isCheck = false

    constructor(dept: String, st_class: String, eid: String, faculty_id: String?, subject: String?, date: String?) {
        this.dept = dept
        this.st_class = st_class
        this.eid = eid
        this.faculty_id = faculty_id
        this.subject = subject
        this.date = date
    }

    constructor(fname: String?, lname: String?, dept: String, st_class: String, eid: String, check: Boolean) {
        this.fname = fname
        this.lname = lname
        this.dept = dept
        this.st_class = st_class
        this.eid = eid
        isCheck = check
    }

    constructor(fname: String?, lname: String?, dept: String, st_class: String, address: String?, mobile: String?, eid: String, pass: String?, add_id: Int) {
        this.fname = fname
        this.lname = lname
        this.dept = dept
        this.st_class = st_class
        this.address = address
        this.mobile = mobile
        this.eid = eid
        this.pass = pass
        this.add_id = add_id
    }
}