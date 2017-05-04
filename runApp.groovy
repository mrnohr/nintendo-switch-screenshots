import src.*

//Read config
ConfigObject config = new ConfigSlurper().parse(new File('config/Config.groovy').toURL())

//Initialize Classes
TweetReader reader = new TweetReader(config)
ImageDownloader downloader = new ImageDownloader(config)
ImageCropper cropper = new ImageCropper(config)
RunAuditor auditor = new RunAuditor(config)
DropboxUploader dropbox = new DropboxUploader(config)
DownloadCleaner cleaner = new DownloadCleaner(config)

//Read from Twitter
List<SwitchTweet> tweets = reader.readTweets()

//Download images
if(config.download.enabled) {
	List<SwitchTweet> filteredTweets = TweetFilter.filterForDownload(tweets, auditor)
	(tweets - filteredTweets).each {
		println "Already downloaded ${it.filenameBase}"
	}
	filteredTweets.each {
		downloader.downloadImage(it)
		auditor.markDownloaded(it)

		//crop if needed
		if(config.crop.enabled) {
			if(TweetFilter.needsCropping(it)) {
				cropper.cropImage(it)
			}
		}
	}
	auditor.writeAuditLog()
}

//Upload images
if(config.dropbox.enabled) {
	filteredTweets = TweetFilter.filterForUpload(tweets, auditor)
	filteredTweets.each {
		dropbox.upload(it, TweetFilter.needsCropping(it))
		auditor.markUploaded(it)
	}
	auditor.writeAuditLog()
}

//Delete the images
if(config.delete.enabled) {
	filteredTweets = TweetFilter.filterForDelete(tweets, auditor)
	filteredTweets.each {
		cleaner.delete(it, TweetFilter.needsCropping(it))
		auditor.markDeleted(it)
	}
	auditor.writeAuditLog()
}