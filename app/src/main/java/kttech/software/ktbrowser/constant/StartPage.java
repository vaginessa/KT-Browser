/*
 * Copyright 2014 A.C.R. Development
 */
package kttech.software.ktbrowser.constant;

import android.app.Application;
import android.support.annotation.NonNull;

import com.anthonycr.bonsai.Single;
import com.anthonycr.bonsai.SingleAction;
import com.anthonycr.bonsai.SingleSubscriber;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;

import kttech.software.ktbrowser.R;
import kttech.software.ktbrowser.app.BrowserApp;
import kttech.software.ktbrowser.preference.PreferenceManager;
import kttech.software.ktbrowser.utils.Utils;

public class StartPage {

    public static final String FILENAME = "homepage.html";

    private static final String HEAD_1 = "<!DOCTYPE html>" +
            "<html lang=\"en\">" +
            "<head>" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\">" +
            "<title>";

    private static final String HEAD_2 = "</title>" +
            "<style>" +
            "html{height: 100%;display:table;width: 100%;margin-top:-20px}" +
            "body{background: #fafafa;text-align: center;margin: 0;display: table-cell;vertical-align: middle;}" +
            "#search_input {margin-top: 1em;border: 1px solid #999;border-radius: 2px;width: 85%;max-width: 500px;outline: none;font-size: 16px;padding: .5em .6em;background-color: #fff;}" +
            "#search_input:active,#search_input:focus{border: 1px solid #777;box-shadow: 0 2px 5px 0 rgba(0,0,0,0.16),0 2px 10px 0 rgba(0,0,0,0.12);}" +
            "#search_submit{outline: none;color: #fff;font-size: 16px;font-weight: 700;border: none;border-radius: 2px;cursor: pointer;margin-top: 1em;" +
            "padding: .8em 1.8em;background-color: #777;}" +
            "#search_submit:active {background-color: #999;box-shadow: 0 2px 5px 0 rgba(0,0,0,0.16),0 2px 10px 0 rgba(0,0,0,0.12);}" +
            "#logo {width: 50%;max-width: 300px;}" +
            ".bottom{width:auto;height:auto;position:fixed;left:50%;bottom:0; margin: 0 auto; transform: translate(-50%, -15%); background: transparent; border-radius: 5px;padding-top:5px;padding_bottom:5px }" +
            ".img { height: auto; width: 40px; -webkit-transition: all 0s; transition: all 0s }"+
            ".img:hover { filter: brightness(130%); -webkit-filter: brightness(130%); -moz-filter: brightness(130%);-o-filter: brightness(130%); -ms-filter: brightness(130%); -webkit-transition: all .2s; transition: all .2s }"+
            "@media only screen and (max-width:980px) { .bottom { width: auto } .img { width: 55px } }"+
            "@media only screen and (max-width:720px) { .bottom { width: 300px } .img { height: auto; width: 50px } }"+
            "@media only screen and (max-width:480px) { .bottom { width: 280px } .img { height: auto; width: 50px } }"+
            "@media only screen and (max-width:320px) { .bottom { width: 225px; word-spacing: 10px } .img { height: auto; width: 40px } }"+
            "</style>" +
            "</head>" +
            "<body>" +
            "<div id=\"wrapper\">" +
            "<img id=\"logo\" src=\"";

    private static final String MIDDLE_1 = "\"><br>" +
            "<form autocomplete=\"off\" onsubmit=\"return search()\">" +
            "<input id=\"search_input\" type=\"text\" value=\"\"> <br>" +
            "<input id=\"search_submit\" type=\"submit\" value=\"";

    private static final String MIDDLE_2 = "\">" +
            "</form>" +
            "</div>" +
            "<script type=\"text/javascript\">" +
            "function search() {" +
            "if (document.getElementById(\"search_input\").value != \"\") {" +
            "window.location.href = \"";

    private static final String END = "\" + document.getElementById(\"search_input\").value;document.getElementById(\"search_input\").value = \"\";}return false;}</script>" +
            "<div class=\"bottom\"> <a href=\"http://fb.com\" target=\"_self\" style=\"margin-right:10px\">"+
            "<img src=\"file:///android_asset/favorite/fb-icon.png\" class=\"img\" /></a>"+
            "<a href=\"http://mp3.zing.vn\" target=\"_self\"style=\"margin-right:10px\"><img src=\"file:///android_asset/favorite/zing-icon.png\" class=\"img\" /></a>"+
            "<a href=\"http://youtube.com\" target=\"_self\"style=\"margin-right:10px\"><img src=\"file:///android_asset/favorite/youtube-icon.png\" class=\"img\" /></a>"+
            "<a href=\"http://kt-browser.com\" target=\"_self\"><img src=\"file:///android_asset/favorite/kt-icon.png\" class=\"img\" /></a> </div>"+
            "</body></html>";

