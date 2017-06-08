package com.ityingli.www.mynews.Bean;

/**
 * Created by Administrator on 2017/5/28.
 */

public class RiBao_recycleView_item {
    String imgUri;
    String title;
    int id;

    public RiBao_recycleView_item(String imgUri, String title, int id) {
        this.imgUri = imgUri;
        this.title = title;
        this.id = id;
    }

    public String getImgUri() {
        return imgUri;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setId(int id){
        this.id = id;
    }
}

