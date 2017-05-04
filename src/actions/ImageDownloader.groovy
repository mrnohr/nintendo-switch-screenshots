package src.actions

import src.models.SwitchTweet

class ImageDownloader extends AbstractAction {
	public ImageDownloader(ConfigObject config) {
		super(config)
	}

	public void downloadImage(SwitchTweet tweet) {
		println "Going to download ${tweet.imageUrlLarge} to ${tweet.downloadFilename}"
		File file = new File(tweet.downloadFilename, downloadDirectory)
		def fileOutputStream = file.newOutputStream()
		fileOutputStream << new URL(tweet.imageUrlLarge).openStream()
		fileOutputStream.close()
	}
}