# Nintendo Switch Screenshot Downloader

I like being able to share screenshots from my Nintendo Switch to Twitter, but I'd also like to download a copy. This groovy script reads your timeline and downloads the screenshots to your computer.

![Alt text](images/readme-header.jpg?raw=true "Zelda")

## Downloading

Either clone this repository or download the latest [release](https://github.com/mrnohr/nintendo-switch-screenshots/releases). All you really need is the `screenshots.groovy` file. The `twitter4j.properties.example` file is a template that you can use when setting up twitter auth.

## Groovy

This runs with Groovy. If you need to install Groovy, [click here](http://groovy-lang.org/install.html).

## Twitter Account Setup

You just pick which twitter account to lookup (in `screenshots.groovy`). The script will only look for tweets sent from the Nintendo Switch. It can be a private twitter account, as long as you have access to view the tweets.

## Twitter Auth Setup

To use this, you will need to create a new Twitter application at https://apps.twitter.com/. This is what will be used to lookup the tweets. Once you setup the application you will need to create yourself an access token, which is all done in the Twitter application setup.

In my case, I have a separate Twitter account that I marked private. My main twitter account is following that second account, and my main twitter account is the one I used to create the Twitter application. It is even easier if you just use one account.

### twitter4j.properties

Once you have the application keys and access tokens for your new app, create a `twitter4j.properties` file in the root directory (same as `screenshots.groovy`) in this format:

	debug=true
	oauth.consumerKey=*********************
	oauth.consumerSecret=******************************************
	oauth.accessToken=**************************************************
	oauth.accessTokenSecret=******************************************

There is an example `twitter4j.properties.example` file you can use to start with.

More information can be found here: http://twitter4j.org/en/configuration.html#fileconfiguration

## Running

First update the `screenshots.groovy` file. Towards the top there are a few configuration options most importantly what twitter username to look for.

Then just run:

	groovy screenshots.groovy

It pulls the last 25 (can be changed in `screenshots.groovy`) tweets from your user, and downloads the image if the tweet came from the Switch (Twitter has a "source" of `Nintendo Switch Share`).

It will not download the same image twice because it names the files based on the date of the tweet.

## Image information

In my tests, the downloaded images have a resolution of 1280x720 and are in .jpg format.
