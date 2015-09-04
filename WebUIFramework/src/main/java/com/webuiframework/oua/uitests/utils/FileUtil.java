package com.webuiframework.oua.uitests.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.openqa.selenium.Cookie;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Set;

public final class FileUtil {

    private FileUtil(){}

    /**
     * Gets default directory(path) for downloaded files
     *
     * @return Default directory(path) for downloaded files
     */
    public static String getDownloadPath() {
        return System.getProperty("user.home") + "\\Downloads\\";
    }

    /**
     * Tests whether the file denoted by this abstract pathname is a normal
     * file.  A file is <em>normal</em> if it is not a directory and, in
     * addition, satisfies other system-dependent criteria.  Any non-directory
     * file created by a Java application is guaranteed to be a normal file.
     *
     * @param pathAndFileName - name of file and FULL path for it
     *
     * @return
     * - true if file exists and
     * - false if file absence default directory(path) for downloaded files
     * - true if and only if the file denoted by this abstract pathname exists and is a normal file;
     * - false otherwise
     */
    public static boolean isFileExist(String pathAndFileName){
        File findFile = new File(pathAndFileName);
        return findFile.isFile();
    }

    /**
     * Check file download from url.
     *
     * @param downloadUrl     - url of file to download
     * @param outputFilePath  - file path for output
     * @throws  Exception - exception
     */
    public static void downloadFile(String downloadUrl, String outputFilePath) throws Exception {

        CookieStore cookieStore = seleniumCookiesToCookieStore();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.setCookieStore(cookieStore);
        HttpGet httpGet = new HttpGet(downloadUrl);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            File outputFile = new File(outputFilePath);
            InputStream inputStream = entity.getContent();
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, read);
            }
            fileOutputStream.close();
        }
        else {
        }
    }

    /**
     * Get Cookie from WebDriver browser session.
     *
     * @return cookieStore from WebDriver browser session.
     */
    private static CookieStore seleniumCookiesToCookieStore() {

        Set<Cookie> seleniumCookies = WebDriverWrapper.getDriver().manage().getCookies();
        CookieStore cookieStore = new BasicCookieStore();

        for(Cookie seleniumCookie : seleniumCookies){
            BasicClientCookie basicClientCookie =
                    new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
            basicClientCookie.setDomain(seleniumCookie.getDomain());
            basicClientCookie.setExpiryDate(seleniumCookie.getExpiry());
            basicClientCookie.setPath(seleniumCookie.getPath());
            cookieStore.addCookie(basicClientCookie);
        }

        return cookieStore;
    }

}

