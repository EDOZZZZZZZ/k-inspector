function getPassword() {
    var password = prompt("Enter the password")
    if (password != null && password != "") {
        return password
    } else {
        return ""
    }
}