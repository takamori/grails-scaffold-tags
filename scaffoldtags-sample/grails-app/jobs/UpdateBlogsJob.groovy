class UpdateBlogsJob {
	def timeout = 1000*60*5
	def startDelay = 1000*60

  	def malenames = ["Joe", "Bob", "Dave", "Chris", "Tom"]
  	def femalenames = ["Mary", "Ann", "Sue", "Fiona", "Georgia"]
  	def lastnames = ["Barber", "Smith", "Mason", "First", "Woods", "Field"]
  	def nouns = [ "apple", "fox", "moon", "table", "code", "room", "book", "dog", "music", "painting" ]
  	def adjectives = [ "red", "tasty", "pretty", "clear", "crowded", "heavy", "vague", "quick" ]
  	def verbs = [ "jumps", "eats", "loves", "does", "is", "hears", "sings", "reads", "gets", "closes", "makes", "writes" ]
  	def articles = ["one", "the", "each", "every", "any"]
  	def starts = ["but", "so", "and", "still,", "anyways,", "then", "occasionally", "usually"]

  	def minUsers = 100
  	def minBlogs = 50
  	def minEntries = 500
  	def maxEntriesPerBlog = 20
  	def maxSentencesPerEntry = 100
  	
	def randomSeed = new Random()
	
	def execute() {	
	    try {
  		// Makes sure we have a reasonable amount of data in the system
	  	if (User.count() < minUsers) {
	  	  	generateRandomUser()
	  	}
	  	if (Blog.count() < minBlogs) {
	  	    generateRandomBlog()
	  	}
	  	if (Entry.count() < minEntries) {
	  	  	generateRandomEntry()
	  	}
	  	if (Math.random() < 0.1) {
	  	  	updateRandomEntry()
	  	}
	    } catch (Exception e) {
	        e.printStackTrace()
	    }
	}
	
	def randomInt = { max ->
		randomSeed.nextInt(max)
  	}
  	
  	def ageInDays = { date->
  	    (int)Math.round((System.currentTimeMillis() - date.time) / (1000*60*60*24))
  	}
	
  	def generateRandomUser = {
	    // random gender, name, and birthdate
	    def gender = (Math.random() < 0.5) ? 'M' : 'F';
	    def firstName = (gender == 'M') ? malenames[randomInt(malenames.size())] : femalenames[randomInt(femalenames.size())]
		def lastName = lastnames[randomInt(lastnames.size())]
	    def dateOfBirth = Calendar.getInstance()
	    def age = 3 + randomInt(50)
	    dateOfBirth.add(Calendar.DATE, -randomInt(365))
	    dateOfBirth.add(Calendar.YEAR, -age )

	    // try to find a username
	    def username = lastName.toLowerCase()
	    if (User.findByUsername(username)) username = "${firstName}".toLowerCase()
	    if (User.findByUsername(username)) username = "${firstName[0]}${lastName}".toLowerCase()
	    if (User.findByUsername(username)) username = "${firstName}${lastName[0]}".toLowerCase()
	    if (User.findByUsername(username)) username = "${firstName}_${lastName}".toLowerCase()
	    if (User.findByUsername(username)) username = "${firstName}${dateOfBirth.get(Calendar.YEAR)}".toLowerCase()
	    if (User.findByUsername(username)) username = "${firstName}${i}".toLowerCase()
	    
	    def user = new User(username: username, 
	            			realName: "${firstName} ${lastName}",
	            			email: "${username}@ThisIsNotARealEmailAddress.com",
	            			gender: gender,
	            			dateOfBirth: dateOfBirth)
	    if (!user.save()) {
	        println "Unable to create user for ${firstName} ${lastName} born on ${dateOfBirth.time}"
	        println "\t${user.errors.allErrors}"
	    }
  	}
  	
  	def generateRandomBlog = {
	  	def users = User.findAll()
	  	if (!users.size()) {
	  	    return
	  	}
        def user = users[randomInt(users.size())]
        def accountAge = ageInDays(user.createdOn)
		def name
		while (!name || Blog.findByName(name)) {
  	        def noun = nouns[randomInt(nouns.size())]
  	        def verb = verbs[randomInt(verbs.size())]
  	        def adj = adjectives[randomInt(adjectives.size())]
  	        def possibleNames = [ "${user.realName}'s Blog", 
  	                              "Deep Thoughts by ${user.realName}",
  	                              "Remember ${user.realName}?",
  	                              "${user.realName}'s Secret",
  	                              "${user.realName}'s Diary",
  	                              "Big ${user.realName} News",
  	                              "Stories from ${user.dateOfBirth.time}",
  	                              "Life according to ${user.realName}",
  	                              "The Real ${user.realName}",
  	                              "${noun}",
  	                              "${noun} ${verb}",
  	                              "${adj} ${noun}",
  	                              "${adj} ${noun} ${verb}"]
			name = possibleNames[randomInt(possibleNames.size())]
		}
        def blog = new Blog(name: name, author: user)
	    if (!blog.save()) {
	        println "Unable to create blog ${name} for ${user.realName}"
	        println "\t${blog.errors.allErrors}"
	    }
  	}
  	
  	def generateRandomEntry = {
  	    def blogs = Blog.findAll()
	  	if (!blogs.size()) {
	  	    return
	  	}
        def blog = blogs[randomInt(blogs.size())]
        def user = blog.author
        def possibleNouns = [ user.realName ]
        possibleNouns.addAll(nouns)
        def text = new StringBuffer()
        def numSentences = randomInt(maxSentencesPerEntry - 1) + 1
        for (k in 1..numSentences) {
            def noun1 = possibleNouns[randomInt(possibleNouns.size())]
            def noun2 = possibleNouns[randomInt(possibleNouns.size())]
            def verb = verbs[randomInt(verbs.size())]
            def adj1 = adjectives[randomInt(adjectives.size())]
            def adj2 = adjectives[randomInt(adjectives.size())]
            def article1 = articles[randomInt(articles.size())]
            def article2 = articles[randomInt(articles.size())]
            def start = starts[randomInt(starts.size())]
            def sentences = [ "${noun1} ${verb}.",
                              "${article1} ${noun1} ${verb}.",
                              "${start} ${article1} ${noun1} ${verb}.",
                              "${article1} ${noun1} ${verb} ${article2} ${noun2}.",
                              "${article1} ${noun1} ${verb} ${article2} ${adj2} ${noun2}.",
                              "${article1} ${adj1} ${noun1} ${verb} ${article2} ${noun2}.",
                              "${article1} ${adj1} ${noun1} ${verb} ${article2} ${adj2} ${noun2}.",
                              "${start} ${article1} ${adj1} ${noun1} ${verb} ${article2} ${adj2} ${noun2}."]
            def sentence = sentences[randomInt(sentences.size())]
            text << sentence[0].toUpperCase() << sentence.substring(1)
            if (Math.random() < 0.1) {
                text << "\n\n"
            } else {
            	text << "  "
            }
        }
        def entry = new Entry(title: text[0..text.indexOf('.') - 1],
                			  blog: blog,
                              body: text.toString())
        if (!entry.save()) {
            println "Unable to save entry for ${blog}"
		    println "\t${entry.errors.allErrors}"
        }
  	}
  	
  	def updateRandomEntry = {
  	    def entries = Entry.findAll()
	  	if (!entries.size()) {
	  	    return
	  	}
        def entry = entries[randomInt(entries.size())]
        def lastUpdate = entry.createdOn
        if (entry.lastUpdated) {
            lastUpdate = entry.lastUpdated
        }
        def entryAge = ageInDays(entry.createdOn)
        def lastUpdateAge = ageInDays(lastUpdate)
  	    def maxUpdateDelta = Math.min(lastUpdateAge, 10)
        if (maxUpdateDelta > 0 && (!entry.lastUpdated || entryAge - lastUpdateAge < 10)) {
  	        entry.lastUpdated = new Date(lastUpdate.time + (randomInt(maxUpdateDelta*24*60*60) + 30) * 1000)
	        if (!entry.save()) {
	            println "Unable to update entry ${entry}"
			    println "\t${entry.errors.allErrors}"
	        }
        }
  	}
	
}
