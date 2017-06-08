package com.ityingli.www.mynews.Bean;

/**
 * Created by Administrator on 2017/5/28.
 */

public class RiBao_Viewpager {
        String title;
        String imgUri;

    public RiBao_Viewpager(String title, String imgUri) {
        this.title = title;
        this.imgUri = imgUri;
    }

    public String getTitle() {
        return title;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

}
