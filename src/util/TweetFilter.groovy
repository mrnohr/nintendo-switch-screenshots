package src.util

import src.models.SwitchTweet

class TweetFilter {
	public static List<SwitchTweet> filterForDownload(List<SwitchTweet> tweets, RunAuditor auditor) {
		return tweets.findAll {
			!auditor.isDownloaded(it) && it.fromNintendo() && it.hasImage()
		}
	}

	public static List<SwitchTweet> filterForUpload(List<SwitchTweet> tweets, RunAuditor auditor) {
		return tweets.findAll {
			!auditor.isUploaded(it)
		}
	}

	public static List<SwitchTweet> filterForDelete(List<SwitchTweet> tweets, RunAuditor auditor) {
		return tweets.findAll {
			!auditor.isDeleted(it)
		}
	}

	public static needsCropping(SwitchTweet tweet) {
		return tweet.hashtags.contains("BreathoftheWild")
	}
}