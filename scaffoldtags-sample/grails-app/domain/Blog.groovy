class Blog {

    static constraints = {
    }
    static belongsTo = User
    static hasMany = [entries: Entry]
    User author
    String name
    Date createdOn = new Date()
}
