package src

class ImageDownloader {
	ConfigObject config
	File directory

	public ImageDownloader(ConfigObject config) {
		this.config = config
		validateOrCreateDirectory()
	}

	public void downloadImage(SwitchTweet tweet) {
		println "Going to download ${tweet.imageUrlLarge} to ${tweet.downloadFilename}"
		File file = new File(tweet.downloadFilename, directory)
		def fileOutputStream = file.newOutputStream()
		fileOutputStream << new URL(tweet.imageUrlLarge).openStream()
		fileOutputStream.close()
	}

	private validateOrCreateDirectory(){
		directory = new File(config.download.directory)
		if(!directory.exists()) {
			println "Creating directory ${directory.absolutePath}"
			directory.mkdirs()
		}
	}
}