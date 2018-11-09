package io.liyou.sample.model.entity;

import java.util.List;

/**
 * Time: 2018/11/7 0007
 * Created by LiYou
 * Description :
 */
public class PageEntity<T> {
    public int curPage;
    public boolean over;
    public List<T> datas;
}
