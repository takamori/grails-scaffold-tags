class Entry {

    static constraints = {
    }
    static belongsTo = Blog
    static optionals = [ "lastUpdated" ]
    Blog blog
    Date createdOn = new Date()
    String title
    String body
    Date lastUpdated
}
