class User {
    static constraints = {
        email(email: true)
        gender(inList: ['M' as char,'F' as char])
    }

    String username
    String realName
    String email
    char gender
    Calendar dateOfBirth
    Date createdOn = new Date()
}
