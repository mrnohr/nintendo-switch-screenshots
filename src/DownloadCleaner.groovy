package src

class DownloadCleaner {
	ConfigObject config
	File downloadDirectory
	File cropDirectory

	public DownloadCleaner(ConfigObject config) {
		this.config = config
		initializeDirectories()
	}

	public delete(SwitchTweet tweet, boolean cropped = false) {
		File downloadedFile = new File(tweet.downloadFilename, downloadDirectory)
		if(downloadedFile.exists()) {
			println "Going to delete ${downloadedFile.absolutePath}"
			downloadedFile.delete()
		} else {
			println "WARN: Could not find ${downloadedFile.absolutePath}"
		}

		if(cropped) {
			File croppedFile = new File(tweet.croppedFilename, cropDirectory)
			if(croppedFile.exists()) {
				println "Going to delete ${croppedFile.absolutePath}"
				croppedFile.delete()
			} else {
				println "WARN: Could not find ${croppedFile.absolutePath}"
			}
		}
	}



	private initializeDirectories(){
		downloadDirectory = new File(config.download.directory)
		cropDirectory = new File(config.crop.directory)

		if(!downloadDirectory.exists()) {
			throw new FileNotFoundException("What did you do? $downloadDirectory does not exist")
		}

		if(!cropDirectory.exists()) {
			throw new FileNotFoundException("What did you do? $cropDirectory does not exist")
		}
	}
}