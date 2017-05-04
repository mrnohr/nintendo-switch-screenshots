package src

import java.awt.Image
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class ImageCropper {
	ConfigObject config
	File downloadDirectory
	File cropDirectory

	public ImageCropper(ConfigObject config) {
		this.config = config
		validateOrCreateDirectory()
	}

	public void cropImage(SwitchTweet tweet) {
		println "Going to crop ${tweet.downloadFilename}"

		int x = 0
		int y = 70
		int w = 1280
		int h = 650

		File inputFile = new File(tweet.downloadFilename, downloadDirectory)
		File outputFile = new File(tweet.croppedFilename, cropDirectory)

		Image src = ImageIO.read(inputFile)

		BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
		dst.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null)

		ImageIO.write(dst, "png", outputFile)
	}

	private validateOrCreateDirectory(){
		downloadDirectory = new File(config.download.directory)
		if(!downloadDirectory.exists()) {
			throw FileNotFoundException("Could not crop because $downloadDirectory does not exist")
		}

		cropDirectory = new File(config.crop.directory)
		if(!cropDirectory.exists()) {
			println "Creating directory ${cropDirectory.absolutePath}"
			cropDirectory.mkdirs()
		}
	}
}