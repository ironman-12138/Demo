package org.example.service;

import org.elasticsearch.action.index.IndexResponse;
import org.example.domin.EsGoods;

import java.io.IOException;
import java.util.List;

public interface EsService {

    public boolean createIndexOfArticle();

    public boolean testExistIndex() throws IOException;

    public IndexResponse addEsGoods(EsGoods esGoods);

    public List<EsGoods> queryByKey(String keyword);

    public EsGoods queryById(String indexId);

}
