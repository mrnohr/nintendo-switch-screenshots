@Grab(group="org.twitter4j", module="twitter4j-core", version="[3.0,)")

import twitter4j.*
//import twitter4j.api.*

String username = "<twitter-user>"
int numberOfTweetsToSearch = 10
File directory = new File("downloads");

//setup download directory
if(!directory.exists()) {
	println "Creating directory ${directory.absolutePath}"
	directory.mkdirs()
}

//do the work
getTweets(username, numberOfTweetsToSearch).each {
	downloadImage(it, directory)
}

//helper functions
List<SwitchTweet> getTweets(String username, int count) {
	Twitter twitter = TwitterFactory.getSingleton()
	Paging paging = new Paging(1, count);
	List<Status> statuses = twitter.getUserTimeline(username, paging);

	List<SwitchTweet> tweets = []

	statuses.each{ status ->
		SwitchTweet tweet = new SwitchTweet()
		tweet.source = status.source
		if(tweet.fromNintendo) {
			tweet.tweetDate = status.createdAt
			tweet.imageUrl = status.mediaEntities.first().mediaURL
			tweets << tweet
		}
	}
	return tweets
}

void downloadImage(SwitchTweet tweet, File directory) {
	String filename = tweet.tweetDate.format("yyyy-MM-dd_HH:mm:ss") + ".jpg"
	def file = new File(filename, directory)
	if(file.exists()){
		println "Already downloaded $filename"
	} else {
		println "Downloading to $filename"
		def fileOutputStream = file.newOutputStream()
	    fileOutputStream << new URL(tweet.imageUrl).openStream()
	    fileOutputStream.close()
	}
}

//helper class
class SwitchTweet {
	String imageUrl
	String source
	Date tweetDate

	boolean isFromNintendo(){
		return source.contains("Nintendo Switch Share")
	}

	String toString() {
		"$tweetDate $imageUrl (Nintendo = $fromNintendo)"
	}
}
