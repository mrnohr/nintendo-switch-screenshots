package src.actions

import src.models.SwitchTweet

import twitter4j.*

@Grab(group="org.twitter4j", module="twitter4j-core", version="[3.0,)")
class TweetReader {
	ConfigObject config

	public TweetReader(ConfigObject config) {
		this.config = config
	}

	List<SwitchTweet> readTweets() {
		Twitter twitter = TwitterFactory.getSingleton()
		int pageNumber = config.download.pageNumber ?: 1
		Paging paging = new Paging(pageNumber, config.download.numberOfTweets);
		List<Status> tweets = twitter.getUserTimeline(config.download.twitterUser, paging);

		return convertToSwitchTweets(tweets)
	}

	private List<SwitchTweet> convertToSwitchTweets(List<Status> tweets) {
		List<SwitchTweet> switchTweets = []
		tweets.each{nextTweet ->
			SwitchTweet switchTweet = new SwitchTweet()
			switchTweet.with {
				twitterId = nextTweet.id
				tweetDate = nextTweet.createdAt
				imageUrl = nextTweet.mediaEntities?.first()?.mediaURL
				source = nextTweet.source
				hashtags = nextTweet.hashtagEntities.collect { it.text }
			}

			switchTweets << switchTweet
		}
		return switchTweets
	}
}
