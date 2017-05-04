package src.actions

import src.models.SwitchTweet

class DownloadCleaner extends AbstractAction{
	public DownloadCleaner(ConfigObject config) {
		super(config)
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
}