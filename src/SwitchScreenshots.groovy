package src

import src.actions.*
import src.models.SwitchTweet
import src.util.*

public class SwitchScreenshots {
	ConfigObject config
	RunAuditor auditor

	public SwitchScreenshots(String configFilePath) {
		loadConfig(configFilePath)
		auditor = new RunAuditor(config)
	}

	public void takeActions() {
		List<SwitchTweet> tweets = readFromTwitter();

		if(config.download.enabled) {
			downloadImages(tweets)
		}
		if(config.crop.enabled) {
			cropImages(tweets)
		}
		if(config.dropbox.enabled) {
			uploadImages(tweets)
		}
		if(config.delete.enabled) {
			deleteImages(tweets)
		}
	}

	private List<SwitchTweet> readFromTwitter() {
		TweetReader reader = new TweetReader(config)
		return reader.readTweets()
	}

	private void downloadImages(List<SwitchTweet> tweets) {
		ImageDownloader downloader = new ImageDownloader(config)
		List<SwitchTweet> filteredTweets = TweetFilter.filterForDownload(tweets, auditor)
		(tweets - filteredTweets).each {
			println "Already downloaded ${it.filenameBase}"
		}
		filteredTweets.each {
			downloader.downloadImage(it)
			auditor.markDownloaded(it)
		}
		auditor.writeAuditLog()
	}

	private void cropImages(List<SwitchTweet> tweets) {
		ImageCropper cropper = new ImageCropper(config)
		List<SwitchTweet> filteredTweets = TweetFilter.filterForCropping(tweets, auditor)
		(tweets - filteredTweets).each {
			println "Do not need to crop ${it.filenameBase} (cropped = ${auditor.isCropped(it)}, hashtags = ${it.hashtags}"
		}

		filteredTweets.each {
			cropper.cropImage(it)
			auditor.markCropped(it)
		}
		auditor.writeAuditLog()
	}

	private void uploadImages(List<SwitchTweet> tweets) {
		DropboxUploader dropbox = new DropboxUploader(config)
		List<SwitchTweet> filteredTweets = TweetFilter.filterForUpload(tweets, auditor)
		filteredTweets.each {
			dropbox.upload(it, TweetFilter.needsCropping(it))
			auditor.markUploaded(it)
		}
		auditor.writeAuditLog()
	}

	private void deleteImages(List<SwitchTweet> tweets) {
		DownloadCleaner cleaner = new DownloadCleaner(config)

		List<SwitchTweet> filteredTweets = TweetFilter.filterForDelete(tweets, auditor)
		filteredTweets.each {
			cleaner.delete(it, TweetFilter.needsCropping(it))
			auditor.markDeleted(it)
		}
		auditor.writeAuditLog()
	}

	private void loadConfig(String configFilePath) {
		config = new ConfigSlurper().parse(new File(configFilePath).toURL())
	}
}