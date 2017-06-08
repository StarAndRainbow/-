package com.ityingli.www.mynews.Bean;

/**
 * Created by Administrator on 2017/5/31.
 */

public class RiBao_Content_Item {
    public String title;
    public String imgUri;
    public String body;

    public RiBao_Content_Item(String title, String imgUri, String body) {
        this.title = title;
        this.imgUri = imgUri;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getImgUri() {
        return imgUri;
    }

    public String getBody() {
        return body;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
