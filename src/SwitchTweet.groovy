package src

class SwitchTweet {
	String twitterId
	String imageUrl
	Date tweetDate
	List<String> hashtags
	String source

	String toString() {
		"[$tweetDate] $imageUrl , $hashtags , ${fromNintendo()}"
	}

	boolean fromNintendo() {
		return source.contains("Nintendo Switch Share")
	}

	boolean hasImage() {
		return imageUrl?.trim() as boolean
	}

	String getImageUrlLarge() {
		return "${imageUrl}:large"
	}

	String getDownloadFilename() {
		return "${filenameBase}.jpg"
	}

	String getCroppedFilename() {
		return "${filenameBase}.png"
	}

	private String getFilenameBase() {
		return tweetDate.format("yyyy-MM-dd_HH-mm-ss")
	}
}
