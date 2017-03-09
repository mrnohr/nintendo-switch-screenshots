@Grab(group="org.twitter4j", module="twitter4j-core", version="[3.0,)")

import twitter4j.*

String username = "<twitter-user>"
int numberOfTweetsToSearch = 10
File directory = new File("downloads");

//setup download directory
if(!directory.exists()) {
    println "Creating directory ${directory.absolutePath}"
    directory.mkdirs()
}

//do the work
filterTweets(readFromTwitter(username, numberOfTweetsToSearch)).each{tweet ->
    downloadImage(tweet, directory)
}

//twitter methods
List<Status> readFromTwitter(String username, int count) {
    Twitter twitter = TwitterFactory.getSingleton()
    Paging paging = new Paging(1, count);
    return twitter.getUserTimeline(username, paging);
}

List<SwitchTweet> filterTweets(List<Status> statuses) {
    return statuses.findAll{fromNintendo(it)}?.collect { status ->
        SwitchTweet tweet = new SwitchTweet()
        tweet.tweetDate = status.createdAt
        tweet.imageUrl = status.mediaEntities?.first()?.mediaURL
        if(tweet.imageUrl) {
            return tweet
        }
    }.findAll{it} //just to make sure there are no null values
}

boolean fromNintendo(Status status) {
    return status.source.contains("Nintendo Switch Share")
}

//download method
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
    Date tweetDate
}