    @NonNull private final String mTitle;
    @NonNull
    private final String mSearchButtonValue;
    @Inject Application mApp;
    @Inject PreferenceManager mPreferenceManager;

    public StartPage() {
        BrowserApp.getAppComponent().inject(this);
        mSearchButtonValue = mApp.getString(R.string.search_button);
        mTitle = mApp.getString(R.string.home);
    }

    @NonNull
    public Single<String> getHomepage() {
        return Single.create(new SingleAction<String>() {
            @Override
            public void onSubscribe(@NonNull SingleSubscriber<String> subscriber) {

                StringBuilder homepageBuilder = new StringBuilder(HEAD_1 + mTitle + HEAD_2);
                String icon;
                String searchUrl;
                switch (mPreferenceManager.getSearchChoice()) {
                    case 0:
                        // CUSTOM SEARCH
                        icon = "file:///android_asset/lightning.png";
                        searchUrl = mPreferenceManager.getSearchUrl();
                        break;
                    case 1:
                        // GOOGLE_SEARCH;
                        icon = "file:///android_asset/google.png";
                        // "https://www.google.com/images/srpr/logo11w.png";
                        searchUrl = Constants.GOOGLE_SEARCH;
                        break;
                    case 2:
                        // ANDROID SEARCH;
                        icon = "file:///android_asset/ask.png";
                        searchUrl = Constants.ASK_SEARCH;
                        break;
                    case 3:
                        // BING_SEARCH;
                        icon = "file:///android_asset/bing.png";
                        // "http://upload.wikimedia.org/wikipedia/commons/thumb/b/b1/Bing_logo_%282013%29.svg/500px-Bing_logo_%282013%29.svg.png";
                        searchUrl = Constants.BING_SEARCH;
                        break;
                    case 4:
                        // YAHOO_SEARCH;
                        icon = "file:///android_asset/yahoo.png";
                        // "http://upload.wikimedia.org/wikipedia/commons/thumb/2/24/Yahoo%21_logo.svg/799px-Yahoo%21_logo.svg.png";
                        searchUrl = Constants.YAHOO_SEARCH;
                        break;
                    case 5:
                        // STARTPAGE_SEARCH;
                        icon = "file:///android_asset/startpage.png";
                        // "https://com/graphics/startp_logo.gif";
                        searchUrl = Constants.STARTPAGE_SEARCH;
                        break;
                    case 6:
                        // STARTPAGE_MOBILE
                        icon = "file:///android_asset/startpage.png";
                        // "https://com/graphics/startp_logo.gif";
                        searchUrl = Constants.STARTPAGE_MOBILE_SEARCH;
                        break;
                    case 7:
                        // DUCK_SEARCH;
                        icon = "file:///android_asset/duckduckgo.png";
                        // "https://duckduckgo.com/assets/logo_homepage.normal.v101.png";
                        searchUrl = Constants.DUCK_SEARCH;
                        break;
                    case 8:
                        // DUCK_LITE_SEARCH;
                        icon = "file:///android_asset/duckduckgo.png";
                        // "https://duckduckgo.com/assets/logo_homepage.normal.v101.png";
                        searchUrl = Constants.DUCK_LITE_SEARCH;
                        break;
                    case 9:
                        // BAIDU_SEARCH;
                        icon = "file:///android_asset/baidu.png";
                        // "http://www.baidu.com/img/bdlogo.gif";
                        searchUrl = Constants.BAIDU_SEARCH;
                        break;
                    case 10:
                        // YANDEX_SEARCH;
                        icon = "file:///android_asset/yandex.png";
                        // "http://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Yandex.svg/600px-Yandex.svg.png";
                        searchUrl = Constants.YANDEX_SEARCH;
                        break;
                    default:
                        // DEFAULT GOOGLE_SEARCH;
                        icon = "file:///android_asset/google.png";
                        searchUrl = Constants.GOOGLE_SEARCH;
                        break;

                }


                homepageBuilder.append(icon);
                homepageBuilder.append(MIDDLE_1);
                homepageBuilder.append(mSearchButtonValue);
                homepageBuilder.append(MIDDLE_2);
                homepageBuilder.append(searchUrl);
                homepageBuilder.append(END);

                File homepage = new File(mApp.getFilesDir(), FILENAME);
                FileWriter hWriter = null;
                try {
                    //noinspection IOResourceOpenedButNotSafelyClosed
                    hWriter = new FileWriter(homepage, false);
                    hWriter.write(homepageBuilder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Utils.close(hWriter);
                }

                subscriber.onItem(Constants.FILE + homepage);

            }
        });
    }

}
